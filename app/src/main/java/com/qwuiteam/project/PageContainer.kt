package com.qwuiteam.project

import com.qwuiteam.project.fragment.AutoScrollTextViewFragment
import com.qwuiteam.project.fragment.BaseFragment
import com.qwuiteam.project.fragment.BitmapFragment

enum class PageContainer(val title: String, val targetClass: Class<out BaseFragment>) {

    bitmap("研究bitmap", BitmapFragment::class.java),
    auto_text("研究自动滚动text", AutoScrollTextViewFragment::class.java)

}