package com.qwuiteam.project.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.FragmentUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qwuiteam.project.PageContainer
import com.qwuiteam.project.R
import com.qwuiteam.project.utils.SimpleFragmentPagerAdapter
import kotlinx.android.synthetic.main.fragment_viewpager.*

/**
 * 内外部列表 在事件控制权切换时会有顿一下感觉
 */
class ViewpagerFragment : BaseFragment() {


    override fun getLayoutId(): Int = R.layout.fragment_viewpager

    val fragments = arrayListOf(Default3Fragment(),DefaultFragment(), Default2Fragment())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = rv
        recyclerView.layoutManager = LinearLayoutManager(context)
        val data = PageContainer.values().toMutableList()
        data.addAll(data)


        recyclerView.adapter = ViewPagerLearnAdapter().also {
                val dataList = data.take(10).map { TestB(2, it.title)  }.toMutableList()
                    .apply { add(TestB(1, "")) }
            it.setNewData(dataList)
        }
//            object :
//                BaseQuickAdapter<PageContainer, BaseViewHolder>(
//                    R.layout.item_viewpager, data.take(1).toMutableList()
//                ) {
//                override fun convert(holder: BaseViewHolder, item: PageContainer) {
//                    Log.d("liuyuzhe", "ViewpagerFragment: "+holder.layoutPosition);
//                    val viewPager = holder.getView<ViewPager>(R.id.viewPager)
//                    Log.d("liuyuzhe", "ViewpagerFragment.viewPager.fragments: "+fragments.size);
//                    viewPager.adapter = SimpleFragmentPagerAdapter(fragments, childFragmentManager)
//                }
//            }


//            object :
//                BaseQuickAdapter<PageContainer, BaseViewHolder>(
//                    R.layout.item_main, data.take(1).toMutableList()
//                ) {
//                override fun convert(holder: BaseViewHolder, item: PageContainer) {
//                    holder.setText(R.id.itemText, item.title)
//                }
//            }
    }


    inner class ViewPagerLearnAdapter : BaseMultiItemQuickAdapter<TestB, BaseViewHolder>() {

        init {
            addItemType(1, R.layout.item_viewpager)
            addItemType(2, R.layout.item_main)
        }

        override fun convert(holder: BaseViewHolder, item: TestB) {
            if (holder.itemViewType == 1) {
                Log.d("liuyuzhe", "ViewpagerFragment: " + holder.layoutPosition);
                val viewPager = holder.getView<ViewPager>(R.id.viewPager)
                Log.d("liuyuzhe", "ViewpagerFragment.viewPager.fragments: " + fragments.size);
                viewPager.adapter = SimpleFragmentPagerAdapter(fragments, childFragmentManager)
            } else {
                holder.setText(R.id.itemText, item.title)
            }
        }

    }


    class TestB(override val itemType: Int, val title: String = "") : MultiItemEntity {

    }
}