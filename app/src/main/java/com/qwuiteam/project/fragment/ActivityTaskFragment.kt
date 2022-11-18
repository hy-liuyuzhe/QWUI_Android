package com.qwuiteam.project.fragment

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import com.blankj.utilcode.constant.TimeConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.Utils
import com.qwuiteam.project.PageContainer
import com.qwuiteam.project.R
import com.qwuiteam.project.SimpleSecondActivity
import com.qwuiteam.project.utils.HkCountDownTimer
import kotlinx.android.synthetic.main.fragment_date.*
import kotlinx.android.synthetic.main.fragment_date.week
import kotlinx.android.synthetic.main.fragment_string.atText
import kotlinx.android.synthetic.main.fragment_string.bigDecimal
import kotlinx.android.synthetic.main.fragment_string.countdown
import kotlinx.android.synthetic.main.fragment_string.formatText
import kotlinx.android.synthetic.main.fragment_task.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * task
 */
class ActivityTaskFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_task


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startTask.setOnClickListener {
            LogUtils.d("new task")
            val intent = Intent(Utils.getApp(), SimpleSecondActivity::class.java)
                .putExtra("page", PageContainer.task)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        start_normal.setOnClickListener {
            LogUtils.d("normal")
            val intent = Intent(requireActivity(), SimpleSecondActivity::class.java)
                .putExtra("page", PageContainer.task)
            startActivity(intent)
        }

        countdown.setOnClickListener {
            object : HkCountDownTimer(TimeUnit.HOURS.toMillis(1)
                    + TimeUnit.MINUTES.toMillis(20)
                    + TimeUnit.SECONDS.toMillis(30)
                    + TimeUnit.MILLISECONDS.toMillis(40), 1000L) {
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
        week.setOnClickListener {
            val now = 1666851900000L//System.currentTimeMillis()
            val inRange = inDaysTimeRange(now, 7)
//            val week2 = isCurrentWeekV2(now)
            LogUtils.d("target now: ${TimeUtils.millis2String(now)}")
            LogUtils.d("应该不更新在一周内: ${inRange}")
//            val week = getWeeOfToday()
//            val weekPlus7 = week + TimeUnit.DAYS.toMillis(7)
//            LogUtils.d("weekPlus7: ${TimeUtils.millis2String(weekPlus7)}")
//            LogUtils.d("week: ${TimeUtils.millis2String(week)}")
//            LogUtils.d("isCurrentWeek: ${isCurrentWeek(now)}")
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

    fun isDay(millis: Long): Boolean {
        val wee = getWeeOfToday()
        return millis >= wee && millis < wee + TimeConstants.DAY
    }

    fun isCurrentWeek(millis: Long): Boolean {
        val wee = getWeeOfToday()
        return millis >= wee && millis < wee + TimeUnit.DAYS.toMillis(7)
    }

    fun isCurrentWeekV2(millis: Long): Boolean {
        return millis + TimeUnit.DAYS.toMillis(7) > System.currentTimeMillis()
    }

    fun inDaysTimeRange(targetMillis: Long, duration: Long): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        LogUtils.d("inDaysTimeRange.now: ${TimeUtils.millis2String(currentTimeMillis)}")
        return targetMillis + TimeUnit.DAYS.toMillis(duration) >= currentTimeMillis
    }

    private fun getWeeOfToday(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.WEEK_OF_YEAR, 0)
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.MILLISECOND] = 0
        return cal.timeInMillis
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