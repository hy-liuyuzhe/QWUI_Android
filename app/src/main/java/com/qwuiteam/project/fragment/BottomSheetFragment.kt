package com.qwuiteam.project.fragment

import android.content.ContentProvider
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import com.blankj.utilcode.util.*
import com.qwuiteam.project.R
import com.qwuiteam.project.view.RoomPkBottomSheetDialog
import kotlinx.android.synthetic.main.fragment_layout.*
import kotlinx.android.synthetic.main.fragment_string.*

/**
 * id
 */
class BottomSheetFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_string


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        start_with.text = "show dialog"
        start_with.setOnClickListener {
            RoomPkBottomSheetDialog().show(requireActivity().supportFragmentManager, "pk")
        }

        start_with2.setOnClickListener {
            val all = "http://www.odaily.news/pp/api/hot-word"
            val target = "http://www.odaily.news/"

            val result = all.startsWith(target)
            Log.d("liuyuzhe", "result: " + result);
        }
        formatText.setOnClickListener {
            val originContent = "@yzqing 阿"
            val nickname =
                originContent.substring(originContent.indexOf("@"), originContent.indexOf(" "))
            val content = originContent.replace(nickname, "")
            atText.text = Html.fromHtml(
                String.format(
                    StringUtils.getString(R.string.at_content),
                    nickname,
                    content
                )
            )

        }
        val content = "hahaha"
        atText.setText(
            Html.fromHtml(
                String.format(
                    StringUtils.getString(R.string.at_content),
                    "liuyuzhe",
                    content
                )
            )
        )
    }
}