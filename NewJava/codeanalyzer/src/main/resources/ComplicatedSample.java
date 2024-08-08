package org.example;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

class ComplicatedSample {

    static class InnerClass {
        private int inner;
    }

    private final static String TEST_STRING = "TestString";

    private List<Object> objectList;

    private Deque<String> stringDeque;

    ComplicatedSample() {
        this.objectList = new List<Object>();
        this.objectDeque = new ArrayDeque<>();
    }

    private void forLoopFunction() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }

}