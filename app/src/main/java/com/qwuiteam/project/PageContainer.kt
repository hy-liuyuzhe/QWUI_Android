package com.qwuiteam.project

import com.qwuiteam.project.fragment.*

enum class PageContainer(
    val title: String,
    val targetClass: Class<out BaseFragment>
) {

    idImage("研究。9图使用", IdImageFragment::class.java),
    pk("pk 需求", PkFragment::class.java),
    avatar("头像挂链适配", AvatarFragment::class.java),
//    agoraRTC("研究声网", RTCFragment::class.java),
    layout("研究布局layout", LayoutFragment::class.java),
    bitmap("研究bitmap", BitmapFragment::class.java),
    memory("内存研究", MemoryFragment::class.java),
    auto_text("研究自动滚动text", AutoScrollTextViewFragment::class.java),
    choosePhoto("选择相册", ChoosePhotoFragment::class.java),
    string("字符串", StringFragment::class.java),
    FFmpag("ffmpag", FFmpagFragment::class.java)

}