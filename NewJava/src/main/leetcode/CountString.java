package main.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountString {

    public HashMap<String, List<Integer>> countString(String[] s) {
        HashMap<String, List<Integer>> m = new HashMap();
        int index = 0;
        for (String ss : s) {
            List<Integer> l = m.getOrDefault(ss, new ArrayList<Integer>());
            l.add(index);
            m.putIfAbsent(ss, l);
            index++;
        }
        return m;
    }
}
