package com.qwuiteam.project.fragment

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ReplacementSpan
import android.view.View
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.SpanUtils.ALIGN_CENTER
import com.qwuiteam.project.R
import com.qwuiteam.project.view.CenterImageSpan
import kotlinx.android.synthetic.main.fragment_span.*


/**
 * id
 */
class SpanFragment : BaseFragment() {
    companion object {
        val TAG = SpanFragment::class.java.simpleName
    }

    override fun getLayoutId(): Int = R.layout.fragment_span

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        SpanUtils.with(textView)
//            .append("cc拉屎啦苏卡达拉屎啦苏卡达拉屎啦苏卡达拉屎啦苏卡达拉屎啦苏卡达aa")
//            .appendSpace(SizeUtils.dp2px(8f))
//            .appendImage(R.drawable.room_rules_icon, ALIGN_CENTER)
//            .appendSpace(SizeUtils.dp2px(8f))
//            .create()
        span1()
    }

    private fun span1() {
        val sp = SpannableStringBuilder("cc拉苏卡达aa")

        val start = sp.length
        sp.append("< >")
        sp.setSpan(SpaceSpan(SizeUtils.dp2px(6f)), start, sp.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val balanceIconPlaceHolder = "[icon]"
        sp.append(balanceIconPlaceHolder)
        val iconPlaceHolderIndex = sp.indexOf(balanceIconPlaceHolder)
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.room_rules_icon)!!
        val dp20 = SizeUtils.dp2px(20f)
        drawable.setBounds(0, 0, dp20, dp20)
        val imageSpan = CenterImageSpan(drawable)
        sp.setSpan(imageSpan, iconPlaceHolderIndex, sp.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

//        sp.setSpan()
        textView.text = sp
    }
}


class SpaceSpan(private val width: Int, color: Int = Color.TRANSPARENT) : ReplacementSpan() {
    private val paint = Paint()
    override fun getSize(
        paint: Paint, text: CharSequence,
        @IntRange(from = 0) start: Int,
        @IntRange(from = 0) end: Int,
        fm: FontMetricsInt?
    ): Int {
        return width
    }

    override fun draw(
        canvas: Canvas, text: CharSequence,
        @IntRange(from = 0) start: Int,
        @IntRange(from = 0) end: Int,
        x: Float, top: Int, y: Int, bottom: Int,
        paint: Paint
    ) {
        canvas.drawRect(x, top.toFloat(), x + width, bottom.toFloat(), this.paint)
    }

    init {
        paint.color = color
        paint.style = Paint.Style.FILL
    }
}
