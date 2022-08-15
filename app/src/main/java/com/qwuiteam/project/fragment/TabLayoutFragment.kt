package com.qwuiteam.project.fragment

import android.content.ContentProvider
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.qwuiteam.project.R
import kotlinx.android.synthetic.main.fragment_layout.*
import kotlinx.android.synthetic.main.fragment_string.*
import kotlinx.android.synthetic.main.fragment_tablayout.*

/**
 * id
 */
class TabLayoutFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_tablayout

    private val tabTitles = arrayListOf<String>()
    private val fragment = arrayListOf<Fragment>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        update.setOnClickListener {
            tabLayout.getTabAt(2)
                ?.text = "我是长文本长文本长文本长文本1234567890"
        }
        for (i in 0..2){
            val tab = tabLayout.newTab()
            tab.view
            tab.text = "我是tab: $i"
            tabTitles.add("我是tab: $i")
            fragment.add(IdImageFragment())
            tabLayout.addTab(tab)
        }
        viewPager.adapter = SimpleFragmentPagerAdapter(requireActivity().supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }


    private inner class SimpleFragmentPagerAdapter constructor(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            return fragment[position]
        }

        override fun getCount(): Int {
            return fragment.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tabTitles[position]
        }
    }
}