package main.leetcode;

import java.util.List;

// https://leetcode.com/problems/count-items-matching-a-rule/

public class CountMatch {

    public int countMatches(List<List<String>> items, String ruleKey, String ruleValue) {
        int count = 0;
        for (List<String> item : items) {
            String type = item.get(0);
            String color = item.get(1);
            String name = item.get(2);
            if (ruleKey.equals("type") && ruleValue.equals(type)) {
                count += 1;
                continue;
            }

            if (ruleKey.equals("color") && ruleValue.equals(color)) {
                count += 1;
                continue;
            }

            if (ruleKey.equals("name") && ruleValue.equals(name)) {
                count += 1;
                continue;
            }
        }
        return count;
    }
}
