package com.qwuiteam.project.fragment

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.qwuiteam.project.R
import com.qwuiteam.project.bean.Person
import com.qwuiteam.project.enum.RoomMessageShowEnum
import com.qwuiteam.project.utils.MainLooperPrinter
import kotlinx.android.synthetic.main.fragment_layout.encode_video
import kotlinx.android.synthetic.main.fragment_layout.joinChannel
import kotlinx.android.synthetic.main.fragment_layout.log
import kotlinx.android.synthetic.main.fragment_layout.print
import kotlinx.android.synthetic.main.fragment_memory.*
import java.util.*
import java.util.concurrent.CountDownLatch

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

        encode_video.setOnClickListener {
            requireActivity().intent.putExtra("show", RoomMessageShowEnum.MESSAGE_ALL)
            val type = requireActivity().intent.getSerializableExtra("show")
            LogUtils.d("type: $type");

            requireActivity().intent.putExtra("show2", RoomMessageShowEnum.MESSAGE_CHAT)
            val type2 = requireActivity().intent.getSerializableExtra("show2")
            LogUtils.d("type2: $type");
        }

        addMoreObject.setOnClickListener {
            list.add(Person(Random(10).toString()))
        }

        joinChannel.setOnClickListener {
            RoomRedPacketDialogFragment.newInstance("")
                .show(requireActivity().supportFragmentManager, "124")
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
        thread.setOnClickListener {
            val t = arrayOfNulls<Thread>(100)
            val countdownLatchTest = CountDownLatch(t.size)

            t.forEachIndexed { index, thread ->
                t[index] = Thread {
                    var r = 0
                    for (j in 0..1000) {
                        r += j
                    }
                    println("index: $index , r: $r")
                }
                countdownLatchTest.countDown()
            }

            t.forEachIndexed { index, thread ->
                t[index]?.start()
            }

            countdownLatchTest.await()
            println("end countdownlatch")
        }
    }
}


