# Binary Search (二分查找)

## 题目列表（按面试频次降序）

| 频次 | 题号 | 题目 | 难度 | 核心思路 | 状态 |
|:---:|:----:|------|:----:|---------|:----:|
| ★★★★★ | 704 | Binary Search | 🟢 Easy | 标准二分模板，左右指针收缩 | ✅ BinarySearch.java |
| ★★★★★ | 35 | Search Insert Position | 🟢 Easy | 找第一个 `≥ target` 的位置 | ✅ BinarySearch.java |
| ★★★★★ | 34 | Find First and Last Position | 🟡 Medium | 左右边界分别二分查找 | ⬜ |
| ★★★★★ | 69 | Sqrt(x) | 🟢 Easy | 数值二分，平方比较防溢出 | ⬜ |
| ★★★★ | 33 | Search in Rotated Sorted Array | 🟡 Medium | 先判哪半有序，再二分 | ⬜ |
| ★★★★ | 153 | Find Min in Rotated Sorted Array | 🟡 Medium | 比较 mid 与 high，无重复 | ⬜ |
| ★★★★ | 162 | Find Peak Element | 🟡 Medium | 比较 mid vs mid+1，向高侧收缩 | ⬜ |
| ★★★ | 74 | Search a 2D Matrix | 🟡 Medium | 二维映射一维做二分 | ⬜ |
| ★★★ | 875 | Koko Eating Bananas | 🟡 Medium | 答案二分：check 每小时能否吃完 | ⬜ |
| ★★★ | 1011 | Ship Packages Within D Days | 🟡 Medium | 答案二分：check 能否 D 天内运完 | ⬜ |
| ★★★ | 278 | First Bad Version | 🟢 Easy | first occurrence 变体 | ⬜ |
| ★★★ | 367 | Valid Perfect Square | 🟢 Easy | 类似 Sqrt，判断完全平方 | ⬜ |
| ★★ | 81 | Search in Rotated Sorted Array II | 🟡 Medium | 含重复 → 去重处理 (high--) | ⬜ |
| ★★ | 154 | Find Min in Rotated Sorted Array II | 🔴 Hard | 含重复旋转数组最小值 | ⬜ |
| ★★ | 540 | Single Element in a Sorted Array | 🟡 Medium | 奇偶下标性质 | ⬜ |
| ★★ | 4 | Median of Two Sorted Arrays | 🔴 Hard | 划分数组 + 二分，O(log(m+n)) | ⬜ |
| ★★ | 658 | Find K Closest Elements | 🟡 Medium | 二分找插入点 + 双指针扩展 | ⬜ |
| ★★ | 378 | Kth Smallest in Sorted Matrix | 🟡 Medium | 二分值域 + 计数 | ⬜ |
| ★★ | 410 | Split Array Largest Sum | 🔴 Hard | 最小化最大值：答案二分经典题 | ⬜ |
| ★ | 50 | Pow(x, n) | 🟡 Medium | 快速幂：二分思想（x^n = x^(n/2)²） | ⬜ |
| ★ | 29 | Divide Two Integers | 🟡 Medium | 倍增 + 减法实现除法 | ⬜ |
| ★ | 300 | Longest Increasing Subsequence | 🟡 Medium | 耐心排序：tails 数组二分优化 DP | ⬜ |
| ★ | 354 | Russian Doll Envelopes | 🔴 Hard | 二维 LIS | ⬜ |
| ★ | 174 | Dungeon Game | 🔴 Hard | 二分血量 + DP check | ⬜ |

## 核心套路

```
精确查找    → 标准二分，相等即返回
左边界      → 找到后 high = mid - 1 继续往左
右边界      → 找到后 low = mid + 1 继续往右
插入位置    → lower_bound 找第一个 ≥ target
旋转数组    → 比较 mid 与 high/low，判断哪半有序
答案二分    → 在值域上二分，check 函数判断可行性
数值二分    → 整数平方根/除法，注意溢出用 long
二维二分    → 映射为一维 / 右上角 Z 字形
矩阵第 K 小 → 二分值域 + 每行计数
中位数      → 划分数组 + 二分，奇偶统一处理
求峰值      → 比较 mid 与 mid+1，往上升方向收缩
快速幂      → 分治 x^n = x^(n/2) * x^(n/2) * (n%2 ? x : 1)
LIS         → tails[i] 表示长度为 i+1 的递增子序列的最小末尾
```

