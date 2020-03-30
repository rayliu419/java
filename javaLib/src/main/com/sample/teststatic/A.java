package com.sample.teststatic;

public class A {

    static {
        System.out.println("static block in A");
    }

    private final static String test = getString();

    public static String createTest() {
        System.out.println("A 2");
        return test;
    }

    public static String getString() {
        System.out.println("A 1");
        return "test";
    }
}
