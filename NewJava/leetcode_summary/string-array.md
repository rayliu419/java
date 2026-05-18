# String & Array（字符串与数组基本操作）

> 核心性质：**边界条件处理 + 位置映射 + 原地操作**

## 题目列表（按面试频次降序）

| 频次 | 题号 | 题目 | 难度 | 核心思路 | 状态 |
|:---:|:----:|------|:----:|---------|:----:|
| ⭐⭐⭐⭐⭐ | 151 | Reverse Words in a String | 🟡 Medium | 去多余空格 → 整体反转 → 逐单词反转 | ✅ ArrayStringOperation.java |
| ⭐⭐⭐⭐⭐ | 189 | Rotate Array | 🟡 Medium | 三次翻转：整体 → 前 k → 后 n-k | ✅ ArrayStringOperation.java |
| ⭐⭐⭐⭐⭐ | 415 | Add Strings | 🟢 Easy | 双指针 + carry 模拟，循环条件带 `\|\| carry > 0` | ✅ ArrayStringOperation.java |
| ⭐⭐⭐⭐⭐ | 43 | Multiply Strings | 🟡 Medium | 竖式乘法，结果数组 len = m+n，从后往前累加 | ⬜ |
| ⭐⭐⭐⭐⭐ | 344 | Reverse String | 🟢 Easy | 双指针交换 | ⬜ |
| ⭐⭐⭐⭐⭐ | 125 | Valid Palindrome | 🟢 Easy | 双指针 + Character.isLetterOrDigit | ⬜ |
| ⭐⭐⭐⭐⭐ | 20 | Valid Parentheses | 🟢 Easy | 栈匹配 | ⬜ |
| ⭐⭐⭐⭐⭐ | 268 | Missing Number | 🟢 Easy | 求和 / 异或 | ⬜ |
| ⭐⭐⭐⭐⭐ | 41 | First Missing Positive | 🔴 Hard | 原地置换：将数字放到正确位置 | ⬜ |
| ⭐⭐⭐⭐⭐ | 5 | Longest Palindromic Substring | 🟡 Medium | 中心扩展 / DP | ⬜ |
| ⭐⭐⭐⭐ | 448 | Find All Numbers Disappeared | 🟢 Easy | 原地标记：下标对应值 | ⬜ |
| ⭐⭐⭐⭐ | 287 | Find the Duplicate Number | 🟡 Medium | 快慢指针（看作有环链表） | ⬜ |
| ⭐⭐⭐⭐ | 442 | Find All Duplicates in Array | 🟡 Medium | 原地标记：值→下标取反 | ⬜ |
| ⭐⭐⭐⭐ | 242 | Valid Anagram | 🟢 Easy | 字符计数数组 | ⬜ |
| ⭐⭐⭐⭐ | 14 | Longest Common Prefix | 🟢 Easy | 纵向比较所有字符串 | ⬜ |
| ⭐⭐⭐⭐ | 28 | Find the Index of First Occurrence | 🟢 Easy | KMP / 暴力 | ⬜ |
| ⭐⭐⭐⭐ | 3 | Longest Substring Without Repeating | 🟡 Medium | 滑动窗口 + 哈希 | ⬜ |
| ⭐⭐⭐⭐ | 49 | Group Anagrams | 🟡 Medium | 排序+哈希 / 计数+哈希 | ⬜ |
| ⭐⭐⭐⭐ | 67 | Add Binary | 🟢 Easy | 同字符串相加，二进制 carry=sum/2 | ⬜ |
| ⭐⭐⭐⭐ | 8 | String to Integer (atoi) | 🟡 Medium | 去空格 → 判正负 → 溢出处理 | ⬜ |
| ⭐⭐⭐ | 186 | Reverse Words in a String II | 🟡 Medium | 整体反转 + 逐单词反转（原地，无多余空格） | ⬜ |
| ⭐⭐⭐ | 13 | Roman to Integer | 🟢 Easy | 哈希 + 逆序遍历减法 | ⬜ |
| ⭐⭐⭐ | 217 | Contains Duplicate | 🟢 Easy | 哈希 / 排序 | ⬜ |
| ⭐⭐⭐ | 136 | Single Number | 🟢 Easy | 异或 | ⬜ |
| ⭐⭐⭐ | 345 | Reverse Vowels of a String | 🟢 Easy | 双指针 + 元音判断 | ⬜ |
| ⭐⭐⭐ | 392 | Is Subsequence | 🟢 Easy | 双指针扫描 | ⬜ |
| ⭐⭐⭐ | 6 | Zigzag Conversion | 🟡 Medium | 行模拟 / flag 方向控制 | ⬜ |
| ⭐⭐⭐ | 38 | Count and Say | 🟡 Medium | 递归生成，双指针统计连续相同 | ⬜ |
| ⭐⭐⭐ | 647 | Palindromic Substrings | 🟡 Medium | 中心扩展法 | ⬜ |
| ⭐⭐ | 12 | Integer to Roman | 🟡 Medium | 贪心匹配 | ⬜ |
| ⭐⭐ | 168 | Excel Sheet Column Title | 🟢 Easy | 26 进制（注意从 1 开始） | ⬜ |
| ⭐⭐ | 171 | Excel Sheet Column Number | 🟢 Easy | 26 进制转十进制 | ⬜ |
| ⭐⭐ | 76 | Minimum Window Substring | 🔴 Hard | 滑动窗口 + 计数数组 | ⬜ |
| ⭐⭐ | 68 | Text Justification | 🔴 Hard | 贪心分配空格 | ⬜ |

