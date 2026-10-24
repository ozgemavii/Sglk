# AI Plate & Progress Coach — Mobil Yaşam Simülasyonu, Beslenme Koçu & Moda Stüdyosu

**AI Plate & Progress Coach**, beslenme ve kalori takibini, Zepeto/Stardoll/The Sims tarzı derinlikli 3D/vektörel karakter yaratma ve vücut heykeltıraşı motorunu, profesyonel Kıyafet Tasarım Atölyesi'ni (Moda Atölyesi) ve gerçek zamanlı **Gemini AI** tabak/tarif analizini tek bir akıcı (60 FPS) mobil deneyimde birleştiren kapsamlı bir yaşam simülasyonu uygulamasıdır.

---

## Kullanıcı Onayları ve Kritik Kararlar

> [!IMPORTANT]
> İlk aşamada yanıtladığınız tercihlere göre mimari kararlar aşağıdaki şekilde kesinleştirilmiştir:

- **Onaylanan Karar 1 (Çalışma Motoru & Mimari)**: Uygulama, Android üzerinde tam ekran (Edge-to-Edge) çalışan, donanım hızlandırmalı bir **WebView Oyun & Simülasyon Motoru (Tek Parça Kusursuz HTML/CSS/JS)** olarak inşa edilecektir. Android katmanı; galeri/kamera erişimi ve Gemini AI köprüsünü (`AndroidBridge`) sağlayacaktır.
- **Onaylanan Karar 2 (Yapay Zekâ Entegrasyonu)**: AI Kamera (Tabak, Etiket, Menü Analizi) ve Akıllı Mutfak (Fit Tarif Üreticisi, Dolabımı Çek, 7 Günlük Menü) modülleri, **`gemini-3.5-flash`** modeli üzerinden gerçek zamanlı multimodal görüntü ve metin analizi yapacak şekilde entegre edilecektir (API anahtarı olmadığında veya çevrimdışı durumda kesintisiz akıllı analiz motoru devreye girecektir).
- **Onaylanan Karar 3 (Veri Kaydı & Sosyal Sistem)**: Tüm karakter anatomisi, gardırop, özel tasarım kıyafet dokuları, beslenme günlüğü, LP/Elmas bakiyesi, Takas Pazarı ilanları, pasif gelir döngüsü ve Arkadaş/Sohbet geçmişi cihaz içi kalıcı depolamada (`localStorage` + WebView DOM Storage) saklanacak ve etkileşimli sosyal simülasyon motoruyla desteklenecektir.

---

## 1. Genel Bakış ve Temel Konsept

- **Ne Yapar?**: Kullanıcının fiziksel ölçülerini ve 8 eksenli anatomik vücut kalıbını gerçek zamanlı olarak 3D/vektörel gölgelendirmeli karakteri üzerinde simüle eder; günlük kalori, makro besin ve su takibini oyunlaştırır; gerçek yemek fotoğraflarını Gemini AI ile analiz ederek yüzen etiketlerle (floating tags) kalori/makro çıkarımı yapar; kullanıcının sıfırdan fırça, desen ve galeri fotoğrafı kaplamasıyla (texture mapping) kıyafet tasarlayıp Takas Pazarı'nda LP karşılığı satmasını sağlar.
- **Hedef Kitle**: Sağlıklı yaşam, kalori/makro takibi ve fitness hedeflerini sıkıcı tablolar yerine Loftia, CaloriCam, Zepeto ve Stardoll tarzı estetik bir yaşam simülasyonu ve moda dünyası içinde deneyimlemek isteyen kullanıcılar.
- **Temel Değer Önerisi**: Fiziksel gelişimi ve sağlıklı beslenme alışkanlıklarını doğrudan karakterin yaşam odasına, evcil hayvanına, moda tasarım ekonomisine (Lezzet Puanı & Elmas) ve sosyal dünyasına bağlayan bütünsel motivasyon döngüsü.

---

## 2. Kullanıcı Deneyimi ve Görsel Tasarım

### Temel Kullanıcı Akışları
1. **İlk Giriş (Onboarding) & Canlı Boy/Kilo Vücut Simülasyonu**:
   - Tanıtım kartlarının ardından açılan "Fiziksel Ölçüler" ekranında takma ad, yaş, cinsiyet, boy (`140–210 cm` slider) ve kilo (`40–160 kg` slider) seçilir.
   - Boy veya kilo çubuğu kaydırıldığı anda arka plandaki karakterin bacak/gövde boyu ve BMI/VKİ kalibresi (zayıf, atletik, balıketli, dolgun) 60 FPS akıcılıkta anında yeniden hesaplanıp çizilir.
