package com.qwuiteam.project.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.FrameLayout
import com.blankj.utilcode.util.Utils
import com.qwuiteam.project.databinding.FragmentSenderBinding
import com.qwuiteam.project.databinding.LayoutSenderBinding
import kotlinx.android.synthetic.main.fragment_pk.*

class SenderLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {


    private val binding = LayoutSenderBinding.inflate(LayoutInflater.from(Utils.getApp()), this, true)

    lateinit var sendListener: OnTouchListener

    init {
        setOnClickListener {
            Log.d("liuyuzhe", "onclick: setOnTouchListener(sendListener)");
        }


        sendListener = OnTouchListener { v, event ->
            Log.d("liuyuzhe", "内部监听: ");
            when (event.action) {
                MotionEvent.ACTION_DOWN -> v.animate().scaleX(0.8f).scaleY(0.8f)
                    .setDuration(50).start()
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> v.animate().scaleX(1.0f)
                    .scaleY(1.0f).setDuration(200).start()
            }
            Log.d("liuyuzhe", "return super.onTouchEvent(event): ");
            return@OnTouchListener false
        }
        Log.d("liuyuzhe", "setOnTouchListener(null): ");
        setOnTouchListener(sendListener)
    }

}