## 核心套路

```
反转单词         → 去多余空格 + 整体反转 + 逐单词反转
字符串相加/乘     → 双指针 + carry 模拟竖式
旋转数组         → 三次翻转（整体→前k→后n-k）
回文串           → 中心扩展 / 双指针
缺失/重复数字    → 原地置换 / 异或 / 求和
有效括号         → 栈匹配
最长公共前缀      → 纵向比较
滑动窗口         → 双指针维护窗口 + 哈希/计数数组
异位词           → 排序后比较 / 字符计数
atoi            → 去空格 → 判正负 → 逐位累加 → 溢出处理
atoi 溢出处理    → result = result * 10 + digit, 用 long 或提前判断 > MAX/10
```

### 刷题路线

```
第一梯队（必会）：151 → 189 → 415 → 43 → 344 → 125 → 20 → 268 → 41
第二梯队（高频）：448 → 287 → 442 → 242 → 14 → 28 → 3 → 49 → 67 → 8
第三梯队（进阶）：186 → 13 → 217 → 136 → 345 → 392 → 647 → 76
```

## 实现文件

- `algorithm/src/main/java/leetcode/ArrayStringOperation.java` — 主实现
- `algorithm/src/test/java/leetcode/ArrayStringOperationTest.java` — 测试

---

## 我的笔记

### 概览

数组或者字符串的基本操作。

- String 主要是各种边界情况的处理或者位置的处理。
- 数组包含给定范围内的数字，查找丢失或重复的数字。这里的通常要求是只能是 O(1) 空间。将数字放到它应该放到的位置，然后继续。难度不高，但是有点偏门。

### String 原地删除多余空格

有一个很经典简洁的算法（参考 git repo）。

这是基础操作。

边界覆盖：空串 `""` → `""`、纯空格 `"   "` → `""`、前导/尾随空格、单词间多空格、混合等情况。

核心实现：外层循环用 `fast` 过滤空格，内层 `while` 拷贝实际单词。`slow != 0` 时先加一个空格分隔。

```java
for (int fast = 0; fast < s.length(); fast++) {
    if (chars[fast] != ' ') {
        if (slow != 0) chars[slow++] = ' ';
        while (fast < s.length() && chars[fast] != ' ')
            chars[slow++] = chars[fast++];
    }
}
```

### Reverse Word

上一题 `removeSpace` 作为基本操作。流程：

1. `removeSpace(s)` — 去除多余空格
2. `reverse(chars, 0, len-1)` — 整体反转
3. 逐单词 `reverse(chars, slow, fast-1)` — 恢复单词顺序

### 字符串相加

主要是循环条件需要写得好：

```java
// 如果有某个 String 没处理完或者有进位。
while (i >= 0 || j >= 0 || carry > 0)
```

### 字符串相乘

竖式乘法：`m` 位 × `n` 位结果最多 `m+n` 位，用数组存储，双层循环从后往前累加。

### 旋转数组

`rotate([1,2,3,4,5,6,7], k=3)` → `[5,6,7,1,2,3,4]`

- `k > n` 时：`k = k % n`
- 三次翻转，O(n) 时间，O(1) 空间：

```
① 整体翻转: [1,2,3,4,5,6,7] → [7,6,5,4,3,2,1]
② 翻转前 k: [7,6,5,4,3,2,1] → [5,6,7,4,3,2,1]
③ 翻转后 n-k: [5,6,7,4,3,2,1] → [5,6,7,1,2,3,4] ✓
```

### 查找丢失或重复的数字

**题型特点**：数组包含 `[1, n]` 范围内的数字，查找丢失或重复，要求 O(1) 空间。

**核心思路**：将数字放到它应该放到的位置（`nums[i]` 应该放到 `nums[nums[i]-1]`），然后遍历检查。

- **Missing Number**（268）：`[0, n]` 中缺失的数 → 求和 / 异或
- **First Missing Positive**（41）：未排序数组找缺失的最小正整数 → 原地置换（只处理 `[1, n]` 范围的数）
- **Find All Numbers Disappeared in an Array**（448）：`[1, n]` 中缺失的数 → 原地标记（出现过的下标对应值取负）
- **Find the Duplicate Number**（287）：`[1, n]` 中重复的数 → 快慢指针判环入口
- **Find All Duplicates in an Array**（442）：`[1, n]` 中出现两次的数 → 原地标记（值→下标取反，已负则说明重复）
