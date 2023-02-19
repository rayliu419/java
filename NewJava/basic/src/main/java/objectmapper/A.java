package objectmapper;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class A {
    public int a;
    public List<String> aa;

    public A() {
        aa = new ArrayList<>();
        aa.add("A");
    }
}
