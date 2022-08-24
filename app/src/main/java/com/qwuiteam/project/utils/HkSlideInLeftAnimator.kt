package com.qwuiteam.project.utils

import android.view.animation.Interpolator
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import jp.wasabeef.recyclerview.animators.BaseItemAnimator

open class HkSlideInLeftAnimator : BaseItemAnimator {
    constructor()
    constructor(interpolator: Interpolator) {
        this.interpolator = interpolator
    }

    override fun animateRemoveImpl(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate().apply {
            translationX(-holder.itemView.rootView.width.toFloat())
            duration = removeDuration
            interpolator = interpolator
            setListener(DefaultRemoveAnimatorListener(holder))
            startDelay = getRemoveDelay(holder)
        }.start()
    }

    override fun preAnimateAddImpl(holder: RecyclerView.ViewHolder) {
        holder.itemView.translationX = -holder.itemView.rootView.width.toFloat()
    }

    override fun animateAddImpl(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate().apply {
            translationX(0f)
            duration = addDuration
            interpolator = interpolator
            setListener(DefaultAddAnimatorListener(holder))
            startDelay = getAddDelay(holder)
        }.start()
    }

    override fun onAddFinished(item: RecyclerView.ViewHolder?) {
        LogUtils.d("onAddFinished");
    }

    override fun onChangeStarting(item: RecyclerView.ViewHolder?, oldItem: Boolean) {
        LogUtils.d("onChangeStarting");
    }

    override fun onRemoveFinished(item: RecyclerView.ViewHolder?) {
        LogUtils.d("onRemoveFinished");
    }
}
