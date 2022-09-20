package com.qwuiteam.project.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import kotlin.properties.Delegates

open class TouchRootLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs), View.OnTouchListener {

    var maxHeight by Delegates.notNull<Int>()
    private var touchSlop: Int = ViewConfiguration.get(getContext()).scaledTouchSlop
    private var xCoOrdinate: Float = 0.0f
    private var mWidth = 0
    private var mHeight = 0
    var lastX: Float = 0.0f
    var lastY: Float = 0.0f
    private var yCoOrdinate: Float = 0.0f
    private val currentState = State.EDIT_MOVE
    private var isMove: Boolean = false
    private var navigationBarHeight: Int = 0
    var onClickListener: (() -> Unit)? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        setOnTouchListener(this)
        maxHeight = ScreenUtils.getScreenHeight()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        if (currentState != State.EDIT_MOVE) return false;

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                xCoOrdinate = view.x - event.rawX;
                yCoOrdinate = view.y - event.rawY;
                isMove = false
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                var x = event.rawX + xCoOrdinate
                var y = event.rawY + yCoOrdinate
                if (x < 0) x = 0f
                val screenWidth = ScreenUtils.getScreenWidth()
                if (x > screenWidth - mWidth) {
                    x = (screenWidth - mWidth).toFloat()
                }

//                if (y < SizeUtils.dp2px(200f)) {
//                    y = SizeUtils.dp2px(200f).toFloat()
//                }

                Log.d("liuyuzhe", "yyyyy: " + y);

                val screenHeight = ScreenUtils.getAppScreenHeight()
                val justHeight = (screenHeight - mHeight - SizeUtils.dp2px(100f)).toFloat()
                if (maxHeight > 0 && y > maxHeight - mHeight) {
                    Log.d("liuyuzhe", "maxHeight: " + (maxHeight-mHeight));
                    y = (maxHeight - mHeight).toFloat()
                } else if (y > justHeight) {
                    y = justHeight
                }


                if (Math.abs(x - lastX) > touchSlop || Math.abs(y - lastY) > touchSlop) {
                    isMove = true
                }
                view.animate()
                    .x(x)
                    .y(y)
                    .setDuration(0).start();
                lastX = x
                lastY = y
            }
            MotionEvent.ACTION_UP -> {
                if (!isMove) {
                    onClickListener?.invoke()
                }
                isMove = false
            }
        }
        return true
    }


    enum class State {
        EDIT_MOVE, NONE

    }

    fun isMoving() = isMove
}
