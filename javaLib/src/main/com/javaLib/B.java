package com.javaLib;

import com.javaLib.A;

public class B {
    static {
        System.out.println("static block in B");
    }

    public B(){
        System.out.println("B 1");
        String result = A.createTest();
    }
}