## 刷题路线

第一梯队（必会）：704 → 35 → 34 → 69 → 33 → 153
第二梯队（高频）：162 → 74 → 875 → 1011 → 278 → 367
第三梯队（进阶）：81 → 154 → 540 → 4 → 658 → 378 → 410

## 学习笔记

---

### 一、核心心法

> 二分查找的每一轮循环开始前，我们都**确信答案一定在 [low, high] 范围内**（如果存在的话）。这个确信的结论就是"不变量"。根据 mid 与 target 的比较结果，我们缩小区间，同时保证这个不变量仍然成立。当区间缩小到空集（或只剩一个元素）时，答案就找到了。

#### 概览

- **什么时候用二分？** 只要数据是**有序的，或者局部有序的**，就应该适当考虑二分查找的方法。
- **猜数法**：需要猜数或者用不同的数来试某个解时，使用二分法快速试错。

#### 解题思维顺序

碰到一道二分题时，按这个顺序推导：

**第 1 步：看能否排除 mid → 决定 low/high 更新方式**

核心问题：**mid 有没有可能是最终答案？**

- `nums[mid]` 肯定不是答案（如 `nums[mid] > target` 且要找 target）→ `low = mid + 1` / `high = mid - 1`
- `nums[mid]` 可能就是答案（如找左边界，`nums[mid] >= target` 时 mid 可能是第一个）→ `low = mid` / `high = mid`

**第 2 步：看更新方式 → 决定循环条件**

| 更新方式 | 循环条件 | 原因 |
|---------|---------|------|
| `mid ± 1` | `<=` | 每次范围至少减 1，`low > high` 自然退出 |
| `high = mid` 或 `low = mid` | `<` | mid 可能被保留，范围不必然减 1，`low == high` 时终止 |

**第 3 步：看题意 → 决定 mid 取整方向**

- 需要**靠左**决策（如找左边界，`high = mid`）→ 下取整 `mid = low + (high - low) / 2`
- 需要**靠右**决策（如找右边界，`low = mid`）→ **必须上取整** `mid = low + (high - low + 1) / 2`，否则 `low + 1 == high` 时死循环

三步走完，二分查找的三个决定性因素就全部确定了。

#### 三个决定性因素

| 因素 | 说明 |
|------|------|
| **区间定义** | 左闭右闭 `[low, high]` vs 左闭右开 `[low, high)` |
| **mid 取整方向** | `low + (high - low) / 2`（下取整）vs `low + (high - low + 1) / 2`（上取整） |
| **收缩方式** | `low = mid + 1` / `high = mid - 1` vs `low = mid` / `high = mid` |

这三个因素必须相互匹配，否则死循环或越界。


#### 循环条件与收缩规则的深层原理

`while (low <= high)` 和 `while (low < high)` 的根本区别在于：**mid 是否能被安全地排除出搜索空间。**

**核心直觉**：搜索空间是一个集合 `[low, high]`，每次循环要缩小这个集合。缩小方式由 `mid` 是否可能是最终答案来决定。

**场景一：mid 可以被排除（标准二分）**

当我们知道 `nums[mid] != target` 后，`mid` 肯定不是答案，可以大胆排除。于是：
```
low = mid + 1    // mid 及其左侧全都排除
high = mid - 1   // mid 及其右侧全都排除
```
每次范围至少缩小 1，`low <= high` 作为终止条件完全安全 — 迟早 `low > high` 退出。

**场景二：mid 可能还是答案（边界查找 / 变体）**

```java
// 左边界：找第一个 >= target 的数
if (nums[mid] >= target)  high = mid;   // mid 可能就是答案，不能排除！
```
当 `nums[mid] >= target` 成立时，`mid` 有可能是第一个符合条件的数，所以 `high = mid` 而不是 `mid - 1`。

