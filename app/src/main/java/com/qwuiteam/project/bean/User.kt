package com.qwuiteam.project.bean

class User(val t:String = "") : AbsUser() {
    init {
        title = t
    }

    val age : Int = 10

}

class User2 : AbsUser() {

    val address :String = "广州"

}