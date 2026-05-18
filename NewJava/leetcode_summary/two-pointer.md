# Two Pointer (双指针)

> 核心性质：**双指针通过两个变量的协同移动来缩小解空间；与滑动窗口的区别在于不要求连续**

## 题目列表（按面试频次降序）

| 频次 | 题号 | 题目 | 难度 | 核心思路 | 状态 |
|:---:|:----:|------|:----:|---------|:----:|
| ★★★★★ | 11 | Container With Most Water | 🟡 Medium | 左右指针，每次移动短板 | ✅ TwoPointer.java |
| ★★★★★ | 15 | 3Sum | 🟡 Medium | 固定 i + 左右指针，注意去重三连 | ✅ TwoPointer.java |
| ★★★★★ | 121 | Best Time to Buy and Sell Stock | 🟢 Easy | 正向扫描，记录历史最低价 | ✅ TwoPointer.java |
| ★★★★★ | 42 | Trapping Rain Water | 🔴 Hard | 左右指针 + 左右最大高度 / 单调栈 | ✅ StackS.java |
| ★★★★ | 16 | 3Sum Closest | 🟡 Medium | 固定 i + 左右指针，唯一解无需去重 | ⬜ |
| ★★★★ | 122 | Best Time to Buy and Sell Stock II | 🟡 Medium | 贪心累加正差价 | ✅ TwoPointer.java |
| ★★★★ | 844 | Backspace String Compare | 🟢 Easy | 逆向扫描 + skip 计数 | ✅ TwoPointer.java |
| ★★★★ | 75 | Sort Colors | 🟡 Medium | 三指针（red/white/blue） | ⬜ |
| ★★★★ | 581 | Shortest Unsorted Continuous Subarray | 🟡 Medium | 正反指针找备选区间再扩展 | ⬜ |
| ★★★ | 134 | Gas Station | 🟡 Medium | 贪心剪枝，跳过中间不可能起点 | ⬜ |
| ★★★ | 455 | Assign Cookies | 🟢 Easy | 双数组排序 + 双指针 | ✅ TwoPointer.java |
| ★★★ | 123 | Best Time to Buy and Sell Stock III | 🔴 Hard | 双向分割：left[i] + right[i] | ⬜ |
| ★★ | 167 | Two Sum II - Input Array Is Sorted | 🟡 Medium | 左右指针，和大了动 high，小了动 low | ⬜ |
| ★★ | 26 | Remove Duplicates from Sorted Array | 🟢 Easy | 快慢指针，慢指针维护不重复区 | ⬜ |
| ★★ | 283 | Move Zeroes | 🟢 Easy | 快慢指针，非零元素前移 | ⬜ |
| ★ | 188 | Best Time to Buy and Sell Stock IV | 🔴 Hard | 三维 DP，面试一般不考 | ⬜ |

## 核心套路

```
左右指针 (Two Ends)     → i=0, j=n-1，向中间逼近，移动哪侧取决于比较 a[i] 和 a[j]
固定 i + 左右指针       → 排序后固定一个，另外两个左右逼近（3Sum 系列）
快慢指针 (同向)         → 快慢同时前进，慢指针维护符合条件的区间
正反指针 (Two Pass)     → 正向扫一遍 + 逆向扫一遍，组合结果（Stock III / 最短无序子数组）
逆向扫描               → 当正向需要栈/队列记住"未来"的信息时，逆向直接"看到未来"
双数组指针              → 各指一个已排序数组，按条件移动
三指针                 → 三种颜色的荷兰国旗问题

接雨水                → 左右指针 + 左右最大高度，哪侧低就处理哪侧
累加正差价            → 无限次交易：只累加 prices[i] > prices[i-1] 的差值
贪心剪枝              → Gas Station：从 i 到 k 失败，则 i~k 中间的都可跳过
双向分割              → 在 i 处将数组分成左右两段，分别求最优再组合
扩展法                → 先找备选区间，再根据 min/max 向外扩展边界
```

## 刷题路线

```
第一梯队（必会）：11 → 15 → 121 → 42 → 167
第二梯队（高频）：16 → 122 → 844 → 75 → 581
第三梯队（进阶）：134 → 455 → 123 → 26 → 283
第四梯队（扩展）：188
```

