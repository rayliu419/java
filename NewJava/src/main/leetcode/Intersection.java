package main.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

// https://leetcode.com/problems/intersection-of-two-arrays

public class Intersection {

    public int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> res = new HashSet<>();
        if (nums1.length == 0 || nums2.length == 0) {
            return new int[0];
        }
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i = 0;
        int j = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                res.add(nums1[i]);
                i++;
                j++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                i++;
            }
        }
        return transform(res);
    }

    public int[] transform(HashSet<Integer> h) {
        int[] result = new int[h.size()];
        int index = 0;
        for (Integer i : h) {
            result[index++] = i.intValue();
        }
        return result;
    }
}
