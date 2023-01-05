package com.qwuiteam.project.view

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class MessageListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr)

{
    val _messageListView by lazy { RecyclerView(context) }
    val _scrollSpeedLayoutManager = ScrollSpeedLinearLayoutManger(context)
    val _messageAdapter = MessageAdapter()

    init {
        with(_messageListView) {
            val rvParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            layoutParams = rvParams
            overScrollMode = OVER_SCROLL_NEVER
            isHorizontalScrollBarEnabled = false

            layoutManager = _scrollSpeedLayoutManager
            addItemDecoration(
                DividerItemDecoration(context, _scrollSpeedLayoutManager.orientation)
            )
            adapter = _messageAdapter
        }
        addView(_messageListView)
    }


}

class MessageAdapter : BaseMultiItemQuickAdapter<TestBean,BaseViewHolder>(){


    override fun convert(holder: BaseViewHolder, item: TestBean) {

    }

}

class ScrollSpeedLinearLayoutManger(context: Context) : LinearLayoutManager(context) {

    private var lastPosition = -1
    private var _linearSmoothScroller: LinearSmoothScroller =
        object : LinearSmoothScroller(context) {

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float =
                MILLISECONDS_PER_INCH

        }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int
    ) {
        if (_linearSmoothScroller.isRunning && lastPosition != -1) {
            scrollToPosition(lastPosition)
        }
        _linearSmoothScroller.targetPosition = position
        startSmoothScroll(_linearSmoothScroller)
        this.lastPosition = position
    }

    companion object {
        private const val MILLISECONDS_PER_INCH = 0.2f
    }

}

class TestBean(val title: String) : MultiItemEntity {

    override val itemType: Int = 1
}