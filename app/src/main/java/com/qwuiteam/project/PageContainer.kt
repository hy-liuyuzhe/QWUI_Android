package com.qwuiteam.project

enum class PageContainer(val title: String, val targetClass: Class<out BaseFragment>) {

    BitmapPage("研究bitmap", BitmapFragment::class.java)

}