package main.objectmapper;

import lombok.Data;
import main.lombokapp.MyCalculator;

import java.util.HashMap;
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
