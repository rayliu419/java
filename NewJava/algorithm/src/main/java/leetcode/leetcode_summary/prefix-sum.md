# Prefix Sum (前缀和)

> 核心性质：**连续子数组/子串求和问题 → 优先考虑前缀和或滑动窗口。有负数时滑动窗口失效，前缀和+HashMap 是通用解法。** 前缀和能在 O(1) 时间内求出任意子数组的和，空间换时间。

## 题目列表（按面试频次降序）

| 频次 | 题号 | 题目 | 难度 | 核心思路 | 状态 |
|:---:|:----:|------|:----:|---------|:----:|
| ★★★★★ | 560 | Subarray Sum Equals K | 🟡 Medium | 前缀和+HashMap（存频率），`ans += map.getOrDefault(cur - k, 0)` | ⬜ |
| ★★★★★ | 238 | Product of Array Except Self | 🟡 Medium | 前缀积：左→右扫一遍，右→左扫一遍 | ⬜ |
| ★★★★★ | 53 | Maximum Subarray | 🟡 Medium | Kadane 算法：`cur = max(cur + nums[i], nums[i])` | ⬜ |
| ★★★★ | 303 | Range Sum Query - Immutable | 🟢 Easy | 一维前缀和裸题，`prefix[i] = prefix[i-1] + nums[i-1]` | ⬜ |
| ★★★★ | 304 | Range Sum Query 2D - Immutable | 🟡 Medium | 二维前缀和，`sum = s[x2][y2] - s[x1-1][y2] - s[x2][y1-1] + s[x1-1][y1-1]` | ⬜ |
| ★★★★ | 724 | Find Pivot Index | 🟢 Easy | 中心下标，左和 = 右和，`leftSum == total - leftSum - nums[i]` | ⬜ |
| ★★★★ | 525 | Contiguous Array | 🟡 Medium | 0→-1，前缀和+HashMap（存最早索引），求最长 | ⬜ |
| ★★★★ | 974 | Subarray Sums Divisible by K | 🟡 Medium | 前缀和取模，`ans += map.getOrDefault((cur % k + k) % k, 0)` | ⬜ |
| ★★★ | 523 | Continuous Subarray Sum | 🟡 Medium | 前缀和取模+存索引，要求长度≥2 | ⬜ |
| ★★★ | 930 | Binary Subarrays With Sum | 🟡 Medium | 560 的 binary 特化版（元素只有 0/1） | ⬜ |
| ★★★ | 1248 | Count Number of Nice Subarrays | 🟡 Medium | 奇→1, 偶→0，转化为 560 | ⬜ |
| ★★★ | 1310 | XOR Queries of a Subarray | 🟡 Medium | 前缀异或，`xor[i..j] = pref[j+1] ^ pref[i]` | ⬜ |
| ★★★ | 325 | Maximum Size Subarray Sum Equals k | 🟡 Medium | 前缀和+HashMap（存最早索引），`O(n)` | ⬜ |
| ★★★ | 1094 | Car Pooling | 🟡 Medium | 差分数组，`diff[l] += val; diff[r] -= val` | ⬜ |
| ★★★ | 370 | Range Addition | 🟡 Medium | 差分数组模板题 | ⬜ |
| ★★ | 1480 | Running Sum of 1d Array | 🟢 Easy | 基础前缀和，入门级 | ⬜ |
| ★★ | 1732 | Find the Highest Altitude | 🟢 Easy | 前缀和最大值 | ⬜ |
| ★★ | 1314 | Matrix Block Sum | 🟡 Medium | 二维前缀和应用，注意边界处理 | ⬜ |
| ★★ | 1442 | Count Triplets That Form Two Equal XOR | 🟡 Medium | 前缀异或，`arr[i..j-1] XOR == 0` 时成立 | ⬜ |
| ★★ | 1590 | Make Sum Divisible by P | 🟡 Medium | 取模+HashMap，找最短移除子数组 | ⬜ |
| ★ | 1074 | Number of Submatrices That Sum to Target | 🔴 Hard | 二维+HashMap，枚举行上下界降维成一维 | ⬜ |
| ★ | 1371 | Find Longest Substring with Vowels in Even Counts | 🟡 Medium | 状态压缩(5位bit)+前缀异或 | ⬜ |
| ★ | 1542 | Find Longest Awesome Substring | 🔴 Hard | 状态压缩(10位bit)+前缀异或 | ⬜ |

## 核心套路

```
区间和查询（数组不变）        → 一维前缀和： prefix[i] = prefix[i-1] + nums[i-1]
矩阵区域和查询               → 二维前缀和： s[x][y] = s[x-1][y] + s[x][y-1] - s[x-1][y-1] + matrix[x][y]
子数组和 = k（有负数）        → 前缀和+HashMap： map.put(0,1); ans += map.getOrDefault(cur - k, 0)
子数组和 % k == 0            → 前缀和取模： ans += map.getOrDefault((cur % k + k) % k, 0)
子数组长度 ≥ 2 && 和%k==0     → 取模存索引，要求 idx - map.get(mod) ≥ 2
0/1 数组等值转化             → 0→-1，求前缀和，转化为和为 0 的最长子数组
区间更新，少查询              → 差分数组： diff[l] += val; diff[r+1] -= val
前缀积                      → 左→右算 prefix，右→左算 suffix，相乘排除自身
前缀异或                    → pref[i+1] = pref[i] ^ nums[i]; 区间异或 = pref[j+1] ^ pref[i]
最大子数组和                 → Kadane： cur = max(cur + nums[i], nums[i]); ans = max(ans, cur)
状态压缩+前缀异或            → 用 bit 表示状态（奇偶），最早出现同状态的距离即为最长

和为 k 的子数组模板：
  freq = {0: 1}
  cur = ans = 0
  for num in nums:
      cur += num
      ans += freq.get(cur - k, 0)
      freq[cur] = freq.get(cur, 0) + 1
```

## 快速判题指南

| 场景 | 用哪个 |
|------|--------|
| 有负数，求子数组和 = k 的**个数** | 前缀和+HashMap（存频率） |
| 有负数，求子数组和 = k 的**最长长度** | 前缀和+HashMap（存最早索引） |
| 只有正数，求子数组和 = k | 滑动窗口（O(n) 无额外空间） |
| 子数组和能被 k 整除 | 前缀和取模 |
| 0/1 数组求 0和1 数量相等 | 0→-1 转化为和为 0 |
| 矩阵子矩阵和 = target | 二维前缀和 或 枚举行+一维降维 |
| 区间内奇偶元素计数 | 前缀和（奇→1，偶→0 或状态压缩） |
| 区间多次求和查询 | 裸前缀和 （303, 304） |
| 区间多次更新，很少查询 | 差分数组（1094, 370） |
| 从 i 到 j 的异或值 | 前缀异或 |
| 排除自身外的乘积 | 前缀积（左右两次遍历） |
