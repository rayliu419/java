# Top K (第 K 大/小)

> 核心性质：**求无序数组中第 K 大/小 → QuickSelect 平均 O(n)；涉及"前 K 个高频/最近" → 堆 O(n log k)**

## 题目列表（按面试频次降序）

| 频次 | 题号 | 题目 | 难度 | 核心思路 | 状态 |
|:---:|:----:|------|:----:|---------|:----:|
| ★★★★★ | 215 | Kth Largest Element in an Array | 🟡 Medium | QuickSelect O(n) / 堆 O(n log k) | ✅ Kth.java |
| ★★★★★ | 347 | Top K Frequent Elements | 🟡 Medium | 频率统计 + 堆或桶排序 | ⬜ |
| ★★★★ | 703 | Kth Largest Element in a Stream | 🟢 Easy | 最小堆维护 Top K，堆顶即第 K 大 | ⬜ |
| ★★★★ | 973 | K Closest Points to Origin | 🟡 Medium | 最大堆 / QuickSelect / 排序 | ⬜ |
| ★★★★ | 378 | Kth Smallest Element in a Sorted Matrix | 🟡 Medium | K路最小堆 / 值域二分 | ✅ KSortedListMerge.java |
| ★★★★ | 692 | Top K Frequent Words | 🟡 Medium | 频率统计 + 堆（按频率+字典序排序） | ⬜ |
| ★★★ | 658 | Find K Closest Elements | 🟡 Medium | 二分找到左边界 + 双指针扩展 | ⬜ |
| ★★★ | 373 | Find K Pairs with Smallest Sums | 🟡 Medium | K路最小堆，每行=1个有序链表 | ✅ KSortedListMerge.java |
| ★★★ | 451 | Sort Characters By Frequency | 🟡 Medium | 频率统计 + 堆/bucket，相当于 TopK 的全排序版 | ⬜ |
| ★★★ | 23 | Merge k Sorted Lists | 🔴 Hard | 最小堆 / 分治两两合并 | ⬜ |
| ★★ | 414 | Third Maximum Number | 🟢 Easy | 三个变量模拟小顶堆，O(n) | ⬜ |
| ★★ | 264 | Ugly Number II | 🟡 Medium | DP 三指针 / 最小堆 | ⬜ |
| ★★ | 295 | Find Median from Data Stream | 🔴 Hard | 大顶堆+小顶堆，第k大的特化版（k=n/2） | ⬜ |

## 核心套路

```
无序数组第K大           → QuickSelect（原地分区）或最小堆
数据流第K大             → 最小堆维护 K 个元素，堆顶即答案
前K个高频元素           → 频率 Map + 最小堆（保持 K 个最高频）
最近K个点               → 最大堆（容量 K，淘汰最远的）/ QuickSelect
有序矩阵/多路第K小      → K路最小堆（每行/每序列一个指针）
频率排序               → 频率 Map + 桶排序 / 堆全部弹

QuickSelect 模板       → partition + 根据 rank 递归一侧，平均 O(n)，最坏 O(n²)
最小堆模板             → 容量 K，堆满后每次与堆顶比，更大的入堆、堆顶弹出
最大堆模板             → 容量 K，堆满后每次与堆顶比，更小的入堆、堆顶弹出
```

## 刷题路线

```
第一梯队（必会）：215 → 347 → 703 → 973 → 378
第二梯队（高频）：692 → 658 → 373 → 451
第三梯队（进阶）：23 → 414 → 264 → 295
```

## 实现文件

- `algorithm/src/main/java/leetcode/Kth.java` — QuickSelect 核心实现
- `algorithm/src/test/java/leetcode/KthTest.java` — QuickSelect 测试
- `algorithm/src/main/java/leetcode/KSortedListMerge.java` — K路合并求第K小

## 详细分析

### 1. Kth Largest Element in an Array

