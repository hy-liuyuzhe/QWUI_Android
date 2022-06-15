package com.qwuiteam.project

import com.qwuiteam.project.fragment.*

enum class PageContainer(
    val title: String,
    val targetClass: Class<out BaseFragment>
) {

    agoraRTC("研究声网", RTCFragment::class.java),
    layout("研究布局layout", LayoutFragment::class.java),
    bitmap("研究bitmap", BitmapFragment::class.java),
    auto_text("研究自动滚动text", AutoScrollTextViewFragment::class.java)

}