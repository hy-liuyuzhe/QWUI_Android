package com.qwuiteam.project.fragment

import android.content.ContentProvider
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.qwuiteam.project.R
import com.qwuiteam.project.view.MessageListView
import kotlinx.android.synthetic.main.fragment_joined_room.*
import kotlinx.android.synthetic.main.fragment_layout.*
import kotlinx.android.synthetic.main.fragment_string.*

/**
 * id
 */
class JoinedRoomFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_joined_room


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewPager.adapter =
    }



    class JoinedAdapter : PagerAdapter(){

        private var views = arrayOfNulls<MessageListView>(3)


        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int  = 3

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            if (views[position] != null) {
                return views[position]!!
            }
            val messageView = MessageListView(container.context)
            container.addView(messageView)
            views[position] = messageView
            return messageView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }


    }
}