2. **Ana Sayfa (Canlı Yaşam Odası & CaloriCam Kartları)**:
   - Üst bölümde pencereli, güneş/ay ışığı kırılmalı, bitkili ve zemin derinlikli interaktif yaşam odasında karakter ve sahiplenilen evcil hayvan (Pet) canlı nefes alma/salınım animasyonuyla yer alır.
   - Altında CaloriCam estetiğinde fıstık yeşili **Günlük Kalori Kartı** (sol tarafta hedef yüzdesi, sağda SVG dairesel kalori halkası `1.250 / 1.850 kcal`, altta Kalan Kalori), **Makro Besin Dağılımı** (Karbonhidrat, Protein, Yağ ilerleme çubukları), **8 Bardak Tek Dokunuşlu Su Takibi** ve öğün özetleri bulunur.
3. **AI Kamera & Tabak / Etiket / Menü Analizi**:
   - Alt navigasyonun ortasındaki büyük **Kamera (📸)** butonuna basıldığında tam ekran vizör açılır; kullanıcı galeriden/kameradan fotoğraf seçebilir veya örnek gurme tabakları tarayabilir.
   - **Ev Yemeği | Restoran | Etiket Oku | Menü Oku** modları arasında geçiş yapılır.
   - Gemini AI (`gemini-3.5-flash`) analizi sonucunda tabak üzerinde siyah-yeşil **yüzen etiketler (floating tags)** belirir; etikete dokunarak gramaj/isim düzenlenir, `"Payım: Tamamı / Yarısı"` ve `"Yağ: Az / Normal"` seçilip günlüğe eklendiğinde **+20 LP** kazanılır.
4. **3D/Vektörel Vücut Heykeltıraşı & Kapsayıcı Yüz/Saç Stüdyosu**:
   - **Vücut & Kalıp Sekmesi**: `-3` ile `+3` arasında hassas kaydırıcılarla Boy Uzunluğu & Orantı, Genel Kalibre, Omuz Genişliği, Göğüs/Üst Gövde Dolgunluğu, Bel İnceliği (Kum saati/Atletik), Karın/Göbek Yapısı, Kalça & Basen Genişliği ve Kol/Uyluk/Bacak Kalınlığı şekillendirilir.
   - **6 Hızlı Şablon (Presets)**: *Zepeto İdeal*, *İnce Bel & Geniş Kalça (Kum Saati)*, *Armut Tipi*, *Atletik / V-Gövde*, *Minyon & Zarif*, *Dolgun & Kıvrımlı*.
   - **Dinamik Kıyafet Esneme Motoru**: Seçilen tüm kıyafetler (S, M, L, XL, XXL) karakterin bel oyuntusu, göğüs ve kalça/basen kontrol noktalarına göre parametrik Bezier eğrileriyle otomatik esneyip vücuda tam oturur.
   - **Kapsayıcı Yüz, Saç & Detay Stüdyosu**: 24 ten rengi + sınırsız Color Picker; 10 yüz/çene şekli; 12 göz stili + 20 göz rengi + **Heterokromi** (sağ/sol farklı göz rengi); 10 burun ve kaş stili; 9 dudak stili + 16 ruj/gloss rengi; 15 sakal/bıyık modeli; 40+ saç/rasta/afrika örgüsü/başörtüsü/türban modeli (**Çift renkli Ombre geçişleri** dahil); çoklu seçilebilir yüz detayları (çiller, gamzeler, benler, allık, elmacık kontürü, göz altı halkası, kaş izi, vitiligo, hızma, piercing, şeffaf optik gözlük, işitme cihazı, yüz/boyun dövmeleri).
5. **Moda Atölyesi (Fashion Designer Studio - Stardoll & Zepeto Tarzı)**:
   - **Kalıp Seçimi**: Tişört, crop top, hoodie, kot pantolon, etek, elbise, ceket, sırt çantası veya bez çanta şablonu.
   - **Galeriye Göre Baskı (Custom Texture Upload)**: Cihaz galerisinden yüklenen fotoğraf veya desenin kıyafet UV/clip-path maskesi içine giydirilmesi; dokunmatik/kaydırıcı kontrollerle boyut (scale), X/Y konum ve açı (rotation) ayarı.
   - **Serbest Boyama & Desen Fırçası**: HTML5 Canvas tabanlı serbest fırça çizimi, kalınlık/renk seçimi ve hazır geometrik/pop-art desen damgaları.
   - **Metin & Rozet Ekleme**: Özel font, renk ve boyutta slogan/rozet yerleştirme.
   - **Pazara Çıkarma & Pasif Gelir**: Tasarıma isim ve LP fiyatı verip **Takas Pazarı**'na listeleme; simüle edilen topluluk alımlarıyla tasarımcının düzenli pasif LP kazanması.
