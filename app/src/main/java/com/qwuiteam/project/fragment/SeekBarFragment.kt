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
import kotlinx.android.synthetic.main.fragment_layout.*
import kotlinx.android.synthetic.main.fragment_seek_bar.*
import kotlinx.android.synthetic.main.fragment_string.*


class SeekBarFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_seek_bar


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        positive.setOnClickListener {
            LogUtils.d("scroll x to 100")
            layoutView.scrollBy(100,0)
        }
        negative.setOnClickListener {
            LogUtils.d("scroll x to -100")
            layoutView.scrollBy(-100,0)
        }


    }
}