但问题来了：如果 `high = mid`，`low` 更新为 `mid + 1`，当 `low + 1 == high` 时：
```
low = k, high = k+1
mid = low + (high - low) / 2 = k   // 下取整
如果命中条件 → high = mid = k → low = k, high = k → low == high
```
此时如果还用 `low <= high`，`low == high` 时还会进循环，而 `mid = low = high`，如果又命中条件 → `high = mid = low` → **死循环**！

所以**当出现 `high = mid` 或 `low = mid` 时，必须用 `low < high`**。循环在 `low == high` 时终止，此时唯一剩下的元素就是答案。

**场景三：low = mid 时为什么必须上取整？**

```java
// 右边界：找最后一个 <= target 的数
if (nums[mid] <= target)  low = mid;   // mid 可能就是答案
```
当 `low = mid` 且 `low + 1 == high` 时：
```
low = k, high = k+1
mid = low + (high - low) / 2 = k      // 下取整！
如果命中条件 → low = mid = k → low 没变 → 死循环！
```
必须改成**上取整**：
```
mid = low + (high - low + 1) / 2 = k+1
如果命中条件 → low = mid = k+1 → low = high → 退出 ✅
```

**三段论总结：**

| 收缩方式 | 能否排除 mid | 循环条件 | 取整方式 | 原因 |
|---------|------------|---------|---------|------|
| `low = mid+1`, `high = mid-1` | ✅ 排除 mid | `<=` | 下取整 | 范围每次至少减 1，不会死循环 |
| `low = mid+1`, `high = mid` | ❌ mid 可能保留 | `<` | 下取整 | 保留 mid 意味着范围不必然减 1，`<` 在 `low==high` 时终止 |
| `low = mid`, `high = mid-1` | ❌ mid 可能保留 | `<` | **上取整** | `low = mid` 必须上取整，否则 `low+1==high` 时死循环 |

---



### 三、实现细节

#### mid 取整方向

- `mid = low + (high - low) / 2` — mid 总是**偏向左边**（偶数长度时取左中）或中间（奇数长度）。
- `mid = low + (high - low + 1) / 2` — mid 总是**偏向右边**（偶数长度时取右中）或中间（奇数长度）。
- `low = mid` 时 → 必须用**上取整**，否则死循环。
- `high = mid` 时 → 必须用**下取整**，否则死循环。

#### 循环条件的选择

- `while (low <= high)` → **闭区间** `[low, high]`。low 和 high 都可能被检查到。
  - 移动 low 或 high 后，最终以 `low > high` 退出循环。一般最后检查 `low` 的情况。
- `while (low < high)` → **半开区间** `[low, high)`。终止时 `low == high` 即为答案。
- 选择 `<=` 还是 `<`，取决于解可能在**闭区间还是半开区间**。

#### 收缩方式

- 常规算法：`low = mid + 1`，`high = mid - 1`。
- 变体：`low = mid`，`high = mid`，主要看设置会不会把解漏掉。一般来说只会取一种，否则可能死循环。

#### 如何写出 bug free 的二分

二分查找最大的问题是很难写出 bug free 的代码，策略：**用 test case 驱动，很难直接思考。**

**边界条件单独处理**：
- `low` 或 `high` **从未被重置过**的情况。这种可能能被后面统一处理，如果比较复杂，就单独拉出来讨论。
- 剩下的就是 `low` 和 `high` **都被重置过**的。

**插入位置 test case**：
- 奇数数组：`[1, 3, 5]` 插入 `2`；`[1, 3, 5]` 插入 `4`。
- 偶数数组：`[1, 3, 5, 7]` 插入 `2`；`[1, 3, 5, 7]` 插入 `4`。

#### ans 变量的使用

变体题设置一个 `ans` 变量，记录最后 break 出循环的解，因为可能 break 出去以后，下次就找不到更好的解了。

**某些情况下，break 出去还得检查最后的 `low` 是不是解。** 变体中，最后的解是在循环中 `ans` 的解和 `low` 的解中的一个。

---

### 四、各题要点

---

#### Binary Search（704）⭐⭐⭐⭐⭐
- 最基础的二分模板，建议一次性写对
- 终止条件 `low <= high`，每次更新 `mid ± 1`

---

