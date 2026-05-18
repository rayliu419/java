# Matrix (矩阵)

> 核心性质：**二维基本功 + 边界处理 + 坐标变换**

## 题目列表（按面试频次降序）

| 频次 | 题号 | 题目 | 难度 | 核心思路 | 状态 |
|:---:|:----:|------|:----:|---------|:----:|
| ⭐⭐⭐⭐⭐ | 48 | Rotate Image | 🟡 Medium | 方阵旋转：转置 + 每行反转（逆时针则每列反转） | ✅ MatrixVisit.java |
| ⭐⭐⭐⭐⭐ | 73 | Set Matrix Zeroes | 🟡 Medium | 用第一行/列做标记，注意标记区污染问题 | ✅ MatrixVisit.java |
| ⭐⭐⭐⭐⭐ | 54 | Spiral Matrix | 🟡 Medium | 四边界收缩遍历，每轮后检查边界是否越界 | ✅ MatrixVisit.java |
| ⭐⭐⭐⭐ | 240 | Search a 2D Matrix II | 🟡 Medium | 从右上/左下开始，根据大小关系缩小搜索范围 | ⬜ |
| ⭐⭐⭐⭐ | 74 | Search a 2D Matrix | 🟡 Medium | 二分查找（行有序+行内有序） | ⬜ |
| ⭐⭐⭐⭐ | 59 | Spiral Matrix II | 🟡 Medium | 同 Spiral Matrix，生成式填充 | ⬜ |
| ⭐⭐⭐ | 867 | Transpose Matrix | 🟢 Easy | 非方阵 new 新矩阵，方阵可原地转置（j = i + 1） | ✅ MatrixVisit.java |
| ⭐⭐⭐ | 36 | Valid Sudoku | 🟡 Medium | 行列/九宫格用 HashSet 或数组判重 | ⬜ |
| ⭐⭐⭐ | 79 | Word Search | 🟡 Medium | 回溯 + visited 标记 | ⬜ |
| ⭐⭐⭐ | 378 | Kth Smallest Element in a Sorted Matrix | 🟡 Medium | 行/列有序 → 二分查找/优先队列 | ⬜ |
| ⭐⭐ | 48 | Rotate Image (already covered) | 同上 | |
| ⭐⭐ | 498 | Diagonal Traverse | 🟡 Medium | 方向交替 + 边界反转 | ⬜ |
| ⭐⭐ | 329 | Longest Increasing Path in a Matrix | 🔴 Hard | DFS + 记忆化搜索 | ✅ BFS.java |
| ⭐⭐ | 200 | Number of Islands | 🟡 Medium | DFS/BFS 遍历 | ⬜ |
| ⭐⭐ | 54 | Spiral Matrix (already covered) | 同上 | |

## 核心套路

```
旋转 90°           → 转置 + 每行反转（顺时针）/ 每列反转（逆时针）
螺旋遍历/生成       → 四边界收缩，每轮后检查 rowStart>rowEnd / colStart>colEnd
置零               → 第一行/列标记，额外标记位处理第0行/列污染
矩阵二分搜索        → 从右上/左下出发逐步缩小(Z字形) / 二维二分
矩阵 BFS/DFS       → 方向数组 + visited + 边界检查
矩阵转置            → 非方阵 new 新矩阵 / 方阵原地 j=i+1 交换
对角遍历           → 方向交替，碰边界反转
单词搜索           → 回溯 + visited
行/列有序找第K小   → 二分查找或优先队列
```

### 刷题路线

```
第一梯队（必会）：48 → 73 → 54 → 240 → 74
第二梯队（高频）：59 → 867 → 36 → 79 → 378
第三梯队（进阶）：498 → 329 → 200
```

## 实现文件

- `algorithm/src/main/java/leetcode/MatrixVisit.java` — 主实现
- `algorithm/src/test/java/leetcode/MatrixVisitTest.java` — 测试

---

## 我的笔记

### 概览

矩阵访问之类，其实就是练二维操作的基本功。

- **基本操作题型**：遍历、访问。
- **转置**。
- **行有序、列有序**相关。
- **性质**：左上角最小，右下角最大。
- **从四个角着手**。

### 矩阵访问（螺旋遍历）

旋转访问，主要是循环内的行列起始和终止位置的处理。

### 矩阵转置

- 如果不是方阵，不能原地转置，必须 new 一个新的。
- 方阵转置和普通矩阵转置的区别写法：方阵 `j = i + 1` 只遍历上三角。

### 矩阵旋转

- **顺时针 90°** = 转置 + 每行反转
- **逆时针 90°** = 转置 + 每列反转

### Set Matrix Zeroes

- 空间的优化：使用第一行/列来存储标记信息。
- **标记位被影响的可能性**：遍历标记清零时必须从 `i=1 / j=1` 开始，否则 `matrix[0][0]` 为0时会提前将整列清0，导致标记区被破坏。第0行/列通过额外 boolean 标志在最后单独处理。
