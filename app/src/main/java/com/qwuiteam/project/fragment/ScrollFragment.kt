package com.qwuiteam.project.fragment

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.blankj.utilcode.util.*
import com.qwuiteam.project.R
import kotlinx.android.synthetic.main.fragment_layout.*
import kotlinx.android.synthetic.main.fragment_seek_bar.*
import kotlinx.android.synthetic.main.fragment_string.*


class ScrollFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_scroll


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                update_progress_bar.setProgress(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        var scrollY = 300
        textScrollByY.setOnClickListener {
            val y = testLayout.scrollY
            LogUtils.d("scrollY: $y")
            testLayout.scrollTo(0, y + scrollY)
        }
        positive.setOnClickListener {
            LogUtils.d("scroll x to 100")
            layoutView.scrollBy(100, 0)
            testLayout.scrollBy(0, scrollY)
        }
        negative.setOnClickListener {
            LogUtils.d("scroll x to -100")
            layoutView.scrollBy(-100, 0)
        }


    }
}