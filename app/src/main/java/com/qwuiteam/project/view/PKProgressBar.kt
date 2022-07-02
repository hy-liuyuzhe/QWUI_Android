package com.qwuiteam.project.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.qwuiteam.project.utils.dp2px

/**
 *
 * Description:pk血条
 * Author: zw
 * CreateDate: 2021/11/6 10:35
 */
class PKProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle), IPKView {

    private var mProgressRate = 0f
    private var mTotalScore = 0f
    private var mMyScore = 0f
    private var mOtherScore = 0f
    private var mMyScoreStr = ""
    private var mOtherScoreStr = ""

    private var mMyPaint = Paint()
    private var mOtherPaint = Paint()
    private var mMyTextPaint = Paint()
    private var mOtherTextPaint = Paint()

    private var mMyRectF = RectF()
    private var mOtherRectF = RectF()
    private var mCorners = 2.dp2px().toFloat()

    /**
     * 是否正在播放动画
     */
    private var isPlaying = false

    /**
     * 当前的具体进度数值
     */
    private var distance = 0f

    /**
     * 左右两边的间距
     */
    private var mTextMargin = 15.dp2px().toFloat()

    /**
     * 左边的文案宽度加上左边间距的宽度
     */
    private var mLeftTextWidth = 0f

    /**
     * 右边的文案宽度加上右边间距的宽度
     */
    private var mRightTextWidth = 0f

    /**
     * 是否需要播放动画
     */
    var playAnimation = false

    /**
     * 回调
     */
    var block: ((Float) -> Unit)? = null

    /**
     * 进度图片的一半宽度
     */
    var progressBitmapWidth = 0

    init {
        //区分阿拉伯语左右的布局问题
        val leftColor = IntArray(2)
        val rightColor = IntArray(2)
        if (!isArabic(context)) {
            leftColor[0] = Color.parseColor("#FF0F3D")
            leftColor[1] = Color.parseColor("#FEB465")
            rightColor[0] = Color.parseColor("#57C1FF")
            rightColor[1] = Color.parseColor("#0277FE")
        } else {
            rightColor[1] = Color.parseColor("#FF0F3D")
            rightColor[0] = Color.parseColor("#FEB465")
            leftColor[1] = Color.parseColor("#57C1FF")
            leftColor[0] = Color.parseColor("#0277FE")
        }
        mMyPaint.apply {
            isAntiAlias = true
            shader = LinearGradient(
                0f, 0f, 500f, 0f, leftColor[0], leftColor[1],
                Shader.TileMode.CLAMP
            )
        }
        mOtherPaint.apply {
            isAntiAlias = true
            shader = LinearGradient(
                0f, 0f, 500f, 0f, rightColor[0], rightColor[1],
                Shader.TileMode.CLAMP
            )
        }
        mMyTextPaint.apply {
            isAntiAlias = true
            textSize = 12.dp2px().toFloat()
            color = Color.WHITE
        }
        mOtherTextPaint.apply {
            isAntiAlias = true
            textSize = 12.dp2px().toFloat()
            color = Color.WHITE
        }
    }

