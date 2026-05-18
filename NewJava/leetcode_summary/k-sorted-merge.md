# K 个有序列表合并（K-Way Merge）

> 核心模式：将 K 个有序序列合并/求 TopK，统一用**最小堆**维护 K 个指针（每个序列当前最小候选），每次 poll 出全局最小，并将该序列的下一个元素入堆。

**复杂度**：建堆 O(K)，每次 poll+push O(logK)，总 O(N logK)，N = 所有元素总数。

## 题目列表（按面试频次降序）

| 频次 | 题号 | 题目 | 难度 | 核心思路 | 状态 |
|:---:|:----:|------|:----:|---------|:----:|
| ★★★★★ | 23 | Merge k Sorted Lists | 🔴 Hard | 最小堆 / 分治两两合并，**K-Way Merge 原型题** | ⬜ |
| ★★★★ | 378 | Kth Smallest Element in a Sorted Matrix | 🟡 Medium | 行/列有序 → 每行=1个有序数组，堆取TopK；也可值域二分 | ✅ KSortedListMerge.java |
| ★★★★ | 373 | Find K Pairs with Smallest Sums | 🟡 Medium | 以短数组为行，每行是1条有序链表，堆取前K个最小和 | ✅ KSortedListMerge.java |
| ★★★ | 632 | Smallest Range Covering Elements from K Lists | 🔴 Hard | K路指针移动最小值以缩小区间，堆取min，维护max | ⬜ |
| ★★★ | 264 | Ugly Number II | 🟡 Medium | 合并3个有序序列（×2, ×3, ×5），三指针/DP；也可堆 | ⬜ |
| ★★ | 313 | Super Ugly Number | 🟡 Medium | 推广到K个质数→K个序列→K个指针，兼容堆解法 | ⬜ |
| ★★ | 786 | K-th Smallest Prime Fraction | 🔴 Hard | 固定分母，每个分母=1条有序链表；还可二分 | ⬜ |
| ★ | 1508 | Range Sum of Sorted Subarray Sums | 🟡 Medium | 子数组和构成K个有序序列，堆取前K个 | ⬜ |

## 核心套路

```
K个有序链表合并       → 每个链表头入堆，poll后push next
有序矩阵第K小         → 每行头入堆，poll K次
K个最小Pair和         → 以短数组为行（固定），长数组为列，存[row, col, sum]
最小区间覆盖K列表     → 堆取min + 维护max，移动min所在指针缩小区间
Ugly Number           → 合并 ×p1, ×p2, ..., ×pk 多个有序序列

链表指针移动          → node.next
数组/矩阵指针移动     → [row, col+1, value]
Pair指针移动          → [row, col+1, nums1[row]+nums2[col+1]]

二分替代方案          → 值域二分适用于「求第K小」（378, 373, 786）
                      不适用于「合并全部序列」
```

## 刷题路线

```
第一梯队（必会）：23 → 378 → 373
第二梯队（高频）：632 → 264
第三梯队（进阶）：313 → 786 → 1508
```

## 实现文件

- `algorithm/src/main/java/leetcode/KSortedListMerge.java` — Merge K 链表、K个最小Pair、有序矩阵第K大
- `algorithm/src/main/java/leetcode/Kth.java` — QuickSelect 求第 K 大/小

---

## 核心统一解法

```text
1. 建堆（K个元素）：所有序列头部入堆
2. 循环：
   a. poll() 出最小值
   b. (求TopK则加入结果，若满K则结束)
   c. 将该序列的下一个元素入堆
3. 直到堆空（合并全部）或结果数=K（求TopK）
```

**数据结构的选择**：
- **链表**：堆中存 `Node`，next 指针天然指向下一个 → `KSortedListMerge.java:38`
- **数组/矩阵**：堆中存 `[行, 列, 值]`，下一个 = `[行, 列+1, matrix[行][列+1]]` → `KSortedListMerge.java:125`
- **Pair求和**：堆中存 `[行, 列, nums1[行]+nums2[列]]`，下一个 = `[行, 列+1, nums1[行]+nums2[列+1]]` → `KSortedListMerge.java:87`

## 题目详解

### 1. [23] Merge k Sorted Lists

**解法一：最小堆**
```text
Step 1: 所有链表头入堆（K个元素）
Step 2: poll() 出最小值加入结果
Step 3: 将该节点的 next 入堆
Step 4: 重复 Step 2-3 直到堆空
```
**解法二：分治合并（两两合并）**
```text
while (lists.size() > 1) {
    两两配对 merge(lists[i], lists[i+1])
    结果存入下一轮
}
```
两者时间复杂度都是 O(N logK)。堆思路更直观但需 O(K) 空间；分治空间 O(1)。

**外排序场景**：序列太大不能全部装入内存时，分治（两两合并写回磁盘）可以控制内存使用，每次只加载两个序列。

### 2. [378] Kth Smallest Element in a Sorted Matrix

矩阵行和列各自递增 → **每行是一个有序数组** → M 个有序数组求第 K 小。

- **堆解法**：`KSortedListMerge.java:125`，每行第一个元素入堆，poll K 次
- **二分查找（更优）**：值域二分，统计 `<= mid` 的个数，用左下角计数法 O(n log(max-min))

### 3. [373] Find K Pairs with Smallest Sums

以较短的数组做行，固定 `nums1[i]` 后，`nums2` 是有序的 → 每行 = 1 条有序链表。

```text
     nums2 →  2    4    6
         ↓  ┌──────────────────┐
nums1: 1    │ (1,2)  (1,4)  (1,6) │  和: 3, 5, 7
        7    │ (7,2)  (7,4)  (7,6) │      9, 11, 13
       11    │(11,2) (11,4) (11,6)│     13, 15, 17
            └──────────────────┘

      取TopK过程（堆视角）：
      head入堆 →  (1,2)  (7,2)  (11,2)      min=(1,2)
      poll(1,2)→ 入堆(1,4)                   min=(1,4)
      poll(1,4)→ 入堆(1,6)                   min=(1,6)
      poll(1,6)→ 行1耗尽                     min=(7,2) → ...
```

**实现**：`KSortedListMerge.java:87`，堆存 `[row, col, sum]`

## 建堆时间复杂度证明

```text
完全二叉树，树高为 h 的层有 n/2^h 个节点，每个节点建堆代价 O(h)
总复杂度 = Σ(n/2^h) × O(h) {h=0 → lgn}
         = O(n × Σ(h/2^(h-1)) {h=0 → lgn})
         = O(n)   （因为 Σ(h/2^(h-1)) 收敛于 1）
```
