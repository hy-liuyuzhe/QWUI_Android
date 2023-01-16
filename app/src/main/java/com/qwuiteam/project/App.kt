package com.qwuiteam.project

import android.app.Activity
import android.app.Application
import android.net.http.HttpResponseCache
import android.os.Environment
import com.arthenica.ffmpegkit.FFmpegKitConfig
import com.blankj.utilcode.util.*
import com.opensource.svgaplayer.utils.log.SVGALogger
import com.opensource.svgaplayer.utils.log.SVGALogger.setLogEnabled
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog
import java.io.File

class App : Application() {


    companion object {
        init {
            System.loadLibrary("c++_shared")
            System.loadLibrary("marsxlog")
        }
    }

    override fun onCreate() {
        super.onCreate()
        initxLog()
        Utils.init(this)
        LogUtils.getConfig().globalTag = "liuyuzhe"
        SVGALogger.setLogEnabled(true)
        val cacheDir = File(this.applicationContext.cacheDir, "http")
        HttpResponseCache.install(cacheDir, 1024 * 1024 * 128)

        //BlockCanary.install(this, new AppBlockCanaryContext()).start();
        //Looper.getMainLooper().setMessageLogging(new MainLooperPrinter());
        FFmpegKitConfig.setFontDirectory(this, "/storage/emulated/0/Download/font", null)
        ActivityUtils.addActivityLifecycleCallbacks(object : Utils.ActivityLifecycleCallbacks(){
            override fun onActivityCreated(activity: Activity) {
                android.util.Log.d("liuyuzhe", "onActivityCreated: $activity");
            }

            override fun onActivityStarted(activity: Activity) {
                android.util.Log.d("liuyuzhe", "onActivityStarted: $activity");
            }

            override fun onActivityPaused(activity: Activity) {
                android.util.Log.d("liuyuzhe", "onActivityPaused: $activity");
            }
        })

    }


    private fun initxLog() {
        val SDCARD = Environment.getExternalStorageDirectory().absolutePath;
        val logPath = SDCARD + "/marssample/log";
        val cachePath = this.filesDir.absolutePath + "/xlog"

        LogUtils.d("logPath $logPath");

        if (BuildConfig.DEBUG) {
            Log.setConsoleLogOpen(true);
            Log.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, "liuyuzheNamePreFix", 0);
        } else {
            Log.setConsoleLogOpen(false);
            Log.appenderOpen(Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, "", logPath, "liuyuzheNamePreFix_release", 0);
        }
    }
}