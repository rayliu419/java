package leetcode;

import java.util.*;

public class Intervals {

    /**
     * https://leetcode.com/problems/merge-intervals/
     * 根据start排序，不断合并
     *
     */
    public static int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        // 使用ArrayList可以动态的添加，避免了
        List<int[]> result = new ArrayList<>();
        int[] prev = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            int[] cur = intervals[i];
            if (cur[0] <= prev[1]) {
                prev[1] = Math.max(prev[1], cur[1]);
            } else {
                result.add(prev);
                prev = cur;
            }
        }
        result.add(prev);
        // List<int[]> → int[][], 传入0长度数组作为类型标记
        // 等效写法还有 result.toArray(new int[result.size()][])
        return result.toArray(new int[0][]);
    }

    /**
     * https://leetcode.com/problems/insert-interval/
     *
     * 在有序无重叠区间中插入新区间，分三段处理：左侧不相交 → 合并重叠 → 右侧不相交
     */
    public static int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;
        // 左侧：所有终点 < 新区间起点的，直接加入
        while (i < intervals.length && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }
        // 中间：所有与新区间重叠的，合并
        while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        result.add(newInterval);
        // 右侧：剩余的直接加入
        while (i < intervals.length) {
            result.add(intervals[i]);
            i++;
        }
        return result.toArray(new int[0][]);
    }

    /**
     * Meeting room II
     *   最小堆思
     *   堆里存当前所有正在进行的会议的结束时间，堆的大小就是当前同时进行的会议数。
     *   步骤
     *   1. 按 start 排序所有会议
     *   2. 遍历每个会议，把它的 end 加入堆
     *   3. 每次加入前，先把堆中所有已经结束的会议（end <= 当前 start）弹出
     *   4. 堆的 size 最大值就是最少会议室数
     *
     *   堆顶是最早结束的会议。每次新会议来的时候，只关心最早结束的那个是否已经结束：
     *   如果最早结束的还没结束（堆顶 > 当前 start），那其他更晚结束的肯定也没结束，不用再看了
     *   如果最早结束的已经结束了，就把它弹出，直到堆顶的会议还没结束
     */
    public static int minMeetingRooms(int[][] meetings) {
        if (meetings.length == 0) return 0;
        // [start, end] 中的start 排序
        Arrays.sort(meetings, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(meetings[0][1]);
        int min = 1;
        for (int i = 1; i < meetings.length; i++) {
            int curStart = meetings[i][0];
            popUpFinishedMeetings(curStart, priorityQueue);
            priorityQueue.add(meetings[i][1]);
            min = Math.max(min, priorityQueue.size());
        }
        return min;
    }

    private static void popUpFinishedMeetings(int start, PriorityQueue<Integer> priorityQueue) {
        while (!priorityQueue.isEmpty()) {
            int minEnd = priorityQueue.peek();
            if (minEnd <= start) {
                priorityQueue.poll();
            } else {
                break;
            }
        }
    }

    /***
     *
     * https://leetcode.com/problems/non-overlapping-intervals/description/
     *
     * 擦除最少的区间，使剩余区间不重叠。
     * 核心思路：按 end 排序，贪心地选最早结束的区间，给后面留更多空间。
     * 等价于反过来算——最多能保留多少个不重叠的区间？总数 - 保留数 = 最小删除数
     *
     * @param intervals
     */
    public static int eraseOverlapIntervals(int[][] intervals) {
        // 按 end 升序排序：结束越早，后续可选空间越大（贪心核心）
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));

        // count: 最多能保留的不重叠区间数（至少保留第一个）
        // end:   当前已选区间链的最后一个终点
        //        初始取第一个区间[0]的终点作为链的末尾
        int count = 1;
        int end = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            int[] cur = intervals[i];

            if (cur[0] >= end) {
                // cur 与已选链不重叠 → 可以安全加入保留链
                // 保留链增加一个区间，末端后移到 cur 的终点
                count++;
                end = cur[1];
            }
            // cur[0] < end → cur 与保留链重叠，必须删掉 cur
            // 为什么不删已选的而删 cur？因为已选的 end 更小（按 end 排序过的），
            // 保留 end 小的比保留 cur 更有利于后面的区间，更优
        }

        // 删掉的 = 总数 - 最多能保留的
        return intervals.length - count;
    }

}
