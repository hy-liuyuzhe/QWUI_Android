package com.qwuiteam.project.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.Utils
import com.qwuiteam.project.databinding.LayoutRoomPkProgressbarBinding
import kotlinx.android.synthetic.main.fragment_pk.*

class RoomPkProgressBarLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    private val binding =
        LayoutRoomPkProgressbarBinding.inflate(LayoutInflater.from(Utils.getApp()), this, true)

    init {
        binding.progressView.leftStartColor = getLeftStartColor()
        binding.progressView.leftEndColor = getLeftEndColor()
        binding.progressView.rightStartColor = getRightStartColor()
        binding.progressView.rightEndColor = getRightEndColor()
        binding.progressView.callBack = callBack@{ leftWidth ->
            Log.d("liuyuzhe", "leftWidth: " + leftWidth);
            val flagWidthHalf = binding.imageProgressFlag.width / 2
            if (binding.imageProgressFlag.x == leftWidth - flagWidthHalf) {
                Log.d("liuyuzhe", "位置没有变: ");
                return@callBack
            }
            binding.imageProgressFlag.x = leftWidth - flagWidthHalf
        }
    }

    fun getLeftStartColor() = Color.parseColor("#F73450")
    fun getLeftEndColor() = Color.parseColor("#DD29EA")
    fun getRightStartColor() = Color.parseColor("#05CAF1")
    fun getRightEndColor() = Color.parseColor("#0647E7")

    fun update(leftValue: Float, rightValue: Float) {
        if (height == 0) {
            post { realUpdate(leftValue, rightValue) }
            return
        }
        realUpdate(leftValue, rightValue)
    }

    private fun realUpdate(leftValue: Float, rightValue: Float) {
        val leftText = leftValue.toInt().toString()
        binding.textLeft.text = leftText
        val rightText = rightValue.toInt().toString()
        binding.textRight.text = rightText

        justMinProgressBarTipWidth(leftValue, rightValue, leftText, rightText)
        binding.progressView.update(leftValue, leftValue + rightValue)
    }

    private fun justMinProgressBarTipWidth(
        leftValue: Float,
        rightValue: Float,
        leftText: String,
        rightText: String
    ) {
        binding.progressView.leftMinWidth = 0f
        binding.progressView.rightMinWidth = 0f
        if (leftValue < rightValue) {
            val textWidth = binding.textLeft.paint.measureText(leftText)
            Log.d("liuyuzhe", "binding.textLeft.width: " + textWidth);
            binding.progressView.leftMinWidth =
                textWidth + (binding.imageProgressFlag.width / 2 + SizeUtils.dp2px(4f))
        } else {
            val textWidth = binding.textRight.paint.measureText(rightText)
            Log.d("liuyuzhe", "binding.rightText.width: " + textWidth);
            binding.progressView.rightMinWidth =
                textWidth + (binding.imageProgressFlag.width / 2 + SizeUtils.dp2px(4f))
        }
    }


}