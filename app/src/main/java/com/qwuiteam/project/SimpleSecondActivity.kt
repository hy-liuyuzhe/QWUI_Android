package com.qwuiteam.project

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.activity_simple.*

class SimpleSecondActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(
                Intent(context, SimpleSecondActivity::class.java).putExtra("page", PageContainer.task)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)
        LogUtils.d("onCreate: $savedInstanceState")
        val pageContainer = intent.getSerializableExtra("page") as PageContainer
        titleBar.title = pageContainer.title
        supportFragmentManager.beginTransaction().replace(
            R.id.container,
            pageContainer.targetClass.newInstance(), pageContainer.targetClass.simpleName
        ).commitAllowingStateLoss()
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val p = intent?.getSerializableExtra("page")
        LogUtils.d("onNewIntent: $p")
    }

}