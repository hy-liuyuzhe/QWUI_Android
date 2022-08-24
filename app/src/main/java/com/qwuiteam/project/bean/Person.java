package com.qwuiteam.project.bean;

import android.os.Handler;

public class Person {
    private final byte[] bytes;
    private String s;

    public Person(String s) {
        this.s = s;
        bytes = new byte[1024 * 1024 * 40];
    }


    static Handler h = new Handler();
}
