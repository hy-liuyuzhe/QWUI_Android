package com.qwuiteam.project.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SizeUtils
import com.qwuiteam.project.R

class OutlineTextView : AppCompatTextView {

    private val defaultStrokeWidth = 0F
    private var isDrawing: Boolean = false

    private var strokeColor: Int = 0
    private var strokeWidth: Float = 0.toFloat()

    constructor(context: Context) : super(context) {
        initResources(context, null)

    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initResources(context, attrs)

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initResources(context, attrs)

    }

    private fun initResources(context: Context?, attrs: AttributeSet?) {
        if (attrs != null) {
//            val a = context?.obtainStyledAttributes(attrs, R.styleable.outlineAttrs)
//            strokeColor = a!!.getColor(R.styleable.outlineAttrs_outlineColor,
//                    currentTextColor)
//            strokeWidth = a.getFloat(R.styleable.outlineAttrs_outlineWidth,
//                    defaultStrokeWidth)

//            a.recycle()
        } else {
        }
        strokeColor = Color.parseColor("#FFB6450B")
        strokeWidth = SizeUtils.dp2px(1f).toFloat()
        setStrokeWidth(strokeWidth)
    }

    fun setStrokeColor(color: Int) {
        strokeColor = color
    }

    /**
     *  give value in sp
     */
    fun setStrokeWidth(width: Float) {
//        strokeWidth = width.toPx(context)
    }

    fun setStrokeWidth(unit: Int, width: Float) {
        strokeWidth = TypedValue.applyDimension(
                unit, width, context.resources.displayMetrics)
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        Log.d("liuyuzhe", "setText: ");
    }

    override fun invalidate() {
        Log.d("liuyuzhe", "invalidate isDrawing: $isDrawing");
        if (isDrawing) return
        super.invalidate()
    }

var count = 0
    override fun onDraw(canvas: Canvas) {
        Log.d("liuyuzhe", "onDraw count: $count");
        count++
        if (strokeWidth > 0) {
            isDrawing = true
            val p = paint
            p.style = Paint.Style.FILL

            super.onDraw(canvas)

            val currentTextColor = currentTextColor
            p.style = Paint.Style.STROKE
            p.strokeWidth = strokeWidth
            setTextColor(strokeColor)
            super.onDraw(canvas)
            setTextColor(currentTextColor)
            isDrawing = false
        } else {
            super.onDraw(canvas)
        }
    }

}