6. **Lezzet Çarkı & Akıllı Mutfak**:
   - *"Bugün Ne Yesem?"* animasyonlu dönemecin (Lezzet Çarkı), *"Canım Çekti"* (yüksek kalorili isteklerin Gemini AI destekli fit versiyonları), *"Dolabımı Çek"* (sıfır atık malzeme analizi), *7 Günlük Akıllı Menü*, etkileşimli *Alışveriş Listesi* ve Web Speech API destekli *Sesli/Eller Serbest Adım Adım Pişirme Modu*.
7. **Mağaza, Evcil Hayvan (Pet), Arkadaşlar & Canlı Sohbet**:
   - LP ve Elmas (💎) mağazası; Kedi, Köpek, Penguen, Tavşan, Panda ve Tilki sahiplenme, isim verme ve pet aksesuarları; arkadaşların 3D/Zepeto kombinleriyle çevrimiçi listesi, kıyafet takası, hediye gönderimi ve gerçek zamanlı sohbet odaları.

### Görsel Kimlik ve Tema
- **Estetik Yönelim**: Loftia'nın sıcak ekolojik/pastel yaşam simülasyonu atmosferi ile CaloriCam'in net, modern fıstık yeşili veri kartları ve Zepeto/Stardoll'un pürüzsüz hacimli moda stüdyosu estetiğinin birleşimi.
- **Renk Paleti (Aydınlık & Karanlık Mod Destekli)**:
  - `--bg-primary`: Krem / Yumuşak Fildişi (`#F7F9F4` aydınlık, `#0F1419` karanlık mod)
  - `--glass-surface`: Yarı şeffaf buzlu cam (`rgba(255, 255, 255, 0.72)` / `backdrop-filter: blur(20px)`)
  - `--accent-pistachio`: CaloriCam Fıstık Yeşili (`#A3E635` / `#84CC16`)
  - `--accent-mint`: Loftia Mint Yeşili (`#6EE7B7`)
  - `--accent-lilac`: Pastel Lila (`#C4B5FD`)
  - `--accent-sky`: Bebek Mavisi (`#93C5FD`)
  - `--accent-peach`: Sıcak Mercan/Şeftali (`#FDA4AF`)

---

## 3. Temel Ürün Kararları ve Ödünleşimler (Trade-Offs)

- **Karar 1: Parametrik SVG + Canvas 3D Gölgelendirme Motoru**
  - *Seçilen Yaklaşım*: Karakterin vücut parçaları, yüz hatları ve kıyafetleri sabit resimler yerine matematiksel Bezier eğrileri, çok katmanlı radyal/lineer SVG gradyanları (`<radialGradient>`, `<linearGradient>`, `<filter>`) ve dinamik HTML5 Canvas doku haritalaması ile gerçek zamanlı çizilir.
  - *Neden?*: `-3` ile `+3` arasındaki 8 anatomik kaydırıcı ve boy/kilo değiştiğinde hem vücudun hem de üzerindeki kıyafetin (S–XXL) pikselleşmeden, 3D hacim hissiyle ve 60 FPS hızında esnemesini sağlar; ayrıca Moda Atölyesi'nde çizilen Canvas dokularını doğrudan karakterin üzerindeki kıyafete giydirmeye olanak tanır.
- **Karar 2: Android Native Köprü (`@JavascriptInterface`) ile Gemini AI Entegrasyonu**
  - *Seçilen Yaklaşım*: WebView içindeki uygulama, kamera/galeri görüntülerini ve tarif isteklerini `window.AndroidBridge` üzerinden Kotlin katmanına iletir; Kotlin katmanı `BuildConfig.GEMINI_API_KEY` kullanarak `gemini-3.5-flash` REST API çağrısını arka planda gerçekleştirir ve yapılandırılmış JSON sonucunu WebView'a döner.
  - *Neden?*: API anahtarını güvenli şekilde `BuildConfig` üzerinden yönetir, CORS kısıtlamalarını ortadan kaldırır ve hem gerçek fotoğraflarda Gemini AI vizyon analizini hem de zengin etkileşimli yedek (fallback) deneyimini garanti eder.