    /**
     * 一般是一开始的时候，播放一个滚动动画(产品要求)
     */
    private fun playStartAnimation() {
        var start = 0f
        val progress = mProgressRate
        if (progress < 1 - progress) {
            start = 1 - progress
        }
        ValueAnimator.ofFloat(start, progress).apply {
            duration = 2000
            isPlaying = true
            addUpdateListener {
                mProgressRate = it.animatedValue as Float
                calculateProgressRate()
                invalidate()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    isPlaying = false
                    playAnimation = false
                }
            })
            start()
        }
    }

    /**
     * 对进度值做一些矫正，防止越界绘制到文本区域遮住文本
     */
    private fun calculateProgressRate() {
        if (mProgressRate < (mLeftTextWidth + progressBitmapWidth) / width) {
            mProgressRate = (mLeftTextWidth + progressBitmapWidth) / width
        } else if (mProgressRate > (width - mRightTextWidth - progressBitmapWidth) / width) {
            mProgressRate = (width - mRightTextWidth - progressBitmapWidth) / width
        }
    }

    /**
     * 左边的绘制区域
     */
    private fun getMyRectF(): RectF {
        //播放动画的计算方式
        if (isPlaying) {
            val originRate = if (mTotalScore == 0f) 0.5f else mMyScore / mTotalScore
            val half = 0.5f
            when {
                originRate < half -> {
                    distance = fixLeftRate(originRate) * width
                    mMyRectF.set(0f, 0f, distance, measuredHeight.toFloat())
                }
                originRate == half -> {
                    distance = mProgressRate * width
                    mMyRectF.set(0f, 0f, distance, measuredHeight.toFloat())
                }
                else -> {
                    distance = mProgressRate * width
                    mMyRectF.set(0f, 0f, distance, measuredHeight.toFloat())
                }
            }
        } else {
            //非播放动画的计算方式
            distance = mProgressRate * width
            mMyRectF.set(0f, 0f, distance, measuredHeight.toFloat())
        }
        block?.invoke(distance)
        return mMyRectF
    }

    /**
     * 右边的绘制区域
     */
    private fun getOtherRectF(): RectF {
        //播放动画的计算方式
        if (isPlaying) {
            val originRate = if (mTotalScore == 0f) 0.5f else mOtherScore / mTotalScore
            when {
                originRate < 0.5f -> {
                    mOtherRectF.set(
                        width - fixRightRate(originRate) * width,
                        0f,
                        width.toFloat(),
                        measuredHeight.toFloat()
                    )
                }
                originRate == 0.5f -> {
                    mOtherRectF.set(
                        width - distance,
                        0f,
                        width.toFloat(),
                        measuredHeight.toFloat()
                    )
                }
                else -> {
                    mOtherRectF.set(
                        mProgressRate * width,
                        0f,
                        width.toFloat(),
                        measuredHeight.toFloat()
                    )
                }
            }
        } else {
            //非播放动画的计算方式
            mOtherRectF.set(
                distance,
                0f,
                width.toFloat(),
                measuredHeight.toFloat()
            )
        }
        return mOtherRectF
    }

    /**
     * 重置下，因为有复用的情况（同一个房间会多次发起PK）
     */
    override fun reset() {
        isPlaying = false
        playAnimation = false
    }

    override fun setTextSize(size: Int) {
        mMyTextPaint.textSize = size.dp2px().toFloat()
        mOtherTextPaint.textSize = size.dp2px().toFloat()
    }

    override fun setTestMargin(value: Int) {
        mTextMargin = value.dp2px().toFloat()
    }

    override fun update(myScore: Float, otherScore: Float) {
        if (isPlaying) return
        //阿拉伯语左右是反着来的，所以这里直接左右的值调换就可以了
        if (isArabic(context)) {
            judgeScoreProperty(otherScore, myScore)
        } else {
            judgeScoreProperty(myScore, otherScore)
        }
        scoreText()
        if (playAnimation) {
            //这里是第一次，需要播放一个慢慢滚动的动画
            playStartAnimation()
            return
        }
        calculateProgressRate()
        invalidate()
    }

    private fun isArabic(context: Context?): Boolean {
        return false
    }

    /**
     * 计算两边的数字值和百分比值
     */
    private fun judgeScoreProperty(myScore: Float, otherScore: Float) {
        mTotalScore = myScore + otherScore
        mMyScore = myScore
        mOtherScore = otherScore
        mProgressRate = mMyScore / mTotalScore
        //如果两边都为0的话，这里就把他们的进度值写死为0.5，就是各占二分之一
        if (mTotalScore == 0f) {
            mProgressRate = 0.5f
        }
    }

    /**
     * 是否需要播放一个缓慢滚动的动画
     */
    override fun playAnimation(play: Boolean) {
        playAnimation = play
    }

    /**
     * 正确计算左边的比例值（加上文本宽度和间距）
     */
    private fun fixLeftRate(rate: Float): Float {
        //最小进度不能小于文本宽度和间距
        var temp = rate
        val smallRate = (mTextMargin + mLeftTextWidth) / width
        if (temp < smallRate) {
            temp = smallRate
        }
        return temp
    }

    /**
     * 正确计算右边的比例值（加上文本宽度和间距）
     */
    private fun fixRightRate(rate: Float): Float {
        //最小进度不能小于文本宽度和间距
        var temp = rate
        if (temp < (mTextMargin + mRightTextWidth) / width) {
            temp = (mTextMargin + mRightTextWidth) / width
        }
        return temp
    }

    /**
     * 进度数字值转换为文本
     */
    private fun scoreText() {
        mMyScoreStr = "${mMyScore.toInt()}"
        mOtherScoreStr = "${mOtherScore.toInt()}"
        //左边的间距加上文本宽度
        mLeftTextWidth = mMyTextPaint.measureText(mMyScoreStr) + mTextMargin
        //右边的间距加上文本宽度
        mRightTextWidth = mMyTextPaint.measureText(mOtherScoreStr) + mTextMargin
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mMyScoreStr.isEmpty() || mOtherScoreStr.isEmpty()) return
        //绘制左边进度背景
        canvas.drawRoundRect(getMyRectF(), mCorners, mCorners, mMyPaint)
        //绘制右边进度背景
        canvas.drawRoundRect(getOtherRectF(), mCorners, mCorners, mOtherPaint)
        //绘制左边文本
        canvas.drawText(mMyScoreStr, mTextMargin, baseLine(), mMyTextPaint)
        val dx = measuredWidth - mMyTextPaint.measureText(mOtherScoreStr) - mTextMargin
        //绘制右边文本
        canvas.drawText(mOtherScoreStr, dx, baseLine(), mOtherTextPaint)
    }

    /**
     * 文本绘制基线
     */
    private fun baseLine() =
        (measuredHeight - (mMyTextPaint.descent() - mMyTextPaint.ascent())) / 2 - mMyTextPaint.ascent()

}