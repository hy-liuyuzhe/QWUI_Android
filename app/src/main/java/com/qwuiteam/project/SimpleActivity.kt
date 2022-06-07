package com.qwuiteam.project

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_simple.*

class SimpleActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context, pageContainer: PageContainer) {
            context.startActivity(
                Intent(context, SimpleActivity::class.java)
                    .putExtra("page", pageContainer)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)
        val pageContainer = intent.getSerializableExtra("page") as PageContainer
        titleBar.title = pageContainer.title
        supportFragmentManager.beginTransaction().replace(
            R.id.container,
            pageContainer.targetClass.newInstance(), pageContainer.targetClass.simpleName
        ).commitAllowingStateLoss()
    }
}