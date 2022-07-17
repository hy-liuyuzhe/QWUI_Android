package com.qwuiteam.project.fragment

import android.content.ContentProvider
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.qwuiteam.project.R
import com.qwuiteam.project.viewmodel.EditViewModel
import kotlinx.android.synthetic.main.fragment_layout.*

/**
 * id
 */
class IdImageFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_id_image


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editViewModel = ViewModelProvider(this).get("123", EditViewModel::class.java)
        Log.d("wwq", "IdImageFragment.editViewModel: "+editViewModel.hashCode());
    }
}