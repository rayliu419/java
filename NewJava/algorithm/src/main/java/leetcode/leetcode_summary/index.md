# LeetCode Summary

> 分类记录面试高频题型，包含题目信息、核心思路和实现状态。

## 专题目录

| 专题 | 核心内容 | 文件 |
|------|---------|------|
| BST (二叉搜索树) | 中序性质、增删查改、中序相关题型 | [bst.md](bst.md) |
| Linked List (链表) | dummy 节点、快慢指针、反转、合并、环检测、相交、排序 | [linklist.md](linklist.md) |
| K-Way Merge (K个有序列表合并) | 最小堆合并 K 个有序序列、有序矩阵第 K 小、K个最小 Pair 和、最小区间覆盖、Ugly Number | [k-sorted-merge.md](k-sorted-merge.md) |
| BFS (广度优先搜索) | 二叉树层次遍历、多源BFS、图BFS、拓扑排序、矩阵BFS、Word Ladder | [bfs.md](bfs.md) |
| Intervals (区间) | Merge、Insert、Non-overlapping、Meeting Rooms II、扫描线/最小堆 | [intervals.md](intervals.md) |
| Matrix (矩阵) | 旋转、转置、螺旋遍历、置零、二维搜索、DFS/BFS | [matrix-visit.md](matrix-visit.md) |
| String & Array (字符串与数组) | 反转单词、相加/相乘、旋转数组、缺失重复数、回文、括号 | [string-array.md](string-array.md) |
| Two Pointer (双指针) | 左右指针、正反指针、快慢指针、三数之和、盛水、股票买卖、颜色排序、加油站 | [two-pointer.md](two-pointer.md) |
| Top K (第 K 大/小) | QuickSelect、最小堆、最大堆、前 K 个高频/最近元素、数据流第 K 大 | [topk.md](topk.md) |
| Prefix Sum (前缀和) | 子数组和为K、前缀积、二维前缀和、差分数组、前缀异或、Kadane、取模/状态压缩 | [prefix-sum.md](prefix-sum.md) |
| Stack (栈) | 单调栈、表达式求值、Valid Parentheses、Largest Rectangle、接雨水、括号匹配 | [stack.md](stack.md) |
| 递归 → 非递归推导 | 机械推导方法 + 6种递归模式的完整图谱 + 推导工作流 | [recursion-to-iteration.md](recursion-to-iteration.md) |

## 通用实现文件

- `algorithm/src/main/java/leetcode/` — 主实现
- `algorithm/src/test/java/leetcode/` — 测试

---

## 总体经验

1. **按类别刷题。** 不熟悉的多个题目一起刷，有利于找出规律。
2. **The ability to map a new problem to an already known problem.** 不在于一定要多，但是要能具有能将新问题映射到已知问题的能力。

## 算法复杂度

常见 loop 的复杂度。

**O(1)**
```java
for (int i = 0; i <= c; i++) {
    // O(1) operation
}
```

**O(n)**
```java
for (int i = 0; i <= n; i += c) {
    // O(1) operation
}

for (int i = n; i > 0; i -= c) {
    // O(1) operation
}
```

**O(n^c)**
```java
for (int i = 0; i <= n; i += c) {
    for (int j = 1; j < n; j += c) {
        // O(1) operation
    }
}
```

**O(lg n)**
```java
for (int i = 0; i <= n; i *= c) {
    // O(1) operation
}
```

**O(lg lg n)**
```java
for (int i = 0; i <= n; i = pow(i, c)) {
    // O(1) operation
}
```

### n 个含 m 个元素的有序表合并

**1. 两两归并的时间复杂度**

- 第一层：n/2 次，每次 2 个表，每个表长度 m → n/2 × (m + m) = mn
- 第二层：n/4 次，每次 2 个表，每个表长 2m → n/4 × (2m + 2m) = mn
- 一共 lg n 层

**时间复杂度：O(mn lg n)**

**2. 借助一个堆**

- 建堆 — O(n)
- 一共要 pop mn 次，每次调整耗费 O(lg n)
- O(n) + O(mn lg n) = **O(mn lg n)**

可以看出，上面两个的计算方式都是直接计算 count 来做的。

### 复杂度分析要点

- 如果含有很多 if-else 语句，通常用 worst case 来估计时间复杂度。
- 如果是递归的方法，递归树是最靠谱的方法。
- 如果太复杂，就计算一个算法复杂度上限。

## 不熟悉题目 / 需重点复习

| 题目 | 要点 |
|------|------|
| Implement Trie (Prefix Tree) / Add and Search Word | Trie 树的实现 |
| Minimum Size Subarray Sum (O(n lg n) 方法) | 二分查找 + cumulative sum 结合使用，提供了一种新思路 |
| Recurring Decimal | 思路有误，注意 string insert 函数对 pos 和 iterator 参数行为不同，且是插在 pos 之前 |
| Unique Binary Search Trees II | 递归建立的过程 |
| Largest Rectangle in Histogram | 比较难，想明白单调栈的核心逻辑 |
| Permutation 系列 | 全排列相关题型的规律 |
| Text Justification | 这种字符串排版处理是不熟练的，需要多练习 |
| Max Points on a Line | 不能用 map\<float\>，要存经过 gcd 化简后的 pair |
| Best Time to Buy and Sell Stock IV | 股票系列第 4 题，状态机 DP |
| 较难的二叉树题目 | 递归和最优化问题的综合
