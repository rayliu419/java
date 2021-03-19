package main.staticorder;

import main.objectmapper.A;

public class StaticB {
    static {
        System.out.println("static block in B");
    }

    public StaticB() {
        System.out.println("StaticB 1");
        String result = StaticA.createTest();
    }
}
