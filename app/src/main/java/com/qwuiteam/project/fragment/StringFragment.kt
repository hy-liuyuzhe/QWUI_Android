package com.qwuiteam.project.fragment

import android.app.PendingIntent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.TimeUtils
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
        //        String prefix = longestCommonPrefix(strs);
//        String prefix = longestCommonPrefix2(strs, 0, strs.length - 1);
//        String prefix = longestCommonPrefix2(strs);
//        String prefix = longestCommonPrefix3(strs);

//        boolean r = palindrome(1221);
//        twoNumbersMain();
//        System.out.println("algorithm:" + addStrings("51189", "967895"));

//        System.out.println("algorithm:" + multiply("123", "456"));
        val time = System.currentTimeMillis()
        println("正确的转换time: " + TimeUtils.millis2String(time))
        println("以前方法转换time: " + getYearMonthDayHourMinuteSecond(time))
    }

    fun getYearMonthDayHourMinuteSecond(timeMillis: Long): String {
        val timezone = 8 // 时区
        var totalSeconds = timeMillis / 1000
        totalSeconds += (60 * 60 * timezone).toLong()
        val second = (totalSeconds % 60).toInt() // 秒
        val totalMinutes = totalSeconds / 60
        val minute = (totalMinutes % 60).toInt() // 分
        val totalHours = totalMinutes / 60
        val hour = (totalHours % 24).toInt() // 时
        val totalDays = (totalHours / 24).toInt()
        val _year = 1970
        var year = _year + totalDays / 366
        var month = 1
        var day = 1
        var diffDays: Int
        var leapYear: Boolean
        while (true) {
            var diff = (year - _year) * 365
            diff += (year - 1) / 4 - (_year - 1) / 4
            diff -= (year - 1) / 100 - (_year - 1) / 100
            diff += (year - 1) / 400 - (_year - 1) / 400
            diffDays = totalDays - diff
            leapYear = year % 4 == 0 && year % 100 != 0 || year % 400 == 0
            if (!leapYear && diffDays < 365 || leapYear && diffDays < 366) {
                break
            } else {
                year++
            }
        }
        val monthDays: IntArray
        monthDays = if (diffDays >= 59 && leapYear) {
            intArrayOf(-1, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335)
        } else {
            intArrayOf(-1, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
        }
        for (i in monthDays.size - 1 downTo 1) {
            if (diffDays >= monthDays[i]) {
                month = i
                day = diffDays - monthDays[i] + 1
                break
            }
        }
        val hours: String
        val minutes: String
        val seconds: String
        hours = if (hour < 10) {
            "0$hour"
        } else {
            "" + hour
        }
        minutes = if (minute < 10) {
            "0$minute"
        } else {
            "" + minute
        }
        seconds = if (second < 10) {
            "0$second"
        } else {
            "" + second
        }

//        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        return "$hours:$minutes:$seconds"
    }

    class Test {
        val code: Int = 0
        val code1: Int = 0
        val message: Test? = null
    }
}