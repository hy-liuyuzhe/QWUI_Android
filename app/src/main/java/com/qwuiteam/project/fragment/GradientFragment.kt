package com.qwuiteam.project.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.qwuiteam.project.R
import com.qwuiteam.project.utils.GlideEngine
import kotlinx.android.synthetic.main.fragment_choose_photo.*
import kotlinx.android.synthetic.main.fragment_layout.*


/**
 * 动态设置layoutParams的类型要和add的父类一致，否则你设置的属性无效（宽高除外）
 */
class GradientFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_gradient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}