package com.qwuiteam.project

import com.qwuiteam.project.fragment.*

enum class PageContainer(
    val title: String,
    val targetClass: Class<out BaseFragment>
) {

    idImage("研究。9图使用", IdImageFragment::class.java),
//    agoraRTC("研究声网", RTCFragment::class.java),
    layout("研究布局layout", LayoutFragment::class.java),
    bitmap("研究bitmap", BitmapFragment::class.java),
    memory("内存研究", MemoryFragment::class.java),
    auto_text("研究自动滚动text", AutoScrollTextViewFragment::class.java),
    FFmpag("ffmpag", FFmpagFragment::class.java)

}