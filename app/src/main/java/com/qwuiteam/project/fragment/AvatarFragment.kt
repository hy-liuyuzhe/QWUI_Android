package com.qwuiteam.project.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.animation.addListener
import androidx.core.animation.doOnStart
import com.qwuiteam.project.R
import com.qwuiteam.project.gone
import com.qwuiteam.project.visible
import kotlinx.android.synthetic.main.fragment_avatar.*

/**
 * id
 */
class AvatarFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_avatar

    val d: Long = 300
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val objectAnimator =  ObjectAnimator.ofFloat(animate1,"scaleX")
//        val objectAnimator =  ObjectAnimator.ofFloat(animate1,"")
        end_iv.setOnClickListener {
            Log.i("TAG_avatar", "group: ")
            initViews(layer)
            layer.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(d)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        layer.visible()
                    }
                })
                .setUpdateListener {

                }
                .start()
        }

        btn.setOnClickListener {
            startAnimation(animate1, 0)
            startAnimation(animate2, 100)
            startAnimation(animate3, 200)

        }

        start_iv.setOnClickListener {
            Log.i("TAG_avatar", "onViewCreated: ")
            initViews(animate1)
            initViews(animate2)
            initViews(animate3)

            animate1.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(d)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        animate1.visible()
                    }
                })
                .start()
            animate2.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(d)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        animate2.visible()
                    }
                })
                .startDelay = 100
            animate3.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(d)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        animate3.visible()
                    }
                })
                .startDelay = 200
        }
    }

    private fun startAnimation(target: View, delayDuration: Long) {
        AnimatorSet()
            .apply {
            playTogether(
                ObjectAnimator.ofFloat(target, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(target, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(target, "alpha", 0f, 1f)
            )
            duration = 500
            startDelay = delayDuration
            start()
        }
            .doOnStart {

            }

        target.postDelayed({
            target.visible()
        }, delayDuration)
    }

    private fun initViews(target: View) {
        target.scaleX = 0f
        target.scaleY = 0f
        target.alpha = 0f
    }
}