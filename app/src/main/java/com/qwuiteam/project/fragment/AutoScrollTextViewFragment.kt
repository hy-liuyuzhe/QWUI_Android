package com.qwuiteam.project.fragment

import android.os.Bundle
import android.view.View
import com.qwuiteam.project.R
import kotlinx.android.synthetic.main.fragment_auto_text.*

class AutoScrollTextViewFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_auto_text


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        autoText.isSelected = true
    }
}