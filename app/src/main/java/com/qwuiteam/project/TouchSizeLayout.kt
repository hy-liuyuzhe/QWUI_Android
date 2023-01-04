package com.qwuiteam.project

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout
import com.blankj.utilcode.util.SizeUtils

class TouchSizeLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    val offset = SizeUtils.dp2px(30f)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val viewHeight = height
        val middleLine = viewHeight / 2
        Log.d("liuyuzhe", "viewHeight: $viewHeight , middleLine: $middleLine");

        Log.d("liuyuzhe", "y: ${event.y}");
        isClickable = isRange(event.y.toInt(), middleLine - offset, middleLine + offset)
        return super.onTouchEvent(event)
    }

    fun isRange(curtY: Int, start: Int, end: Int): Boolean {
        Log.d("liuyuzhe", "start: $start , end: $end");
        val result = start < curtY && curtY < end

        Log.d("liuyuzhe", "can click: $result");
        return result
    }


}