## 实现文件

- `algorithm/src/main/java/leetcode/TwoPointer.java` — 主实现
- `algorithm/src/test/java/leetcode/TwoPointerTest.java` — 测试
- `algorithm/src/main/java/leetcode/StackS.java` — Trapping Rain Water（单调栈版）

## 详细分析

### 1. Container With Most Water

| 项目 | 内容 |
|------|------|
| 题号 | [11. Container With Most Water](https://leetcode.com/problems/container-with-most-water/) |
| 模式 | 左右指针 |
| 实现 | `TwoPointer.maxArea()` — `TwoPointer.java:9` |

**思路：**

两个指针从最左和最右开始，逐步缩小宽度，每次只移动**短板**。

```
面积 = min(height[i], height[j]) * (j - i)
```

**为什么移动长板不行？**
- 宽度 `(j-i)` 减小
- 新高度 ≤ 原短板高度 → 面积不可能增大

**移动逻辑：**
- `height[i] < height[j]` → 短板在左，`i++`
- `height[i] > height[j]` → 短板在右，`j--`
- `height[i] == height[j]` → 两边都是短板，同时移动 `i++` `j--`

**优化点：** 移动短板时可持续移动直到遇到比当前短板更高的柱子，但代码整洁性会下降。

**代码：**
```java
public int maxArea(int[] height) {
    int i = 0, j = height.length - 1, max = 0;
    while (i < j) {
        int area = Math.min(height[i], height[j]) * (j - i);
        max = Math.max(max, area);
        if (height[i] == height[j]) { i++; j--; }
        else if (height[i] < height[j]) i++;
        else j--;
    }
    return max;
}
```

---

### 2. 3Sum

| 项目 | 内容 |
|------|------|
| 题号 | [15. 3Sum](https://leetcode.com/problems/3sum/) |
| 模式 | 固定 i + 左右指针 |
| 实现 | `TwoPointer.threeSum()` — `TwoPointer.java:39` |

**思路：**

1. 先排序
2. 固定 `i`，`low = i + 1`，`high = n - 1`
3. 移动 `low` / `high` 寻找 `nums[i] + nums[low] + nums[high] == 0`

**为什么不能用二分查找固定首尾再中间二分？**
例如 `[1, 20, 30, 60, 100]` 找 150，第一轮没找到时不知道该移动 low 还是 high，都有可能获取解。

**去重三连（易错点）：**
```java
// 1. i 去重
if (i > 0 && nums[i] == nums[i - 1]) continue;

// 2. j 去重
while (j < k && nums[j] == nums[j + 1]) j++;

// 3. k 去重
while (j < k && nums[k] == nums[k - 1]) k--;
```

---

### 3. Best Time to Buy and Sell Stock I

| 项目 | 内容 |
|------|------|
| 题号 | [121. Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/) |
| 模式 | 正向扫描 + 记录最小值 |
| 实现 | `TwoPointer.maxProfit()` — `TwoPointer.java:122` |

**思路：**

数学描述：找到 `i, j` 使得 `j > i` 且 `prices[j] - prices[i]` 是最大值。

正向扫描，记录历史最低价格，每天计算如果当天卖出能赚多少，不断更新最大利润。

```java
public int maxProfit(int[] prices) {
    int minPrice = Integer.MAX_VALUE, maxProfit = 0;
    for (int price : prices) {
        if (price < minPrice) minPrice = price;
        else maxProfit = Math.max(maxProfit, price - minPrice);
    }
    return maxProfit;
}
```

**注意：** 不需要逆向扫描 + 辅助数组，正向记录最小值即可。

---

### 4. Trapping Rain Water

| 项目 | 内容 |
|------|------|
| 题号 | [42. Trapping Rain Water](https://leetcode.com/problems/trapping-rain-water/) |
| 模式 | 左右指针 + 左右最大高度 |
| 实现 | `StackS.trap()`（单调栈版） |

**双指针解法：**

与 Container With Most Water 类似，左右指针 + 维护左右最大高度。

```java
int left = 0, right = n - 1, leftMax = 0, rightMax = 0, ans = 0;
while (left < right) {
    if (height[left] < height[right]) {
        if (height[left] >= leftMax) leftMax = height[left];
        else ans += leftMax - height[left];
        left++;
    } else {
        if (height[right] >= rightMax) rightMax = height[right];
        else ans += rightMax - height[right];
        right--;
    }
}
```

**核心直觉：** 接水量由"较矮的那侧的最高柱子"决定。当 `height[left] < height[right]` 时，`left` 位置的接水量仅取决于左侧最大值（因为右侧有更高的 `right` 兜底）。

---

### 5. Best Time to Buy and Sell Stock II

| 项目 | 内容 |
|------|------|
| 题号 | [122. Best Time to Buy and Sell Stock II](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/) |
| 模式 | 贪心 / 累加正差价 |
| 实现 | `TwoPointer.maxProfitII()` — `TwoPointer.java:178` |

**思路：**

累加所有正差价，因为无限次交易可以把一次长持有拆成多段短持有，总和不变。

```java
public int maxProfitII(int[] prices) {
    int profit = 0;
    for (int i = 1; i < prices.length; i++) {
        if (prices[i] > prices[i - 1])
            profit += prices[i] - prices[i - 1];
    }
    return profit;
}
```

**为什么在局部峰卖出不会错过后面的更高峰？**

| 场景 | 示例 | 分段交易 | 持有不动 | 结论 |
|:---|:----:|:--------:|:--------:|:----|
| 单调上升 | [1,3,5] | (3-1)+(5-3)=4 | 5-1=4 | 等价 |
| 有回撤 | [1,3,2,5] | (3-1)+(5-2)=5 | 5-1=4 | 分段更优 |

累加正差价本质上是把所有上升段都吃到，下跌段跳过。

---

### 6. Backspace String Compare

| 项目 | 内容 |
|------|------|
| 题号 | [844. Backspace String Compare](https://leetcode.com/problems/backspace-string-compare/) |
| 模式 | 逆向扫描 |
| 实现 | `TwoPointer.backspaceCompare()` — `TwoPointer.java:94` |

**思路：**

正向扫描需要处理 `#` 的退格逻辑，需要记住前面的字符 → 需要栈或类似结构。
逆向扫描时，`#` 代表**跳过**下一个有效字符，天然适合从后往前处理。

```java
private String simplify(String s) {
    int i = s.length() - 1, skip = 0;
    StringBuilder sb = new StringBuilder();
    while (i >= 0) {
        if (s.charAt(i) == '#') skip++;
        else if (skip > 0) skip--;
        else sb.append(s.charAt(i));
        i--;
    }
    return sb.reverse().toString();
}
```

**判断模式的标准：** 如果正向扫描时需要维护额外的栈或队列来记住未来的信息，那么逆向扫描可能直接让你"看到未来"。

---

### 7. Sort Colors

| 项目 | 内容 |
|------|------|
| 题号 | [75. Sort Colors](https://leetcode.com/problems/sort-colors/) |
| 模式 | 三指针（荷兰国旗） |

**解法一：基数排序**
计数三个颜色出现的次数，然后回填。O(2n)。

**解法二：one-pass 三指针**
```java
int red = 0, blue = nums.length - 1, i = 0;
while (i <= blue) {
    if (nums[i] == 0)      swap(nums, red++, i++);
    else if (nums[i] == 1) i++;
    else                   swap(nums, blue--, i); // 换来的可能是 0 或 1，不移动 i
}
```

**扩展：** 如果有 k 种元素，需要 k 个临时指针变量，本质上与基数排序无异。

---

### 8. Shortest Unsorted Continuous Subarray

| 项目 | 内容 |
|------|------|
| 题号 | [581. Shortest Unsorted Continuous Subarray](https://leetcode.com/problems/shortest-unsorted-continuous-subarray/) |
| 模式 | 正反指针 + 边界扩展 |

**思路：**

1. **找备选区间：** 从前往后扫描找到第一个逆序点作为 `s`，从后往前扫描找到第一个逆序点作为 `e`
2. **扩展备选区间：**
   - 扫描备选区间的 min 和 max
   - 向左扩展：`nums[0..s-1]` 中有值 > min → `s` 左移
   - 向右扩展：`nums[e+1..n-1]` 中有值 < max → `e` 右移
3. 最终 `[s, e]` 即为答案

**错误思路提醒：** 从两边开始扫描，发现逆序就停止。但 `arr[i] < arr[j]` 时不知道该移动 i 还是 j，类似 3Sum Closest。

---

### 9. Gas Station

| 项目 | 内容 |
|------|------|
| 题号 | [134. Gas Station](https://leetcode.com/problems/gas-station/) |
| 模式 | 贪心剪枝 |

**暴力法：** 选定起点，顺时针尝试完成全程。O(n²)。

**优化（关键剪枝）：**
从点 i 出发，到 k 发现走不下去。此时 `i` 到 `k` 之间的任意点**都不需要尝试**。

反证法：假设存在 `j ∈ (i, k)` 可以从 `j` 走到 `k`。由上一轮已知 `i` 可以走到 `j`。那么 `i` 可以 `i→j→k`，与"`i` 到 `k` 走不下去"矛盾。

所以直接跳过 `i~k` 的所有中间节点，从 `k+1` 开始尝试。

**循环 trick：** `index = index % size`

---

### 10. Assign Cookies

| 项目 | 内容 |
|------|------|
| 题号 | [455. Assign Cookies](https://leetcode.com/problems/assign-cookies/) |
| 模式 | 双数组指针 |
| 实现 | `TwoPointer.findContentChildren()` — `TwoPointer.java:75` |

**思路：**

两个数组各排一次序，两个指针分别扫描。属于双数组指针的标准模板，没有复杂技巧。

```java
public int findContentChildren(int[] children, int[] cookies) {
    Arrays.sort(children);
    Arrays.sort(cookies);
    int i = 0, j = 0;
    while (i < children.length && j < cookies.length) {
        if (cookies[j] >= children[i]) i++;
        j++;
    }
    return i;
}
```

---

### 11. Best Time to Buy and Sell Stock III

| 项目 | 内容 |
|------|------|
| 题号 | [123. Best Time to Buy and Sell Stock III](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/) |
| 模式 | 正反指针 + 双向分割 |

**思路：**

最多出手 2 次。两次交易总会有一个**中间分割点**，把数组切成左右两段，每段求一次 Stock I 的最大利润。

```java
left[i]  = prices[0..i] 范围内一次交易的最大利润
right[i] = prices[i..n-1] 范围内一次交易的最大利润
ans = max(left[i] + right[i])  for i in [0, n-1]
```

之所以需要辅助数组（left, right），是因为每个位置都需要知道两侧的最佳交易值，无法单次扫描同时获得。

---

### 12. Two Sum II - Input Array Is Sorted

| 项目 | 内容 |
|------|------|
| 题号 | [167. Two Sum II - Input Array Is Sorted](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/) |
| 模式 | 左右指针 |

**思路：**

已排序数组，左右指针。`sum < target → low++`，`sum > target → high--`。

### 13. Remove Duplicates from Sorted Array

| 项目 | 内容 |
|------|------|
| 题号 | [26. Remove Duplicates from Sorted Array](https://leetcode.com/problems/remove-duplicates-from-sorted-array/) |
| 模式 | 快慢指针 |

**思路：**

慢指针 `i` 维护不重复区的末尾，快指针 `j` 扫描。遇到新元素就放到 `i+1`。

```java
int i = 0;
for (int j = 1; j < nums.length; j++) {
    if (nums[j] != nums[i]) nums[++i] = nums[j];
}
return i + 1;
```

### 14. Move Zeroes

| 项目 | 内容 |
|------|------|
| 题号 | [283. Move Zeroes](https://leetcode.com/problems/move-zeroes/) |
| 模式 | 快慢指针 |

**思路：**

慢指针指向当前非零元素应放置的位置，快指针扫描。非零元素前移，最后末尾补零。

```java
int i = 0;
for (int j = 0; j < nums.length; j++) {
    if (nums[j] != 0) nums[i++] = nums[j];
}
while (i < nums.length) nums[i++] = 0;
```
