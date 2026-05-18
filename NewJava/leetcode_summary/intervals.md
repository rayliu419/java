# Intervals (区间问题)

> 核心性质：**排序后遍历处理重叠关系**；**按 start 排序适合合并/求并集，按 end 排序适合贪心最多不重叠**；**最小堆/扫描线求峰值并发数**

## 题目列表（按面试频次降序）

| 频次 | 题号 | 题目 | 难度 | 核心思路 | 状态 |
|:---:|:----:|------|:----:|---------|:----:|
| ★★★★★ | 56 | Merge Intervals | 🟡 Medium | 按 start 排序，遍历合并重叠：`cur[0] <= prev[1]` 时合并 | ✅ Intervals.java |
| ★★★★★ | 57 | Insert Interval | 🟡 Medium | 三段式：左不相交 → 合并重叠 → 右不相交（原数组已有序，可二分优化） | ✅ Intervals.java |
| ★★★★★ | 435 | Non-overlapping Intervals | 🟡 Medium | 按 end 排序 → 贪心保留最早结束的区间，删除 = 总数 - 最多保留数 | ✅ Intervals.java |
| ★★★★★ | 253 | Meeting Rooms II | 🟡 Medium | 最小堆 / 扫描线：堆存结束时间，堆的 size 峰值 = 最少会议室数 | ✅ Intervals.java |
| ★★★★ | 252 | Meeting Rooms | 🟢 Easy | 按 start 排序，检查相邻是否重叠 `cur[0] < prev[1]` | ⬜ |
| ★★★★ | 452 | Min Arrows to Burst Balloons | 🟡 Medium | 按 end 排序贪心射箭，重叠区间可用一支箭（与 435 同思路，`>=` 改 `>`） | ⬜ |
| ★★★★ | 986 | Interval List Intersections | 🟡 Medium | 双指针扫描两个已排序列表，每次取交集后移动 end 较小的指针 | ⬜ |
| ★★★★ | 763 | Partition Labels | 🟡 Medium | 先扫一遍记录每个字符最后出现位置，再扫一遍合并区间 | ⬜ |
| ★★★ | 729 | My Calendar I | 🟡 Medium | TreeMap / 二分：前一个的 end <= start 且下一个的 start >= end | ⬜ |
| ★★★ | 759 | Employee Free Time | 🔴 Hard | 合并所有区间 → 找缝隙（Merge Intervals 逆向） | ⬜ |
| ★★★ | 731 | My Calendar II | 🟡 Medium | 维护单重和双重预订列表，新 booking 与双重重叠则拒绝 | ⬜ |
| ★★★ | 1094 | Carpooling | 🟡 Medium | 差分数组：上下车点加减人数，遍历是否超载 | ⬜ |
| ★★ | 436 | Find Right Interval | 🟡 Medium | start 排序 + 二分查找每个 interval 的右侧区间 | ⬜ |
| ★★ | 352 | Data Stream as Disjoint Intervals | 🔴 Hard | TreeMap 维护区间，每次 addNum 合并相邻 | ⬜ |
| ★★ | 715 | Range Module | 🔴 Hard | TreeMap 区间合并 + 分裂，支持 add/remove/query | ⬜ |
| ★★ | 732 | My Calendar III | 🔴 Hard | 差分数组 / 线段树扫描 k 次重叠峰值 | ⬜ |
| ★★ | 1851 | Minimum Interval to Include Each Query | 🔴 Hard | 离线排序 + 优先队列，区间按长度入堆，延迟删除不包含 query 的区间 | ⬜ |

## 核心套路

```
Merge Intervals         → 按 start 排序，cur[0] <= prev[1] 时合并
Non-overlapping         → 按 end 排序贪心（结束越早给后面留越大空间）
Insert Interval         → 三段式：左 + 中间合并 + 右；也可二分 O(logn)
Meeting Rooms II        → 最小堆 / 扫描线（差分数组），堆 size 峰值 = 最大并发数

My Calendar I           → TreeMap 二分找前后区间
My Calendar II          → 单重 + 双重预订列表逐层判断
My Calendar III         → 差分数组 / 线段树求最大 k
Carpooling              → 差分数组 + 遍历

Interval Intersections  → 双指针，取交集后移动 end 较小的
Partition Labels        → lastIndex 扫两遍，等价于区间合并
Employee Free Time      → 合并所有区间后找间隙

Find Right Interval     → start 排序 + 二分查找
Range Module            → TreeMap 区间维护（分裂 + 合并）
Min Interval per Query  → 离线排序 + 懒删除堆
Data Stream as Disjoint → TreeMap 实时合并
```

## 刷题路线

```
第一梯队（必会）：56 → 57 → 435 → 253
第二梯队（高频）：252 → 452 → 986 → 763
第三梯队（进阶）：729 → 759 → 731 → 1094
第四梯队（扩展）：436 → 352 → 715 → 732 → 1851
```

## 实现文件

