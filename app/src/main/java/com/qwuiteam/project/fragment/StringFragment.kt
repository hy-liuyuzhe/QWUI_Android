package com.qwuiteam.project.fragment

import android.app.PendingIntent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import com.blankj.utilcode.util.*
import com.qwuiteam.project.CommonUtil
import com.qwuiteam.project.R
import com.qwuiteam.project.parseURLValue
import kotlinx.android.synthetic.main.fragment_string.*
import java.math.BigDecimal

/**
 * id
 */
class StringFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_string

    var index = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = arrayListOf("162.62.52.194", "162.62.121.186", "162.62.120.170")
        number.setOnClickListener {
            for(n in 0..100){
                index++
                val i = index % list.size
                val data = list[i]
                LogUtils.d("data: $i")
            }

        }

        splitPath.setOnClickListener {
            val url = "https://www.hakiapp.com/build/index.html#/rankReward?a=1&b=2&hideNav=1"
            val isHttp = URLUtil.isHttpsUrl(url)
            val rearParameter = url.substringAfterLast("?")
            val list = rearParameter.split("&")
            list.forEach {
                val r = it.split("=")
                val r2 = it.split("+")

                if (r.size == 2) {
                    if (r[0] == "hideNav") {
                        LogUtils.d("hideNav: ${r[1]}")
                    }
                }

                LogUtils.d("r2:$r2")
            }
//            LogUtils.d(list)
            LogUtils.d("isHttpUrl: $isHttp")


            LogUtils.d("parseURLValue: ${url.parseURLValue("export")}")

        }
        binaryString.setOnClickListener {
            val flagUpdateCurrent = Integer.toBinaryString(PendingIntent.FLAG_UPDATE_CURRENT)
            LogUtils.d("flagUpdateCurrent: $flagUpdateCurrent")

            val flagImmutable = Integer.toBinaryString(PendingIntent.FLAG_IMMUTABLE)
            LogUtils.d("flagImmutable: $flagImmutable")
            val r = CommonUtil.checkFlag(PendingIntent.FLAG_UPDATE_CURRENT)
            LogUtils.d("r: $r")
        }
        start_with.setOnClickListener {
            val json = "{\n" +
                    "    \"code\": 10003,\n" +
                    "    \"message\": \"Please update app version\"\n" +
                    "}"
            val bean = GsonUtils.fromJson<Test>(json, Test::class.java)
            LogUtils.d("bean: $bean");
        }
        start_with.setOnClickListener {
            val all = "https://passport.odaily.news/api/mobi-investor/appversion?app=0&os=android"
            val target = "https://passport.odaily.news/"

            val result = all.startsWith(target)
            Log.d("liuyuzhe", "result: " + result);
        }

        countdown.setOnClickListener {
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
//        formatText2.setOnClickListener {
//            val phone = "1-2950069"
////            val r = phone.substring(0, phone.indexOf("-"))
//            val r = phone.substring(phone.indexOf("-")+1, phone.length)
//            Log.d("liuyuzhe", "r: $r");
//            //atText.text = StringUtils.getString(R.string.turntable_win_tip, "liuyuzhe", "1232")
//        }
        bigDecimal.setOnClickListener {
//            val r = BigDecimal(10000.0).add(BigDecimal(0.0))
//            Log.d("liuyuzhe", "r: "+r.toDouble());

//            val r = BigDecimal(51.3).divide(BigDecimal(100.0), 2, BigDecimal.ROUND_HALF_UP)
//            Log.d("liuyuzhe", "r: "+r.toDouble());
            val r = BigDecimal(6749.03123141422432)
                .setScale(0, BigDecimal.ROUND_DOWN).toString()

            Log.d("liuyuzhe", "r: " + r);

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
        replaceSpace.setOnClickListener {
            val x = "weekly agency top 1"
            val y = "weekly agency top  1"
            LogUtils.d("space.x: ${x.replace(" ", "")}")
            LogUtils.d("space.y: ${y.replace(" ", "").lowercase()}")
        }
    }

    class Test {
        val code: Int = 0
        val code1: Int = 0
        val message: Test? = null
    }
}