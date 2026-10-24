package com.example

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Base64
import android.webkit.JavascriptInterface
import android.webkit.WebView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.Locale
import java.util.concurrent.TimeUnit

class AndroidGeminiBridge(
    context: Context,
    private val webViewProvider: () -> WebView?,
    private val coroutineScope: CoroutineScope
) {
    private var tts: TextToSpeech? = null
    private var ttsReady = false

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    init {
        tts = TextToSpeech(context.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(Locale("tr", "TR"))
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    tts?.setLanguage(Locale.getDefault())
                }
                ttsReady = true
            }
        }
    }

    @JavascriptInterface
    fun hasValidApiKey(): Boolean {
        val key = BuildConfig.GEMINI_API_KEY
        return key.isNotBlank() && key != "MY_GEMINI_API_KEY" && key != "null"
    }

    @JavascriptInterface
    fun speakTurkish(text: String) {
        if (ttsReady) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "cook_step")
        }
    }

    @JavascriptInterface
    fun stopSpeaking() {
        if (ttsReady) {
            tts?.stop()
        }
    }

    @JavascriptInterface
    fun analyzePlateWithGemini(base64Image: String, scanMode: String, dishHint: String, callbackId: String) {
        coroutineScope.launch(Dispatchers.IO) {
            val apiKey = BuildConfig.GEMINI_API_KEY
            if (!hasValidApiKey()) {
                sendCallbackToJs(callbackId, JSONObject().apply {
                    put("status", "fallback")
                    put("reason", "missing_key")
                }.toString())
                return@launch
            }

            try {
                val prompt = """
                    Sen profesyonel bir diyetisyen ve AI Kalori/Tabak Analiz uzmanısın.
                    Mod: $scanMode. İpucu/Açıklama: $dishHint.
                    Lütfen bu görseldeki veya açıklamadaki yiyecekleri analiz et ve SADECE geçerli bir JSON objesi döndür (markdown veya ```json kullanma).
                    JSON şeması:
                    {
                      "mealTitle": "Yemeğin Türkçe Adı",
                      "healthScore": 88,
                      "coachTip": "Kısa motive edici beslenme koçu yorumu (Türkçe)",
                      "items": [
                        {
                          "name": "Yiyecek Adı",
                          "grams": 150,
                          "kcal": 240,
                          "carb": 18,
                          "protein": 26,
                          "fat": 8,
                          "x": 38,
                          "y": 42
                        }
                      ]
                    }
                    Not: x ve y değerleri tabak üzerindeki yüzdelik (20 ile 80 arası) koordinatlardır. En az 3 yiyecek bileşeni döndür.
                """.trimIndent()

                val partsArray = JSONArray()
                partsArray.put(JSONObject().apply { put("text", prompt) })

                if (base64Image.isNotBlank() && base64Image.contains(",")) {
                    val cleanBase64 = base64Image.substringAfter(",")
                    val mimeType = base64Image.substringAfter("data:", "image/jpeg").substringBefore(";")
                    partsArray.put(JSONObject().apply {
                        put("inlineData", JSONObject().apply {
                            put("mimeType", if (mimeType.isNotBlank()) mimeType else "image/jpeg")
                            put("data", cleanBase64)
                        })
                    })
                }

                val payload = JSONObject().apply {
                    put("contents", JSONArray().put(JSONObject().apply {
                        put("parts", partsArray)
                    }))
                    put("generationConfig", JSONObject().apply {
                        put("responseMimeType", "application/json")
                        put("temperature", 0.4)
                    })
                }

                val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
                val request = Request.Builder()
                    .url(url)
                    .post(payload.toString().toRequestBody("application/json".toMediaType()))
                    .build()

                okHttpClient.newCall(request).execute().use { response ->
                    val bodyStr = response.body?.string().orEmpty()
                    if (response.isSuccessful && bodyStr.isNotBlank()) {
                        val root = JSONObject(bodyStr)
                        val text = root.optJSONArray("candidates")
                            ?.optJSONObject(0)
                            ?.optJSONObject("content")
                            ?.optJSONArray("parts")
                            ?.optJSONObject(0)
                            ?.optString("text", "")
                            .orEmpty()
                        if (text.isNotBlank()) {
                            sendCallbackToJs(callbackId, JSONObject().apply {
                                put("status", "ok")
                                put("data", JSONObject(text))
                            }.toString())
                            return@use
                        }
                    }
                    sendCallbackToJs(callbackId, JSONObject().apply {
                        put("status", "fallback")
                        put("reason", "api_error_${response.code}")
                    }.toString())
                }
            } catch (e: Exception) {
                sendCallbackToJs(callbackId, JSONObject().apply {
                    put("status", "fallback")
                    put("reason", e.message ?: "exception")
                }.toString())
            }
        }
    }

    @JavascriptInterface
    fun generateRecipeWithGemini(query: String, mode: String, callbackId: String) {
        coroutineScope.launch(Dispatchers.IO) {
            val apiKey = BuildConfig.GEMINI_API_KEY
            if (!hasValidApiKey()) {
                sendCallbackToJs(callbackId, JSONObject().apply {
                    put("status", "fallback")
                    put("reason", "missing_key")
                }.toString())
                return@launch
            }

            try {
                val prompt = """
                    Sen yaratıcı bir Fit Şef ve Beslenme Koçusun.
                    Mod: $mode (craving = Canım Çekti Fit Alternatif, fridge = Dolabımı Çek Sıfır Atık Tarif, weekly = 7 Günlük Akıllı Menü).
                    Kullanıcı girdisi: "$query"
                    Lütfen SADECE geçerli bir JSON objesi döndür:
                    {
                      "title": "Fit Tarif Adı",
                      "subtitle": "Normal versiyona göre %45 daha az kalorili!",
                      "kcal": 340,
                      "prepMinutes": 18,
                      "protein": 28,
                      "carb": 32,
                      "fat": 11,
                      "ingredients": ["Malzeme 1", "Malzeme 2", "Malzeme 3", "Malzeme 4"],
                      "steps": [
                        "1. Adım açıklaması",
                        "2. Adım açıklaması",
                        "3. Adım açıklaması",
                        "4. Adım açıklaması"
                      ]
                    }
                """.trimIndent()

                val payload = JSONObject().apply {
                    put("contents", JSONArray().put(JSONObject().apply {
                        put("parts", JSONArray().put(JSONObject().apply { put("text", prompt) }))
                    }))
                    put("generationConfig", JSONObject().apply {
                        put("responseMimeType", "application/json")
                        put("temperature", 0.6)
                    })
                }

                val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
                val request = Request.Builder()
                    .url(url)
                    .post(payload.toString().toRequestBody("application/json".toMediaType()))
                    .build()

                okHttpClient.newCall(request).execute().use { response ->
                    val bodyStr = response.body?.string().orEmpty()
                    if (response.isSuccessful && bodyStr.isNotBlank()) {
                        val root = JSONObject(bodyStr)
                        val text = root.optJSONArray("candidates")
                            ?.optJSONObject(0)
                            ?.optJSONObject("content")
                            ?.optJSONArray("parts")
                            ?.optJSONObject(0)
                            ?.optString("text", "")
                            .orEmpty()
                        if (text.isNotBlank()) {
                            sendCallbackToJs(callbackId, JSONObject().apply {
                                put("status", "ok")
                                put("data", JSONObject(text))
                            }.toString())
                            return@use
                        }
                    }
                    sendCallbackToJs(callbackId, JSONObject().apply {
                        put("status", "fallback")
                        put("reason", "api_error_${response.code}")
                    }.toString())
                }
            } catch (e: Exception) {
                sendCallbackToJs(callbackId, JSONObject().apply {
                    put("status", "fallback")
                    put("reason", e.message ?: "exception")
                }.toString())
            }
        }
    }

    private suspend fun sendCallbackToJs(callbackId: String, jsonPayload: String) {
        withContext(Dispatchers.Main) {
            val encoded = Base64.encodeToString(jsonPayload.toByteArray(Charsets.UTF_8), Base64.NO_WRAP)
            webViewProvider()?.evaluateJavascript(
                "window.__onAndroidGeminiResult && window.__onAndroidGeminiResult('$callbackId', '$encoded');",
                null
            )
        }
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
    }
}
