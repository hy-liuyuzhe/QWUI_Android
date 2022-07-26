package com.qwuiteam.project.fragment

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.*
import com.qwuiteam.project.R
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog
import kotlinx.android.synthetic.main.fragment_xlog.*

/**
 * id
 */
class XlogFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_xlog

    lateinit var xlog: Xlog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        xlog = Xlog();
        Log.setLogImp(xlog);
        Log.setConsoleLogOpen(true)
        create.setOnClickListener {
            xlog.logE(1, "2", "3", "4", 5, 6, 7, 8, "create")
        }
        flush.setOnClickListener {
            xlog.appenderFlush(1, true)
            LogUtils.d("flush");
        }
    }
}