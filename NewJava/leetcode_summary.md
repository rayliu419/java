# LeetCode Summary

> 分类记录面试高频题型，包含题目信息、核心思路和实现状态。

---

## BST (Binary Search Tree)

> 核心性质：**中序遍历 = 升序序列**

| 题号 | 题目 | 难度 | 核心思路 | 状态 |
|------|------|------|---------|------|
| 98 | Validate Binary Search Tree | Medium | ① 中序遍历检查递增 ② 区间约束 (min, max) | ✅ |
| 230 | Kth Smallest Element in a BST | Medium | 中序遍历到第 K 个即停止 | ⬜ |
| 235 | Lowest Common Ancestor of a BST | Medium | 利用大小关系剪枝：p < root < q → root 就是 LCA | ✅ Tree.java |
| 700 | Search in a BST | Easy | 递归/迭代比较 val 决定左右方向 | ⬜ |
| 701 | Insert into a BST | Medium | 搜索到空位插入 | ⬜ |
| 450 | Delete Node in a BST | Medium | 三种情况：叶子 / 单子 / 双子（前驱/后继替换） | ⬜ |
| 108 | Convert Sorted Array to BST | Easy | 二分递归构建 | ⬜ |
| 538 | Convert BST to Greater Tree | Medium | 逆中序遍历（右→根→左）累加 | ⬜ |
| 173 | BST Iterator | Medium | 栈模拟非递归中序遍历 | ⬜ |
| 99 | Recover Binary Search Tree | Medium | 中序遍历找两个错位节点 | ⬜ |
| 669 | Trim a BST | Medium | 区间修剪递归：val<low 剪左，val>high 剪右 | ⬜ |
| 501 | Find Mode in BST | Easy | 中序遍历统计连续出现次数 | ⬜ |
| 530 | Min Absolute Difference in BST | Easy | 中序遍历相邻差值取最小 | ⬜ |
| 96 | Unique BST | Medium | DP / 卡特兰数 | ⬜ |
| 95 | Unique BST II | Medium | 递归构造所有可能的 BST | ⬜ |
| 938 | Range Sum of BST | Easy | DFS + 根据 val 范围剪枝 | ⬜ |

### 核心套路

```
验证 BST    → 中序递增 / 区间约束
第 K 小     → 中序遍历到第 K 个
搜索/插入   → 比较大小决定方向
删除        → 找到节点 → 三种情况处理
构建 BST    → 有序数组二分递归 / 链表快慢指针
累加树      → 逆中序遍历累加
修剪        → 区间递归裁剪
```

### 实现文件

- `algorithm/src/main/java/leetcode/BST.java` — 主实现
- `algorithm/src/test/java/leetcode/BSTTest.java` — 测试

---

> 更多分类待添加...
