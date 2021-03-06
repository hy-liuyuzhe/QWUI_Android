package com.qwuiteam.project.fragment

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.qwuiteam.project.R
import com.qwuiteam.project.utils.MainLooperPrinter
import kotlinx.android.synthetic.main.fragment_layout.*

/**
 * 动态设置layoutParams的类型要和add的父类一致，否则你设置的属性无效（宽高除外）
 */
class LayoutFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_layout


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val child = Button(Utils.getApp())
                child.setBackgroundColor(ColorUtils.getColor(R.color.teal_700))
                val dp100 = SizeUtils.dp2px(100f)
                val layoutParams = FrameLayout.LayoutParams(dp100, dp100)
                layoutParams.gravity = Gravity.END
//                val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
//                    RelativeLayout.LayoutParams.MATCH_PARENT)
//                layoutParams.alignWithParent = true
                view.findViewById<ViewGroup>(R.id.root).addView(child, layoutParams)
                Log.e(TAG, "onViewCreated.add: " + child.parent)
            }
        })
        val list = listOf(
            mapOf(Pair("a", listOf("1", "2"))),
            mapOf(Pair("b", listOf("3", "4")))
        )
        convert_map.setOnClickListener {
            ToastUtils.showShort("1234")
            val mutableList: MutableList<String> = list.map { it.values }
                .flatMap { it.map { it.toString() } }.toMutableList()
            Log.d("liuyuzhe", "map: "+mutableList);

            mutableList.add("5")
            Log.d("liuyuzhe", "add: "+mutableList);
        }

        joinChannel.setOnClickListener {
            RoomRedPacketDialogFragment.newInstance("")
                .show(activity!!.supportFragmentManager,"124")
        }

        print.setOnClickListener {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
                Log.e("liuyuzhe", "onClick of R.id.button1: ", e)
            }
        }
        log.setOnClickListener {
            Looper.getMainLooper().setMessageLogging(MainLooperPrinter());
        }
    }
}