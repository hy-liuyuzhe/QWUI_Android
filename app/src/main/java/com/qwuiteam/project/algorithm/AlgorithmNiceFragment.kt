package com.qwuiteam.project.algorithm

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import com.blankj.utilcode.util.*
import com.qwuiteam.project.R
import com.qwuiteam.project.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_algorithm.*
import kotlinx.android.synthetic.main.layout_list_item.view.*

/**
 * id
 */
class AlgorithmNiceFragment : BaseFragment(), View.OnClickListener {

    override fun getLayoutId(): Int = R.layout.fragment_algorithm

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val host = this
        layoutClick.children.forEach {
            LogUtils.d("children ${it.text}")
            it.setOnClickListener(host)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.palindrome -> {
                palindrome2(121).also { LogUtils.d("r: $it"); }
            }
        }
    }

    private fun palindrome(x: Int): Boolean {
        if (x < 0) return false
        var curt = 0
        var num = x
        while (num != 0) {
            curt = curt * 10 + num % 10
            num /= 10
        }
        return x == curt
    }

    private fun palindrome2(x: Int): Boolean {
        if (x < 0) return false
        var curt = 0
        var num = x
        while (num > curt) {
            curt = curt * 10 + num % 10
            num /= 10
        }
        return num == curt || num == curt / 10
    }
}