#### Search Insert Position（35）⭐⭐⭐⭐⭐
- 等价于 lower_bound：找第一个 `≥ target` 的下标
- 如果所有数都 `< target`，返回 `nums.length`
- 可以直接用 left 边界的写法，最终返回 `low`

```java
int searchInsert(int[] nums, int target) {
    int low = 0, high = nums.length - 1;
    while (low <= high) {
        int mid = low + (high - low) / 2;
        if (nums[mid] >= target)  high = mid - 1;
        else                      low = mid + 1;
    }
    return low;
}
```

---

#### First and Last Position（34）⭐⭐⭐⭐⭐
- 左右边界分解成两个独立函数，各调一次
- 左边界：`nums[mid] >= target` → `high = mid - 1`，记录 `ans`
- 右边界：`nums[mid] <= target` → `low = mid + 1`，记录 `ans`
- 不存在时返回 `[-1, -1]`

---

#### Sqrt(x)（69）⭐⭐⭐⭐⭐
- 二分范围 `[0, x]`，找最大的 `mid` 满足 `mid² ≤ x`
- 注意 `mid * mid` 可能溢出 int，用 `long` 或 `mid > x / mid`

---

#### Search in Rotated Sorted Array（33）⭐⭐⭐⭐
- 先判断哪半有序：比较 `nums[mid]` 与 `nums[low]`
- 左半有序：`nums[low] <= nums[mid]`
  - target 在 `[low, mid)` 内 → `high = mid - 1`
  - 否则 → `low = mid + 1`
- 右半有序：`nums[mid] < nums[low]`
  - target 在 `(mid, high]` 内 → `low = mid + 1`
  - 否则 → `high = mid - 1`

---

#### Find Minimum in Rotated Sorted Array（153）⭐⭐⭐⭐
- 比较 `nums[mid]` 与 `nums[high]`
- `nums[mid] < nums[high]` → 最小值在左半，`high = mid`
- `nums[mid] > nums[high]` → 最小值在右半，`low = mid + 1`
- 循环条件 `low < high`，终止时 `low == high` 即为答案

---

#### Koko Eating Bananas（875）⭐⭐⭐
- 答案二分经典题：二分 `k`（每小时吃的速度）
- check 函数计算总时间 `∑⌈pile/k⌉`，判断是否 `≤ h`
- 二分范围 `[1, max(piles)]`

---

#### Single Element in a Sorted Array（540）⭐⭐
- 核心性质：单个元素出现之前，`nums[2k] == nums[2k+1]`；之后 `nums[2k] != nums[2k+1]`
- 二分检查 mid 的奇偶性
  - mid 为偶数：比较 `nums[mid]` 与 `nums[mid+1]`
  - mid 为奇数：比较 `nums[mid-1]` 与 `nums[mid]`
- 利用性质收缩区间，O(log n)

---

#### Median of Two Sorted Arrays（4）⭐⭐
- 核心：在两个数组上做**划分**，保证 `len(left_part) == len(right_part)` 且 `max(left) ≤ min(right)`
- 对较短的数组二分划分位置，在长数组上算出对应的划分位置
- 复杂度 O(log(min(m, n)))
- 注意奇偶统一处理：中位数 = `(maxLeft + minRight) / 2.0` 或 `minRight`

---

### 五、易错点总结

| 场景 | 易错点 | 正确做法 |
|------|--------|---------|
| 标准二分 | 忘记 `low <= high` | 记住最后要检查等号，否则单元素数组会漏查 |
| 左边界 | 找到后直接返回 | 应该继续往左压缩，记录 ans |
| 右边界 | 找到后直接返回 | 应该继续往右压缩，记录 ans |
| 旋转数组 | 用 target 比较 mid | 应该用 `nums[mid]` 与 `nums[high]` 比较确定哪半有序 |
| 数值二分 | `mid * mid` 溢出 | 用 `long` 或 `mid > x / mid` |
| 答案二分 | 二分范围没覆盖全 | 确保 left 和 right 包含所有合法答案 |
| 死循环 | `low = mid` 时忘记上取整 | 凡是出现 `low = mid` 的一定要 `mid = low + (high - low + 1) / 2` |
