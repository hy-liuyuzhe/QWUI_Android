package com.qwuiteam.project

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout
import com.blankj.utilcode.util.SizeUtils
import com.opensource.svgaplayer.SVGAImageView

class SvgaTouchSizeLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : SVGAImageView(context, attrs, defStyleAttr) {

    private val offset = SizeUtils.dp2px(60f)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return super.onTouchEvent(event)
        val viewHeight = height
        val middleLine = viewHeight / 2
        Log.d("liuyuzhe", "viewHeight: $viewHeight , middleLine: $middleLine");

        Log.d("liuyuzhe", "y: ${event.y}");
        isClickable = isRange(event.y.toInt(), middleLine - offset, middleLine + offset)
        return super.onTouchEvent(event)
    }

//    override fun onTouchEvent(event: MotionEvent): Boolean {
//
//    }

    fun isRange(curtY: Int, start: Int, end: Int): Boolean {
        Log.d("liuyuzhe", "start: $start , end: $end");
        val result = start < curtY && curtY < end

        Log.d("liuyuzhe", "can click: $result");
        return result
    }


}