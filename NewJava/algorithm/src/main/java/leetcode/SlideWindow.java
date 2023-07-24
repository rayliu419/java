package leetcode;

import java.util.HashMap;
import java.util.Map;

public class SlideWindow {

    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> charCount = new HashMap<>();
        int start = 0;
        int end = 0;
        int maxLength = 0;
        int counter = 0;
        while (end < s.length()) {
            if (charCount.getOrDefault(s.charAt(end), 0) == 0) {
                counter += 1;
            }
            charCount.put(s.charAt(end), charCount.getOrDefault(s.charAt(end), 0) + 1);
            end++;
            while (counter > 0) {
                if (charCount.getOrDefault(s.charAt(start), 0) == 1) {
                    counter -= 1;
                }
                charCount.put(s.charAt(start), charCount.get(s.charAt(start)) - 1);
                start++;
            }
            maxLength = Math.max(maxLength, end - start);
        }
        return maxLength;
    }

}
