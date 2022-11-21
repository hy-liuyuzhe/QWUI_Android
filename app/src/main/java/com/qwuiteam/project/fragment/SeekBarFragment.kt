package com.qwuiteam.project.fragment

import android.content.ContentProvider
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.SeekBar
import com.blankj.utilcode.util.*
import com.qwuiteam.project.R
import kotlinx.android.synthetic.main.fragment_layout.*
import kotlinx.android.synthetic.main.fragment_seek_bar.*
import kotlinx.android.synthetic.main.fragment_string.*


class SeekBarFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_seek_bar


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                update_progress_bar.setProgress(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        positive.setOnClickListener {
            LogUtils.d("scroll x to 100")
            layoutView.scrollBy(100,0)
        }
        negative.setOnClickListener {
            LogUtils.d("scroll x to -100")
            layoutView.scrollBy(-100,0)
        }


    }
}