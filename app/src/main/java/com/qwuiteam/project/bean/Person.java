package com.qwuiteam.project.bean;

public class Person {
    final byte[] bytes;
    private String s;

    public Person(String s) {
        this.s = s;
        bytes = new byte[1024 * 1024 * 40];
    }
}
