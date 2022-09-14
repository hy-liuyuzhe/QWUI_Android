package com.qwuiteam.project.fragment

import android.os.Bundle
import android.text.Html
import android.view.View
import com.blankj.utilcode.util.*
import com.qwuiteam.project.R
import com.qwuiteam.project.utils.HkCountDownTimer
import kotlinx.android.synthetic.main.fragment_string.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * id
 */
class DateFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_date


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        format1.setOnClickListener {
            val time = formatTime(
                TimeUnit.HOURS.toMillis(1)
                        + TimeUnit.MINUTES.toMillis(1)
                        + TimeUnit.SECONDS.toMillis(1)
                        + TimeUnit.MILLISECONDS.toMillis(40)
            )
            LogUtils.d("millis2FitTimeSpan: " + time)
        }
        start_with.setOnClickListener {
            val time = millis2FitTimeSpan(
                TimeUnit.HOURS.toMillis(1)
                        + TimeUnit.MINUTES.toMillis(20)
                        + TimeUnit.SECONDS.toMillis(30)
                        + TimeUnit.MILLISECONDS.toMillis(40)
            )
            LogUtils.d("millis2FitTimeSpan: " + time)
        }

        countdown.setOnClickListener {
            object : HkCountDownTimer(TimeUnit.HOURS.toMillis(1)
                    + TimeUnit.MINUTES.toMillis(20)
                    + TimeUnit.SECONDS.toMillis(30)
                    + TimeUnit.MILLISECONDS.toMillis(40),1000L){
                override fun onTick(millisUntilFinished: Long) {
                    val t = formatTime(millisUntilFinished)
                    LogUtils.d("millis2FitTimeSpan: " + t)
                }

                override fun onFinish() {

                }

            }.start()
         }
        formatText.setOnClickListener {
            val time = 1663045680000L
            val t = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(
                Date(time)
            )
            val t2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ar", "")).format(
                Date(time)
            )
            LogUtils.d("time china: $t");
            LogUtils.d("time ar: $t2");
        }
        //    <string name="turntable_win_tip">Congratulations %1s for winning  %2s gold coins in wheel of fortune</string>
        formatText2.setOnClickListener {
        }
        bigDecimal.setOnClickListener {
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

    fun millis2FitTimeSpan(millis: Long): String? {
        var millis = millis
        var precision = 5
        if (precision <= 0) return null
        precision = Math.min(precision, 5)
        val units = arrayOf("天", "小时", "分钟", "秒", "毫秒")
//        if (millis == 0L) return 0 + units[precision - 1]
        val sb = StringBuilder()
        if (millis < 0) {
            sb.append("-")
            millis = -millis
        }
        //1毫秒 ，1000 1秒 ，60000 1小时，3600000 1天
        val unitLen = intArrayOf(86400000, 3600000, 60000, 1000, 1)
        for (i in 0 until precision) {
            if (millis >= unitLen[i]) {
                val mode = millis / unitLen[i]
                millis -= mode * unitLen[i]
                sb.append(mode).append(units[i])
            }
        }
        return sb.toString()
    }

    fun formatTime(ms: Long): String {
        val daysMillis = TimeUnit.DAYS.toMillis(1)
        val day = (ms / daysMillis).toInt()
        val hoursMillis = TimeUnit.HOURS.toMillis(1)
        val hour: Int = (ms % (daysMillis) / hoursMillis).toInt()

        val minutesMillis = TimeUnit.MINUTES.toMillis(1)
        val minute = (ms % (hoursMillis) / minutesMillis).toInt()
        val secondsMillis = TimeUnit.SECONDS.toMillis(1)
        val second = (ms % minutesMillis / secondsMillis).toInt()
        val millisecond = (ms % secondsMillis).toInt()

        return "$day D $hour:$minute:$second - $millisecond"
    }

}