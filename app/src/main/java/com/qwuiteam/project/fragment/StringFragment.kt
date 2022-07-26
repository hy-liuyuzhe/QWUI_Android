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
import kotlinx.android.synthetic.main.fragment_layout.*
import kotlinx.android.synthetic.main.fragment_string.*
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * id
 */
class StringFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_string


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        start_with.setOnClickListener {
            val all = "https://passport.odaily.news/api/mobi-investor/appversion?app=0&os=android"
            val target = "https://passport.odaily.news/"

            val result = all.startsWith(target)
            Log.d("liuyuzhe", "result: " + result);
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
        //    <string name="turntable_win_tip">Congratulations %1s for winning  %2s gold coins in wheel of fortune</string>
        formatText2.setOnClickListener {

            atText.text = StringUtils.getString(R.string.turntable_win_tip, "liuyuzhe", "1232")
        }
        bigDecimal.setOnClickListener {
//            val r = BigDecimal(10000.0).add(BigDecimal(0.0))
//            Log.d("liuyuzhe", "r: "+r.toDouble());

//            val r = BigDecimal(51.3).divide(BigDecimal(100.0), 2, BigDecimal.ROUND_HALF_UP)
//            Log.d("liuyuzhe", "r: "+r.toDouble());
            val r = BigDecimal(6749.03123141422432)
                .setScale(0, BigDecimal.ROUND_DOWN).toString()

            Log.d("liuyuzhe", "r: "+r);

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