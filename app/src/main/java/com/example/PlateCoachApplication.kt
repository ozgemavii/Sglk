package com.example

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import java.io.File
import java.util.concurrent.Executor

class PlateCoachApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        purgeUnsignedVariationsSeed(this)
    }

    override fun bindService(service: Intent, conn: ServiceConnection, flags: Int): Boolean {
        if (isVariationsSeedService(service)) {
            purgeUnsignedVariationsSeed(this)
            return true
        }
        return super.bindService(service, conn, flags)
    }

    override fun bindService(
        service: Intent,
        flags: Int,
        executor: Executor,
        conn: ServiceConnection
    ): Boolean {
        if (isVariationsSeedService(service)) {
            purgeUnsignedVariationsSeed(this)
            return true
        }
        return super.bindService(service, flags, executor, conn)
    }

    companion object {
        fun isVariationsSeedService(intent: Intent?): Boolean {
            if (intent == null) return false
            val cls = intent.component?.className.orEmpty()
            val action = intent.action.orEmpty()
            return cls.contains("VariationsSeed", ignoreCase = true) ||
                action.contains("VariationsSeed", ignoreCase = true) ||
                intent.toString().contains("VariationsSeed", ignoreCase = true)
        }

        fun purgeUnsignedVariationsSeed(context: Context) {
            runCatching {
                val webViewDir = context.getDir("webview", Context.MODE_PRIVATE)
                deleteVariationsFilesInDir(webViewDir)
                context.dataDir?.let { dataDir ->
                    deleteVariationsFilesInDir(File(dataDir, "app_webview"))
                }
            }
        }

        private fun deleteVariationsFilesInDir(dir: File?) {
            if (dir == null || !dir.exists() || !dir.isDirectory) return
            val targets = listOf("variations_seed", "variations_seed_new", "variations_stamp")
            targets.forEach { name ->
                val f = File(dir, name)
                if (f.exists()) {
                    f.delete()
                }
            }
            dir.listFiles()?.forEach { file ->
                if (file.name.startsWith("variations_")) {
                    file.delete()
                }
            }
        }
    }
}