| 项目 | 内容 |
|------|------|
| 题号 | [215. Kth Largest Element in an Array](https://leetcode.com/problems/kth-largest-element-in-an-array/) |
| 复杂度 | QuickSelect 平均 O(n)，最坏 O(n²)；堆 O(n log k) |
| 实现 | `Kth.findKthElement()` — `Kth.java:63` |

**解法对比：**

| 解法 | 复杂度 | 空间 | 适用场景 |
|------|--------|------|---------|
| 排序 | O(n log n) | O(1) | 实现最简单 |
| 最小堆 | O(n log k) | O(k) | 数据流/海量数据，k 较小时 |
| QuickSelect | 平均 O(n) | O(1) | 数据在内存中，要求最高性能 |
| 插入排序（限 k 次内循环） | O(n·k) | O(1) | k 非常小时（如 k=1,2,3） |

**QuickSelect 详解：**

```text
算法流程：
1. 随机选 pivot，分区（partition）
2. 左区所有元素 ≤ pivot，右区所有元素 > pivot
3. pivot 最终位置 = p，则 pivot 是第 (p - low + 1) 小的元素（相对排名）
4. 比较排名与 k，递归进入左区或右区

关键技巧：将 pivot 交换到 high 位置再分区
     → 遍历范围变成 [low, high)，不需要处理 pivot 本身
     → 逻辑更简洁，边界条件更少
```

**第 K 大 ↔ 第 K 小 转换：**
```java
// 第 K 大 = 第 (n - K + 1) 小
int kthLargest = findKthElement(array, array.length - k + 1);
```

**关于相对排名的说明（易错点）：**
```java
// partition 返回的是绝对索引 p
// 需要用 rank = p - low + 1 转为当前区间的相对排名（1-based）
// 不能直接用 p + 1 == k 判断，因为递归进入右半边后 k 已减去左侧元素数
if (rank == k)      return array[p];     // 找到了
if (rank > k)       搜索左半边             // k 不变
if (rank < k)       搜索右半边             // k = k - rank
```

**QuickSelect 复杂度证明（递归树）：**
```
平均情况下：
第1层：扫描 n 个元素
第2层：扫描 n/2 个元素（假设平均分到一边）
第3层：扫描 n/4 个元素
...
总工作量 = n + n/2 + n/4 + ... = n × (1 + 1/2 + 1/4 + ...) = 2n = O(n)
```

**Bug 热点：**
- 把第 K 大当第 K 小求，忘记 `n - k + 1` 转换
- 递归时未将全局下标转为相对排名，导致死递归
- 随机选 pivot 时 `nextInt(upper - lower + 1)` 忘记 `+1`

---

### 2. Top K Frequent Elements

| 项目 | 内容 |
|------|------|
| 题号 | [347. Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/) |
| 复杂度 | O(n log k) / O(n + k)（桶排序版） |

**思路：**

```text
解法一：最小堆（O(n log k)）
1. 频率 Map 统计每个数字出现次数
2. 遍历 entry，用最小堆维护 Top K（堆大小为 k）
3. 堆满后，只有频率大于堆顶才入堆并弹出堆顶

解法二：桶排序（O(n)，但非原地）
1. 频率 Map 统计
2. 将数字按频率放入桶中（下标 = 频率）
3. 从高到低取 K 个

解法三：QuickSelect（平均 O(n)）
1. 频率 Map 统计
2. 对频率数组做 QuickSelect 找到第 K 高的频率
3. 收集所有频率 ≥ 该阈值的元素
```

---

### 3. Kth Largest Element in a Stream

| 项目 | 内容 |
|------|------|
| 题号 | [703. Kth Largest Element in a Stream](https://leetcode.com/problems/kth-largest-element-in-a-stream/) |
| 模式 | 最小堆维护 Top K |
| 复杂度 | 初始化 O(n log k)，每次 add O(log k) |

**思路：**

```text
维护一个容量为 k 的最小堆：
- 堆中始终保持当前数据流中最大的 k 个元素
- 堆顶就是第 k 大的元素（因为是最小堆，堆顶是这 k 个中最小 = 全局第 k 大）
- 新元素来后：
  a) 堆未满 → 直接入堆
  b) 堆已满且新元素 > 堆顶 → poll + add
  c) 堆已满且新元素 ≤ 堆顶 → 不变
```

**代码：**
```java
class KthLargest {
    PriorityQueue<Integer> minHeap;
    int k;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.minHeap = new PriorityQueue<>(k);
        for (int n : nums) add(n);
    }

    public int add(int val) {
        if (minHeap.size() < k) minHeap.offer(val);
        else if (val > minHeap.peek()) {
            minHeap.poll();
            minHeap.offer(val);
        }
        return minHeap.peek();
    }
}
```

---

### 4. K Closest Points to Origin

| 项目 | 内容 |
|------|------|
| 题号 | [973. K Closest Points to Origin](https://leetcode.com/problems/k-closest-points-to-origin/) |
| 复杂度 | 最大堆 O(n log k)，QuickSelect O(n) |

**思路：**

```text
与第 K 大不同：这里要找最近的 K 个点（即第 K 小的距离）。

距离 = x² + y²（不需要开平方，比较平方即可）。

解法一：最大堆
- 堆中保持 K 个最近的点（堆顶是当前最远的）
- 新点距离 < 堆顶距离 → 弹出堆顶，入堆新点

解法二：QuickSelect
- 计算所有距离，在距离数组上做 QuickSelect 找到第 K 小的距离
- 输出距离 ≤ 该阈值的点

解法三：排序（O(n log n)）
- 面试中不够
```

---

### 5. Kth Smallest Element in a Sorted Matrix

| 项目 | 内容 |
|------|------|
| 题号 | [378. Kth Smallest Element in a Sorted Matrix](https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/) |
| 模式 | K路最小堆 / 值域二分 |
| 实现 | `KSortedListMerge.findKthInMatrix()` — `KSortedListMerge.java:125` |
| 复杂度 | 堆 O(k log m)，二分 O(n log(max-min)) |

**思路：**

详见 [k-sorted-merge.md](k-sorted-merge.md) 题目详解部分。

每行是一个有序数组 → m 个有序数组求第 K 小。

- **堆解法：** 每行第一个元素入堆，poll K 次
- **二分法：** 值域二分，统计 `≤ mid` 的元素个数

---

### 6. Top K Frequent Words

| 项目 | 内容 |
|------|------|
| 题号 | [692. Top K Frequent Words](https://leetcode.com/problems/top-k-frequent-words/) |
| 复杂度 | O(n log k) |

**思路：**

347 的进阶版：频率相同时按字典序排序。

```text
堆的比较器：
1. 先按频率升序（最小堆，频率低的在堆顶，会被淘汰）
2. 频率相同则按字典序降序（这样字典序大的在堆顶，会被优先淘汰）

最终取出时反转结果（因为最小堆取出的是频率最小的先出）。
```

---

### 7. Find K Closest Elements

| 项目 | 内容 |
|------|------|
| 题号 | [658. Find K Closest Elements](https://leetcode.com/problems/find-k-closest-elements/) |
| 复杂度 | O(log n + k) （二分 + 双指针） |

**思路：**

数组已排序，找到离 x 最近的 K 个元素（距离绝对值最小）。

```text
1. 二分找到 x 在数组中的插入位置（或最接近 x 的下标作为左边界起点）
2. 左指针 = idx，右指针 = idx + 1
3. 双指针向两侧扩展，比较 arr[left] 和 arr[right] 谁离 x 更近
   - 左指针扩展：left--
   - 右指针扩展：right++
   扩展 K 次即可
4. 结果 = arr[left+1 .. right-1]
```

**注意：** 这是**二分查找 + 双指针**的混合题，不是典型的堆或 QuickSelect。

---

### 8. Top K 四类解法总览

```java
// ============ 1. 直接排序 ============
Arrays.sort(nums);
return nums[n - k];

// ============ 2. 最小堆（第K大） ============
PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);
for (int n : nums) {
    if (minHeap.size() < k) minHeap.offer(n);
    else if (n > minHeap.peek()) {
        minHeap.poll();
        minHeap.offer(n);
    }
}
return minHeap.peek();

// ============ 3. 最大堆（第K小 / K个最近） ============
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a,b) -> b - a);
// 逻辑同上，比较关系反转

// ============ 4. QuickSelect（无序数组最优） ============
// 见 Kth.java findKthElement 实现
// 第K大 = 第(n-K+1)小
```

## 相关专题

- [K-Way Merge (K个有序列表合并)](k-sorted-merge.md) — 多路合并场景的 Top K（有序矩阵、最小 Pair 和、Ugly Number）
- [Stack & Sorting](string-array.md) — 涉及排序的相关内容
