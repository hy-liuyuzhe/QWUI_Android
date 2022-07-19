package com.qwuiteam.project.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

class RoomPkProgressBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    var rightEndColor: Int = 0
    var rightStartColor: Int = 0
    var leftStartColor: Int = 0
    var leftEndColor: Int = 0
    var callBack: ((Float) -> Unit)? = null
    private var viewHeight: Int = 0
    private var viewWidth: Int = 0
    private var percentage: Float = -0.1f
    private var leftGradient: LinearGradient? = null
    private var rightGradient: LinearGradient? = null

    private val leftPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = Color.parseColor("#F73450")
        }
    }

    private val rightPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = Color.parseColor("#0647E7")
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        viewHeight = h
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (percentage < 0 || viewHeight == 0) return
        drawTrack(canvas)
    }


    private fun drawTrack(canvas: Canvas) {
        val leftWidth = viewWidth * percentage
        callBack?.invoke(leftWidth)
        if (leftGradient == null) {
            leftGradient = LinearGradient(
                0f, 0f, leftWidth, viewHeight.toFloat(), leftStartColor, leftEndColor,
                Shader.TileMode.CLAMP
            )
        }
        leftPaint.shader = leftGradient
        canvas.drawRect(0f, 0f, leftWidth, viewHeight.toFloat(), leftPaint)
        if (rightGradient == null) {
            rightGradient = LinearGradient(
                leftWidth,
                0f,
                viewWidth.toFloat(),
                viewHeight.toFloat(),
                rightStartColor,
                rightEndColor,
                Shader.TileMode.CLAMP
            )
        }
        rightPaint.shader = rightGradient
        canvas.drawRect(leftWidth, 0f, viewWidth.toFloat(), viewHeight.toFloat(), rightPaint)
    }

    fun update(progress: Float, max: Float) {
        if (max == 0f) {
            percentage = 0.5f
        } else {
            percentage = progress / max
        }
        Log.d("liuyuzhe", "update: " + progress);
        leftGradient = null
        rightGradient = null
        invalidate()
    }

}