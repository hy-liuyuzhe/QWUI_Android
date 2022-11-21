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
            R.id.lcp -> {
//                val r = longestCommonPrefix(arrayOf("flower", "flow", "flight"))
                val r = longestCommonPrefix2(arrayOf("flower", "1flow", "2flight"))
                LogUtils.d("longestCommonPrefix: $r")
            }
            R.id.palindrome -> {
                palindrome2(121).also { LogUtils.d("r: $it"); }
            }
        }
    }

    /**
     * 取出第一个单词的每个字符，依次和下面的每个单词比较
     */
    private fun longestCommonPrefix2(list: Array<String>): String {
        val firstLetterLength = list[0].length
        for (i in 0..firstLetterLength) {
            val c = list[0].get(i)
            //分别遍历每个单词的列对应的字符
            list.forEachIndexed { index, s ->
                if (index != 0) {
                    //目前单词已经遍历到头了
                    if (i == s.length) {
                        return list[0].substring(0, i)
                    }
                    //比较的单词字符不一致就返回
                    val targetC = s[i]
                    if (targetC != c) {
                        return list[0].substring(0, i)
                    }
                }
            }
        }

        return list[0]
    }

    private fun longestCommonPrefix(list: Array<String>): String {
        var prefix = list.first()
        list.forEachIndexed { index, s ->
            if (index != 0) {
                prefix = longestCommonPrefix(prefix, s)
            }
        }
        return prefix
    }

    private fun longestCommonPrefix(str1: String, str2: String): String {
        var index = 0
        val count = Math.min(str1.length, str2.length)
        while (index < count && str1[index] == str2[index]) {
            index++
        }
        return str1.substring(0, index)
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
