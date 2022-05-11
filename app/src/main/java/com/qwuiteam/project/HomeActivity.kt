package com.qwuiteam.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qmuiteam.qwui.widget.AndroidBug5497Workaround

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        AndroidBug5497Workaround.assistActivity(this)
    }
}