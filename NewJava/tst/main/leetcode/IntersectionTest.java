package main.leetcode;

import main.leetcode.Intersection;
import org.junit.Test;

import java.util.Arrays;

public class IntersectionTest {

    @Test
    public void intersection() {
        Intersection intersection = new Intersection();
        int[] x = new int[]{1, 2, 2, 1};
        int[] y = new int[]{2, 2};
        int[] res = intersection.intersection(x, y);
        Arrays.stream(res).forEach(e -> System.out.println(e));

        int[] x2 = new int[]{4, 9, 5};
        int[] y2 = new int[]{9, 4, 9, 8, 4};
        int[] res2 = intersection.intersection(x2, y2);
        Arrays.stream(res2).forEach(e -> System.out.println(e));
    }
}