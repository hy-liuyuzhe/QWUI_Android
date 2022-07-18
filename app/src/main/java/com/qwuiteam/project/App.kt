package com.qwuiteam.project

import android.app.Application
import android.net.http.HttpResponseCache
import com.arthenica.ffmpegkit.FFmpegKitConfig
import com.blankj.utilcode.util.Utils
import com.opensource.svgaplayer.utils.log.SVGALogger
import com.opensource.svgaplayer.utils.log.SVGALogger.setLogEnabled
import java.io.File

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        SVGALogger.setLogEnabled(true)
        val cacheDir = File(this.applicationContext.cacheDir, "http")
        HttpResponseCache.install(cacheDir, 1024 * 1024 * 128)

        //BlockCanary.install(this, new AppBlockCanaryContext()).start();
        //Looper.getMainLooper().setMessageLogging(new MainLooperPrinter());
        FFmpegKitConfig.setFontDirectory(this, "/storage/emulated/0/Download/font", null)
    }
}