package com.qwuiteam.project.fragment

import android.content.ContentProvider
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.qwuiteam.project.R
import kotlinx.android.synthetic.main.fragment_layout.*
import kotlinx.android.synthetic.main.fragment_string.*

/**
 * id
 */
class StringFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_string


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        start_with.setOnClickListener {
            val all = "https://passport.odaily.news/api/mobi-investor/appversion?app=0&os=android"
            val target = "https://passport.odaily.news/"

            val result = all.startsWith(target)
            Log.d("liuyuzhe", "result: "+result);
        }

        start_with2.setOnClickListener {
            val all = "http://www.odaily.news/pp/api/hot-word"
            val target = "http://www.odaily.news/"

            val result = all.startsWith(target)
            Log.d("liuyuzhe", "result: "+result);
        }
    }
}