package leetcode;

import java.util.*;

public class Intervals {

    /**
     * https://leetcode.com/problems/merge-intervals/
     * 根据start排序，不断合并
     *
     */
    public static int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        int resultIndex = 0;
        int[][] result = new int[intervals.length][intervals[0].length];
        int start = intervals[0][0];
        int end = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            int[] cur = intervals[i];
            if (cur[0] <= end) {
                end = Math.max(cur[1], end);
            } else {
                // 新起点
                result[resultIndex][0] = start;
                result[resultIndex][1] = end;
                resultIndex++;
                start = cur[0];
                end = cur[1];
            }
        }
        result[resultIndex][0] = start;
        result[resultIndex][1] = end;
        int[][] finalResult = new int[resultIndex + 1][2];
        for (int i = 0; i <= resultIndex; i++) {
            finalResult[i][0] = result[i][0];
            finalResult[i][1] = result[i][1];
        }
        return finalResult;
    }

    public static void testMerge() {
        int[][] intervals = new int[][]{
                {1, 3},
                {2, 6},
                {8, 10},
                {15, 18}
        };
        int[][] result = merge(intervals);
        System.out.println("pause");

        int[][] intervals2 = new int[][]{
                {1, 4},
                {0, 4}
        };
        int[][] result2 = merge(intervals2);
        System.out.println("pause");
    }

    /***
     *
     * https://leetcode.com/problems/non-overlapping-intervals/description/
     *
     * 擦除最少的区间，使的剩余区间不重叠。
     * 这里的核心是：按照end排序，然后总是选择比较小的end，使的后续有更大的可能性选择更多区间
     *
     * @param intervals
     */
    public static int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        int count = 1;
        int end = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            int[] cur = intervals[i];
            if (cur[0] >= end) {
                // 如果下一个的区间跟当前的不重复，加入下一个区间，并且重置end。
                // 这个end是可以新选择的，因为后续的end只会比当前的end更大，选择这个是安全的，没有后效性
                count++;
                end = cur[1];
            }
            // 其他情况，选择这个不安全，因为它当前没有增加不可覆盖，还使的end变大了，使的后续可能不能选择尽可能多的区间s
        }
        return intervals.length - count;
    }

    public static void testEraseOverlapIntervals() {
        int[][] intervals = new int[][]{
                {1, 2},
                {2, 3},
                {3, 4},
                {1, 3}
        };
        System.out.println(eraseOverlapIntervals(intervals));
    }

    public static void main(String[] args) {
        testEraseOverlapIntervals();
    }

}
