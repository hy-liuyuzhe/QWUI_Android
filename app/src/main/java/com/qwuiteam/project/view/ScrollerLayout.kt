package com.qwuiteam.project.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.Scroller
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.qwuiteam.project.R
import java.util.concurrent.TimeUnit

class ScrollerLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

    val scroller by lazy { Scroller(context) }
    val duration = TimeUnit.MINUTES.toMillis(1)


    fun smoothScrollTo(destX: Int, destY: Int) {
        LogUtils.d("smoothScrollTo")
        val target = this
        val scrollY = target.scrollY
        val delta = destY - scrollY
        scroller.startScroll(destX, scrollY, destX, delta, duration.toInt())
        invalidate();
    }

    override fun computeScroll() {
        LogUtils.d("computeScroll")
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            postInvalidate();
        }
    }

}