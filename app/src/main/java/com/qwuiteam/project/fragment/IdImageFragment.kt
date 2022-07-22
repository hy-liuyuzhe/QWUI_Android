package com.qwuiteam.project.fragment

import android.content.ContentProvider
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import com.blankj.utilcode.util.*
import com.qwuiteam.project.R
import com.qwuiteam.project.utils.HkCountDownTimer
import kotlinx.android.synthetic.main.fragment_layout.*

/**
 * id
 */
class IdImageFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_id_image


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //30
        for (i in 0..600) {
            if (i % 90 == 0) {
                LogUtils.d("命中了： " + i);
            }
        }
    }
}