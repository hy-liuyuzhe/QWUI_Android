package com.qwuiteam.project.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : RecyclerView(context, attrs, defStyleAttr) {

    var isIntercept = false

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        Log.d("liuyuzhe", "onInterceptTouchEvent.canScrollVerticallyF: $canScrollVerticallyF");
        if (isScrollOnTop()) {
            Log.d("liuyuzhe", "HRecyclerView 滑动到顶部");
        } else {
            Log.d("liuyuzhe", "HRecyclerView 滑动到底部");
        }

        if (!canScrollVerticallyF) {
            Log.d("liuyuzhe", "HRecyclerView 父类滑动到底部了");
            if (isScrollOnTop() && isIntercept) {
                Log.d("liuyuzhe", "HRecyclerView 事件给父类");
                isIntercept = false
                parent?.requestDisallowInterceptTouchEvent(false)
            } else {
                isIntercept = true
                Log.d("liuyuzhe", "HRecyclerView 拦截事件");
                parent?.requestDisallowInterceptTouchEvent(true)
                return true
            }
        } else {
            Log.d("liuyuzhe", "HRecyclerView 父类在控制");
            isIntercept = false
            parent?.requestDisallowInterceptTouchEvent(false)
        }


        return super.onInterceptTouchEvent(e)
    }

    private fun isScrollOnTop(): Boolean {
        val l = layoutManager as LinearLayoutManager
        return l.findFirstCompletelyVisibleItemPosition() == 0
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        Log.d("liuyuzhe", "onTouchEvent: ");
//        parent?.requestDisallowInterceptTouchEvent(true)
        return super.onTouchEvent(e)
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        return false
    }
}