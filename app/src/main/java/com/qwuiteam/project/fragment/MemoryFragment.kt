package com.qwuiteam.project.fragment

import android.app.Instrumentation
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import com.blankj.utilcode.util.*
import com.qwuiteam.project.R
import com.qwuiteam.project.bean.Person
import com.qwuiteam.project.utils.MainLooperPrinter
import kotlinx.android.synthetic.main.fragment_layout.*
import kotlinx.android.synthetic.main.fragment_layout.add
import kotlinx.android.synthetic.main.fragment_layout.joinChannel
import kotlinx.android.synthetic.main.fragment_layout.log
import kotlinx.android.synthetic.main.fragment_layout.print
import kotlinx.android.synthetic.main.fragment_memory.*
import java.util.*

/**
 * 动态设置layoutParams的类型要和add的父类一致，否则你设置的属性无效（宽高除外）
 */
class MemoryFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_memory

        val list = mutableListOf<Person>()
    lateinit var person: Person
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Looper.getMainLooper().setMessageLogging(MainLooperPrinter());

        add.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                person = Person("")
                Log.d("liuyuzhe", "111111111111111: "+person);
            }
        })

        addMoreObject.setOnClickListener {
            list.add(Person(Random(10).toString()))
        }

        joinChannel.setOnClickListener {
            RoomRedPacketDialogFragment.newInstance("")
                .show(activity!!.supportFragmentManager, "124")
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