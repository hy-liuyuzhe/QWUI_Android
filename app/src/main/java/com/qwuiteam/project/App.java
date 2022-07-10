package com.qwuiteam.project;

import android.app.Application;

import com.arthenica.ffmpegkit.FFmpegKitConfig;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.Utils;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        //BlockCanary.install(this, new AppBlockCanaryContext()).start();
        //Looper.getMainLooper().setMessageLogging(new MainLooperPrinter());
        FFmpegKitConfig.setFontDirectory(this,"/storage/emulated/0/Download/font",null);
    }
}
