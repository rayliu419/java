package leetcode;

import leetcode.CountString;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountStringTest {

    @Test
    public void countString() {
        String[] s = new String[]{"the", "a", "the", "the", "a"};
        CountString countString = new CountString();
        HashMap<String, List<Integer>> result = countString.countString(s);
        for (Map.Entry<String, List<Integer>> entry : result.entrySet()) {
            System.out.println(String.format(String.format("key - {}"), entry.getKey()));
            entry.getValue().stream().forEach(e -> System.out.print(e + " "));
        }
    }
}