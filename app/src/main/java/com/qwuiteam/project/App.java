package com.qwuiteam.project;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        //BlockCanary.install(this, new AppBlockCanaryContext()).start();
        //Looper.getMainLooper().setMessageLogging(new MainLooperPrinter());
    }
}
