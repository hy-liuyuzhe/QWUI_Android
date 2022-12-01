package com.qwuiteam.project.algorithm

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import com.blankj.utilcode.util.*
import com.qwuiteam.project.R
import com.qwuiteam.project.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_file_store.*
import org.json.JSONObject
import java.io.File

/**
 * id
 */
class FileStoreFragment : BaseFragment(), View.OnClickListener {

    val fileName = "deviceID"

    override fun getLayoutId(): Int = R.layout.fragment_file_store

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val host = this
        layoutClick.children.forEach {
            it.setOnClickListener(host)
        }

        LogUtils.d("getIPAddressV6: ${NetworkUtils.getIPAddress(false)}")
        LogUtils.d("getIPAddressV4: ${NetworkUtils.getIPAddress(true)}")
        LogUtils.d("getIpAddressByWifi: ${NetworkUtils.getIpAddressByWifi()}")
        LogUtils.d("getBroadcastIpAddress: ${NetworkUtils.getBroadcastIpAddress()}")
    }

    /**
     * no vpn
    2022-11-22 14:03:46.549 2541-2541/com.qwuiteam.project D/liuyuzhe:
    ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
    │ main, com.qwuiteam.project.algorithm.FileStoreFragment.onViewCreated(FileStoreFragment.kt:29)
    ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
    │ getIPAddressV6: FE80::B6C4:FCFF:FEF3:F74A
    └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
    2022-11-22 14:03:46.554 2541-2541/com.qwuiteam.project D/liuyuzhe:
    ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
    │ main, com.qwuiteam.project.algorithm.FileStoreFragment.onViewCreated(FileStoreFragment.kt:30)
    ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
    │ getIPAddressV4: 192.168.10.7
    └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
    2022-11-22 14:03:46.559 2541-2541/com.qwuiteam.project D/liuyuzhe:
    ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
    │ main, com.qwuiteam.project.algorithm.FileStoreFragment.onViewCreated(FileStoreFragment.kt:31)
    ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
    │ getIpAddressByWifi: 192.168.10.7
    └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
    2022-11-22 14:03:46.564 2541-2541/com.qwuiteam.project D/liuyuzhe:
    ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
    │ main, com.qwuiteam.project.algorithm.FileStoreFragment.onViewCreated(FileStoreFragment.kt:32)
    ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
    │ getBroadcastIpAddress: 192.168.10.255
    └────────────────────────────────────────────────────────────────────────────────────────────────────────────────


    vpn

    2022-11-22 14:04:32.303 2541-2541/com.qwuiteam.project D/liuyuzhe:
    ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
    │ main, com.qwuiteam.project.algorithm.FileStoreFragment.onViewCreated(FileStoreFragment.kt:29)
    ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
    │ getIPAddressV6: FE80::B6C4:FCFF:FEF3:F74A
    └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
    2022-11-22 14:04:32.308 2541-2541/com.qwuiteam.project D/liuyuzhe:
    ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
    │ main, com.qwuiteam.project.algorithm.FileStoreFragment.onViewCreated(FileStoreFragment.kt:30)
    ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
    │ getIPAddressV4: 26.26.26.1
    └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
    2022-11-22 14:04:32.309 2541-2541/com.qwuiteam.project D/liuyuzhe:
    ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
    │ main, com.qwuiteam.project.algorithm.FileStoreFragment.onViewCreated(FileStoreFragment.kt:31)
    ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
    │ getIpAddressByWifi: 192.168.10.7
    └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
    2022-11-22 14:04:32.313 2541-2541/com.qwuiteam.project D/liuyuzhe:
    ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
    │ main, com.qwuiteam.project.algorithm.FileStoreFragment.onViewCreated(FileStoreFragment.kt:32)
    ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
    │ getBroadcastIpAddress: 192.168.10.255
    └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
     */
    override fun onClick(v: View) {
        when (v.id) {
            R.id.read -> {
                val externalDownloadsPath = PathUtils.getExternalDownloadsPath()
                val file = File(externalDownloadsPath + "/" + AppUtils.getAppPackageName(), fileName)
                val text = FileIOUtils.readFile2String(file)
                LogUtils.d("read text: ${text}")
            }
            R.id.write -> {
                val externalDownloadsPath = PathUtils.getExternalDownloadsPath()
                val dir =
                    File(externalDownloadsPath + "/" + AppUtils.getAppPackageName())
                val file = File(dir, fileName)
                FileUtils.createOrExistsDir(dir)
                val createFile = FileUtils.createOrExistsFile(file)
                LogUtils.d("createFile.r: $createFile" + ", path: ${file.absolutePath}")
//                val j = JSONObject()
//                j.put("deviceId", "111aaa")
                val r = FileIOUtils.writeFileFromString(file, "☠↬ۦོ͢✰͢⇣͢      ✰͜✘                 ✘͢͢\u2066  ✘͢͢ۦ✰ོ͜͢✘͢͢ۦོ͢↬✰͜͢͡✘✰͜͢͡ۦོ͢✰͢⇣͢✘͢͢\u2066ۦ↬ۦོ͢✰͢⇣͢✰͜✘͢͢\u2066 ☠↬ۦོ͢✰͢⇣͢      ✰͜✘                 ✘͢͢\u2066  ✘͢͢ۦ✰ོ͜͢✘͢͢ۦོ͢↬✰͜͢͡✘✰͜͢͡ۦོ͢✰͢⇣͢✘͢͢\u2066ۦ↬ۦོ͢✰͢⇣͢✰͜✘͢͢\u2066  ✘͢͢☠↬ۦོ͢✰͢⇣͢      ✰͜✘                 ✘͢͢\u2066  ✘͢͢ۦ✰ོ͜͢✘͢͢ۦོ͢↬✰͜͢͡✘✰͜͢͡ۦོ͢✰͢⇣͢✘͢͢\u2066ۦ↬ۦོ͢✰͢⇣͢✰͜✘͢͢\u2066  ✘͢͢☠↬ۦོ͢✰͢⇣͢      ✰͜✘                 ✘͢͢\u2066  ✘͢͢ۦ✰ོ͜͢✘͢͢ۦོ͢↬✰͜͢͡✘✰͜͢͡ۦོ͢✰͢⇣͢✘͢͢\u2066ۦ↬ۦོ͢✰͢⇣͢✰͜✘͢͢\u2066 ☠↬ۦོ͢✰͢⇣͢      ✰͜✘                 ✘͢͢\u2066  ✘͢͢ۦ✰ོ͜͢✘͢͢ۦོ͢↬✰͜͢͡✘✰͜͢͡ۦོ͢✰͢⇣͢✘͢͢\u2066ۦ↬ۦོ͢✰͢⇣͢✰͜✘͢͢\u2066  ✘͢͢☠↬ۦོ͢✰͢⇣͢      ✰͜✘                 ✘͢͢\u2066  ✘͢͢ۦ✰ོ͜͢✘͢͢ۦོ͢↬✰͜͢͡✘✰͜͢͡ۦོ͢✰͢⇣͢✘͢͢\u2066ۦ↬ۦོ͢✰͢⇣͢✰͜✘͢͢\u2066  ✘͢͢☠↬ۦོ͢✰͢⇣͢      ✰͜✘                 ✘͢͢\u2066  ✘͢͢ۦ✰ོ͜͢✘͢͢ۦོ͢↬✰͜͢͡✘✰͜͢͡ۦོ͢✰͢⇣͢✘͢͢\u2066ۦ↬ۦོ͢✰͢⇣͢✰͜✘͢͢\u2066 ☠↬ۦོ͢✰͢⇣͢      ✰͜✘                 ✘͢͢\u2066  ✘͢͢ۦ✰ོ͜͢✘͢͢ۦོ͢↬✰͜͢͡✘✰͜͢͡ۦོ͢✰͢⇣͢✘͢͢\u2066ۦ↬ۦོ͢✰͢⇣͢✰͜✘͢͢\u2066  ✘͢͢☠↬ۦོ͢✰͢⇣͢      ✰͜✘       ")
                LogUtils.d("write text: ${r}")
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
