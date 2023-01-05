package com.qwuiteam.project.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates


var canScrollVerticallyF by Delegates.notNull<Boolean>()

class FRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : RecyclerView(context, attrs, defStyleAttr) {

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
//        canScrollVerticallyF = canScrollVertically(1)
//        Log.d("liuyuzhe", "onInterceptTouchEvent.canScrollVertically: $canScrollVerticallyF");
//        parent?.requestDisallowInterceptTouchEvent(canScrollVerticallyF)
        return super.onInterceptTouchEvent(e)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        canScrollVerticallyF = canScrollVertically(1)
        Log.d("liuyuzhe", "dispatchTouchEvent.canScrollVertically: $canScrollVerticallyF");
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
//        val canScrollVertically = canScrollVertically(1)
//        Log.d("liuyuzhe", "onTouchEvent.canScrollVertically: "+canScrollVertically);
//        parent?.requestDisallowInterceptTouchEvent(canScrollVertically)
        return super.onTouchEvent(e)
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        return false
    }

    override fun canScrollVertically(direction: Int): Boolean {
        val canScrollVertically = super.canScrollVertically(direction)
        Log.d("liuyuzhe", "canScrollVertically: "+canScrollVertically);
        return canScrollVertically
    }
}