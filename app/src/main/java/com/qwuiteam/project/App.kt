package com.qwuiteam.project

import android.app.Application
import com.arthenica.ffmpegkit.FFmpegKitConfig
import com.blankj.utilcode.util.Utils
import com.opensource.svgaplayer.utils.log.SVGALogger
import com.opensource.svgaplayer.utils.log.SVGALogger.setLogEnabled

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        SVGALogger.setLogEnabled(true)
        //BlockCanary.install(this, new AppBlockCanaryContext()).start();
        //Looper.getMainLooper().setMessageLogging(new MainLooperPrinter());
        FFmpegKitConfig.setFontDirectory(this, "/storage/emulated/0/Download/font", null)
    }
}