package com.qwuiteam.project.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.Utils
import com.qwuiteam.project.databinding.LayoutRoomPkProgressbarBinding

class RoomPkProgressBarLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    private val binding =
        LayoutRoomPkProgressbarBinding.inflate(LayoutInflater.from(Utils.getApp()), this, true)

    init {
        //height
        //radius
        binding.leftSpace.setBackgroundColor(getLeftStartColor())
        binding.rightSpace.setBackgroundColor(getRightEndColor())
        binding.progressView.leftStartColor = getLeftStartColor()
        binding.progressView.leftEndColor = getLeftEndColor()
        binding.progressView.rightStartColor = getRightStartColor()
        binding.progressView.rightEndColor = getRightEndColor()
        binding.progressView.callBack = callBack@{ leftWidth ->
            Log.d("liuyuzhe", "leftWidth: " + leftWidth);
            val leftRealWidth = leftWidth + binding.leftSpace.width
            val flagWidthHalf = binding.imageProgressFlag.width / 2
            if (binding.imageProgressFlag.x == leftRealWidth - flagWidthHalf){
                Log.d("liuyuzhe", "位置没有变: ");
                return@callBack
            }
            binding.imageProgressFlag.x = leftRealWidth - flagWidthHalf
            Log.d("liuyuzhe", "文字太多: ");
            val offset = SizeUtils.dp2px(2f)
            setView(binding.leftSpace, binding.textLeft.width + flagWidthHalf+offset)
            setView(binding.rightSpace, binding.textRight.width + flagWidthHalf+offset)
        }
    }

    private fun setView(view: View, size: Int) {
        val layoutParams = view.layoutParams
        layoutParams.width = size
        view.layoutParams = layoutParams
    }

    fun getLeftStartColor() = Color.parseColor("#F73450")
    fun getLeftEndColor() = Color.parseColor("#DD29EA")
    fun getRightStartColor() = Color.parseColor("#05CAF1")
    fun getRightEndColor() = Color.parseColor("#0647E7")

    fun update(leftValue: Float, rightValue: Float) {
        binding.textLeft.text = leftValue.toInt().toString()
        binding.textRight.text = rightValue.toInt().toString()
        binding.progressView.update(leftValue, leftValue + rightValue)
        if (leftValue < rightValue) {

        }
    }


}