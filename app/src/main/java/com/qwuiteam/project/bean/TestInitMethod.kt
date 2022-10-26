package com.qwuiteam.project.bean

import android.util.Log

class TestInitMethod {

    companion object{

        init {
            val c = 1
            val d = 2
        }
    }

    init {
        println("lala")
        Log.d("liuyuzhe", "1111: ");

        val s = 1
        val b = 2
    }

    fun show(){
        println(111)
    }
}