package com.sample.testObjectMapper;

import com.data.MyCalculator;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class A1 extends A {
    public int a1;
    public Map<String, MyCalculator> aa1;

    public A1() {
        aa1 = new HashMap<>();
        aa1.put("Test", new MyCalculator());
        aa1.put("Test1", new MyCalculator());
    }
}
