package com.qwuiteam.project

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

const val TAG = "NoticeService"

class NoticeService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate: ", )
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}