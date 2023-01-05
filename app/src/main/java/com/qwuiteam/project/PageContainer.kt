package com.qwuiteam.project

import com.qwuiteam.project.algorithm.FileStoreFragment
import com.qwuiteam.project.fragment.*

enum class PageContainer(
    val title: String,
    val targetClass: Class<out BaseFragment>
) {

    senderView("研究连击条", SenderViewFragment::class.java),
    tablayout("研究tablayout", TabLayoutFragment::class.java),
    flow("研究flow", FlowFragment::class.java),
    viewmodel("研究双向绑定", ViewmodelFragment::class.java),
    idImage("研究。9图使用", IdImageFragment::class.java),
    pk("pk 需求", PkFragment::class.java),
    scroll("自定义滚动", ScrollFragment::class.java),
    task("task 需求", ActivityTaskFragment::class.java),
    xlog("xlog", XlogFragment::class.java),
    diff("diff刷新", RecyclerDiffFragment::class.java),
    diffMap("diffMap刷新", RecyclerDiffMapFragment::class.java),
    avatar("头像挂链适配", AvatarFragment::class.java),
    joinedRoom("进房特效", AvatarFragment::class.java),
    algorithm("文件存储", FileStoreFragment::class.java),
    date("日期格式化", DateFragment::class.java),
//    agoraRTC("研究声网", RTCFragment::class.java),
    layout("研究布局layout", LayoutFragment::class.java),
    svga("研究svga", SvgaViewFragment::class.java),
    bitmap("研究bitmap", BitmapFragment::class.java),
    memory("内存研究", MemoryFragment::class.java),
    auto_text("研究自动滚动text", AutoScrollTextViewFragment::class.java),
    choosePhoto("选择相册", ChoosePhotoFragment::class.java),
    string("字符串", StringFragment::class.java),
    BottomSheet("弹窗", BottomSheetFragment::class.java),
    ExpandLayout("展开折叠", ExpandLayoutFragment::class.java),
    gradient("渐变色", GradientFragment::class.java),
    FFmpag("ffmpag", FFmpagFragment::class.java)

}