package com.data;

import lombok.Data;

@Data
public class MyCalculator {
    public int x;

    public int add(int a, int b) {
        return a + b;
    }
}
