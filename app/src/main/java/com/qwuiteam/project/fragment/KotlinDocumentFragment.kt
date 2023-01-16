package com.qwuiteam.project.fragment

import android.app.PendingIntent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import androidx.activity.result.contract.ActivityResultContracts
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.TimeUtils
import com.qwuiteam.project.CommonUtil
import com.qwuiteam.project.R
import com.qwuiteam.project.parseURLValue
import kotlinx.android.synthetic.main.fragment_kotlin.*
import java.math.BigDecimal

/**
 * kotlin
 */
class KotlinDocumentFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_kotlin

    var index = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        simple.setOnClickListener {
//            asReversed()
//            reversed()
            val number1 = mutableListOf("one", "two", "three", "four")
            val number2 = mutableListOf("one", "two", "three", "four","five")

            println(number1.union(number2))

            println(number1 union number2)


        }
    }

    private fun reversed() {
        val numbers = mutableListOf("one", "two", "three", "four")
        val reversedNumbers = numbers.reversed()
        Log.d("liuyuzhe", "simple result: $reversedNumbers");
        numbers.add("five")
        Log.d("liuyuzhe", "simple result: $reversedNumbers");
        Log.d("liuyuzhe", "simple result.numbers: $numbers");
    }

    private fun asReversed() {
        val numbers = listOf("one", "two", "three", "four")
        val r = numbers.asReversed()
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