---

## 4. Teknik Mimari ve Veri Stratejisi

### Sistem ve Bileşen Mimarisi
```
┌────────────────────────────────────────────────────────────────────────────┐
│                   ANDROID UYGULAMA KATMANI (Kotlin)                        │
│  ┌──────────────────────────┐  ┌───────────────────────┐  ┌─────────────┐  │
│  │ Tam Ekran WebView Motoru │  │ AndroidGeminiBridge   │  │ PhotoPicker │  │
│  │ (DOM Storage, Donanım    │◄─┤ (@JavascriptInterface)│  │ & Kamera    │  │
│  │  Hızlandırma, Ses/TTS)   │  │ gemini-3.5-flash API  │  │ FileChooser │  │
│  └────────────┬─────────────┘  └───────────────────────┘  └──────┬──────┘  │
└───────────────┼──────────────────────────────────────────────────┼─────────┘
                │                                                  │
┌───────────────▼──────────────────────────────────────────────────▼─────────┐
│             TEK PARÇA OYUN & SİMÜLASYON MOTORU (HTML5 / CSS3 / JS)         │
│                                                                            │
│  ┌──────────────────────────────────────────────────────────────────────┐  │
│  │                  MERKEZİ DURUM DEPOSU (State & Persistence)          │  │
│  │  • Profil & Ölçüler (Boy, Kilo, VKİ, 8 Anatomik Heykeltıraş Ekseni)  │  │
│  │  • Görünüm (Ten, Yüz, Heterokromi Göz, Ombre Saç, Sakal, Detaylar)   │  │
│  │  • Beslenme & Su (Kalori, Makrolar, Öğünler, Yüzen Etiket Kayıtları) │  │
│  │  • Ekonomi & Sosyal (LP, Elmas, Özel Kıyafetler, Pazar, Pet, Chat)   │  │
│  └──────┬──────────────────┬──────────────────┬──────────────────┬──────┘  │
│         │                  │                  │                  │         │
│  ┌──────▼───────┐   ┌──────▼───────┐   ┌──────▼───────┐   ┌──────▼──────┐  │
│  │ 3D/Vektörel  │   │ CaloriCam &  │   │ Moda Atölyesi│   │ Sosyal, Pet │  │
│  │ Karakter &   │   │ Akıllı Mutfak│   │ (UV/Canvas   │   │ & Takas     │  │
│  │ Oda Render   │   │ (Gemini AI   │   │  Boyama,     │   │ Pazarı      │  │
│  │ Motoru (SVG  │   │  Tabak/Menü  │   │  Foto Baskı, │   │ (Pasif LP   │  │
│  │ + Bezier +   │   │  Tarayıcı &  │   │  Metin/Rozet │   │  Geliri &   │  │
│  │ Dinamik Fit) │   │  Lezzet Çarkı│   │  & Üretim)   │   │  Canlı Chat)│  │
│  └──────────────┘   └──────────────┘   └──────────────┘   └─────────────┘  │
└────────────────────────────────────────────────────────────────────────────┘
```

### Etkileşimli Bileşen ve Durum Eşlemesi
- **Anatomik Parametre Motoru (`BodySculptEngine`)**: Boy (`140–210`), Kilo (`40–160`) ve 8 heykeltıraş parametresini (`-3`..`+3`) alarak omuz, göğüs, bel oyuntusu, karın, kalça/basen ve bacak Bezier koordinatlarını gerçek zamanlı üretir; üzerindeki kıyafet SVG path'lerini ve özel Canvas dokularını aynı matrisle esnetir.
- **Moda Atölyesi UV & Canvas Motoru (`FashionStudioEngine`)**: Seçilen kalıp üzerinde 3 katmanlı (Taban Kumaş Rengi + Galeri Fotoğrafı/Desen Transform Katmanı + Serbest Fırça & Metin/Rozet Katmanı) kompozisyon oluşturur; `toDataURL()` ile üretilen dokuyu hem karakterin üzerinde canlı gösterir hem de Takas Pazarı'na kaydeder.
- **AI Tabak & Mutfak Köprüsü (`AICoachEngine`)**: Kamera vizöründen alınan görüntüyü `AndroidBridge.analyzeFoodImage(base64, mode)` ile `gemini-3.5-flash` modeline gönderir; dönen JSON içindeki yemek kalemlerini, koordinatlarını, gramajlarını ve makrolarını tabak üzerinde sürüklenebilir/düzenlenebilir yüzen etiketler olarak render eder.