- `algorithm/src/main/java/leetcode/Intervals.java` — 主实现
- `algorithm/src/test/java/leetcode/IntervalsTest.java` — 测试

---

## 我的笔记

### 排序策略

```
按 start 排序  → Merge, Insert(已有序), Meeting Rooms, 找重叠
按 end 排序   → Erase, Arrows（贪心：选最早结束的，给后面留最多空间）
```

什么时候按 start，什么时候按 end？
- **要合并 / 求并集 / 判断是否冲突** → 按 start 排序，遍历时看当前区间是否与已合并的最后一个重叠
- **要保留最多不重叠区间** → 按 end 排序，每次选结束最早的，给后面留最大空间

### 重叠判断

两个区间 `[a1, a2]` 和 `[b1, b2]`（假设 a1 <= b1）：

```
重叠：a1 ----- a2
            b1 ----- b2
      条件：b1 <= a2

不重叠：a1 --- a2
                    b1 --- b2
      条件：a2 < b1
```

Java 代码：
```java
// 合并条件（按 start 排序后）
if (cur[0] <= prev[1]) {
    prev[1] = Math.max(prev[1], cur[1]);
} else {
    result.add(prev);
    prev = cur;
}

// 不重叠条件（Non-overlapping 判断，按 end 排序后）
if (cur[0] >= end) {
    count++;
    end = cur[1];
}
```

### 最小堆（Meeting Rooms II）

```java
PriorityQueue<Integer> heap = new PriorityQueue<>();
heap.offer(meetings[0][1]);
int min = 1;
for (int i = 1; i < meetings.length; i++) {
    int curStart = meetings[i][0];
    while (!heap.isEmpty() && heap.peek() <= curStart) {
        heap.poll(); // 已结束的会议出堆
    }
    heap.offer(meetings[i][1]);
    min = Math.max(min, heap.size());
}
return min;
```

原理：堆里存所有进行中会议的结束时间。新会议来之前，先把所有已结束的弹出，剩下的就是当前同时进行的会议。堆 size 的最大值就是答案。

为什么只检查堆顶？因为堆顶是最早结束的会议。如果最早结束的还没结束，其他的肯定都没结束。

### 扫描线（差分数组）

```
Meeting Rooms II 的另一种写法：
events:  (time, delta)
(0, +1), (30, -1), (5, +1), (10, -1), (15, +1), (20, -1)

排序后扫描：
time  0   5   10   15   20   30
      1   2    1    2    1    0
                    ↑ 峰值 = 2
```

扫描线适用于「求某时刻最大并发数」的场景，不限于 Meeting Rooms，还适用于 Carpooling、My Calendar III 等。

### Insert Interval 的二分优化

既然 intervals 已按 start 有序且无重叠，可以用二分找到 `newInterval.start` 和 `newInterval.end` 在数组中的位置：

```java
// 二分找到第一个 start > newInterval.end 的区间 —— 右侧边界
// 二分找到最后一个 end < newInterval.start 的区间 —— 左侧边界
// 中间的全被覆盖，直接替换为 newInterval
```

不过 O(n) 的三段式已经足够清晰，面试写三段式即可，二分是加分项。

### 题目关联图

```
Merge Intervals (56)
    ├── Insert Interval (57) — 有序版 + 二分优化
    ├── Partition Labels (763) — 字符串版的 merge
    ├── Employee Free Time (759) — merge 后取反
    └── Data Stream as Disjoint Intervals (352) — 在线 merge

Non-overlapping Intervals (435)
    └── Minimum Arrows to Burst Balloons (452) — 同一思路，判定条件从 >= 改成 >

Meeting Rooms II (253)                 — 最小堆 / 扫描线
    ├── Meeting Rooms (252)            — 判断版（不需要堆）
    ├── Carpooling (1094)              — 差分数组扫描
    ├── My Calendar I (729)            — 单重预订
    ├── My Calendar II (731)           — 双重预订
    └── My Calendar III (732)          — k 重预订

Interval List Intersections (986)      — 双指针
Find Right Interval (436)              — 二分查找
Range Module (715)                     — TreeMap 区间维护
Minimum Interval per Query (1851)      — 离线 + 堆
```

### 常见误区

1. **Merge Intervals 要不要用 `Math.max` 取 end？** 要。因为按 start 排序只保证前面的 start 更小，不保证后面的 end 更大，可能出现 `[1, 4], [2, 3]` 这种大包小的情况。

2. **Non-overlapping 为什么按 end 排序而不是 start？** 因为选 end 更小的区间，给后面留下的空间更大。举个反例：按 start 排序 `[1, 10], [2, 3], [4, 5]`，按 start 会保留 `[1,10]` 然后后面两个全删；按 end 会保留 `[2,3], [4,5]` 只删一个。

3. **Insert Interval 为什么不用 ArrayList.addAll 批量插入？** 用 `List` 三段式添加后再 `toArray` 本质上也是 O(n)，但胜在清晰。如果是数组原地操作（`ArrayList` 内部 `System.arraycopy`），批量删除一段也是 O(n)。
