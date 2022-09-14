package com.qwuiteam.project.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SizeUtils
import com.qwuiteam.project.R

class CommonRadioButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : View(context, attrs) {

    private val outPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.strokeWidth = SizeUtils.dp2px(1F).toFloat()
        this.style = Paint.Style.STROKE
    }
    private val innerPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //dp16 dp16
        val viewWidth = width.toFloat()
        val viewHeight = height.toFloat()
        val viewRadius = (viewWidth / 2) - outPaint.strokeWidth

        val innerWidth = (width / 2).toFloat()
        val innerHeight = (height / 2).toFloat()

        canvas.translate(viewWidth / 2, viewHeight / 2)

        val zero = 0F
        canvas.drawCircle(zero, zero, viewRadius, outPaint)
        innerPaint.color =
            ColorUtils.getColor(if (isSelected) R.color.red_fd2772 else R.color.white)
        canvas.drawCircle(zero, zero, viewRadius / 2, innerPaint)
    }

}