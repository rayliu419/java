# Linked List

> 核心性质：**链表指针操作 = 画图 + 边界检查 + 尾部置空**

## 题目列表（按面试频次降序）

| 频次 | 题号 | 题目 | 难度 | 核心思路 | 状态 |
|:---:|:----:|------|:----:|---------|:----:|
| ★★★★★ | 206 | Reverse Linked List | 🟢 Easy | pre/cur/next 三指针反转 | ✅ LinkList.java |
| ★★★★★ | 141 | Linked List Cycle | 🟢 Easy | 快慢指针相遇判环 | ✅ LinkList.java |
| ★★★★★ | 21 | Merge Two Sorted Lists | 🟢 Easy | dummy + 双指针合并 | ✅ LinkList.java |
| ★★★★★ | 876 | Middle of the Linked List | 🟢 Easy | 快慢指针找中点 | ✅ LinkList.java |
| ★★★★★ | 19 | Remove Nth Node From End | 🟡 Medium | 快慢指针，fast 先走 n 步 | ⬜ |
| ★★★★ | 142 | Linked List Cycle II | 🟡 Medium | 相遇后 slow 从头，fast 从相遇点，再遇即入口 | ✅ LinkList.java |
| ★★★★ | 160 | Intersection of Two Linked Lists | 🟢 Easy | 长度差 / 双指针遍历到相交 | ⬜ |
| ★★★★ | 143 | Reorder List | 🟡 Medium | 找中点 → 反转 → 合并 | ⬜ |
| ★★★★ | 234 | Palindrome Linked List | 🟢 Easy | 找中点 → 反转后半 → 比较 | ⬜ |
| ★★★★ | 2 | Add Two Numbers | 🟡 Medium | dummy + 模拟加法进位 | ⬜ |
| ★★★★ | 82 | Remove Duplicates from Sorted List II | 🟡 Medium | pre 指针判断重复段，不保留任何重复 | ✅ LinkList.java |
| ★★★★ | 148 | Sort List | 🟡 Medium | 归并排序：找中点 → 递归 → 合并 | ⬜ |
| ★★★★ | 138 | Copy List with Random Pointer | 🟡 Medium | HashMap 映射 / 原地三次遍历 | ⬜ |
| ★★★ | 83 | Remove Duplicates from Sorted List | 🟢 Easy | 重复保留一个，直接跳过 | ✅ LinkList.java |
| ★★★ | 203 | Remove Linked List Elements | 🟢 Easy | dummy 节点，注意尾部置空 | ✅ LinkList.java |
| ★★★ | 328 | Odd Even Linked List | 🟡 Medium | 奇偶指针分离再连接 | ⬜ |
| ★★★ | 61 | Rotate List | 🟡 Medium | 先成环再找新头断开 | ⬜ |
| ★★★ | 109 | Convert Sorted List to BST | 🟡 Medium | 找中点递归构建，注意尾部置空 | ⬜ |
| ★★★ | 92 | Reverse Linked List II | 🟡 Medium | 区间反转，四指针定位 | ⬜ |
| ★★★ | 24 | Swap Nodes in Pairs | 🟡 Medium | dummy + 两两交换指针操作 | ⬜ |
| ★★★ | 146 | LRU Cache | 🟡 Medium | HashMap + 双向链表 | ⬜ |
| ★★ | 86 | Partition List | 🟡 Medium | 大小两个 dummy 链表再合并 | ⬜ |
| ★★ | 147 | Insertion Sort List | 🟡 Medium | dummy + 逐个插入 | ⬜ |
| ★★ | 23 | Merge k Sorted Lists | 🔴 Hard | 分治合并 / 优先队列 | ⬜ |
| ★★ | 430 | Flatten Multilevel DLL | 🟡 Medium | DFS 展平 / 迭代栈 | ⬜ |
| ★★ | 237 | Delete Node in a Linked List | 🟢 Easy | 替换删除（无 head 参数） | ⬜ |

## 核心套路

```
反转            → pre/cur/next 三指针
快慢指针        → 找环 / 找中点 / 倒数第K
dummy 节点      → 头结点可能变化时使用
反转+合并       → Reorder List / 回文链表
归并排序        → Sort List（找中点+递归+合并）
环形入口        → 相遇后重置一头到 head，同步走再遇
相交节点        → 长链表先走长度差，同步走相遇
随机指针复制    → HashMap 原→新映射
LRU             → HashMap + 双向链表
尾部门置空      → 删除/修改链表后必检查
```

## 刷题路线

```
第一梯队（必会）：206 → 141 → 21 → 876 → 19 → 142
第二梯队（高频）：160 → 143 → 234 → 2 → 82 → 148 → 138
第三梯队（进阶）：83 → 203 → 61 → 109 → 92 → 24 → 146
第四梯队（扩展）：328 → 86 → 147 → 23 → 430 → 237
```

## 实现文件

- `algorithm/src/main/java/leetcode/LinkList.java` — 主实现
- `algorithm/src/main/java/infra/ListNode.java` — 链表节点定义
- `algorithm/src/test/java/leetcode/LinkListTest.java` — 测试

---

## 我的笔记

### 概览

- **dummy 节点**：头结点有可能变化时（删除/新增头部），设置 dummy 节点简化边界处理。
- **转置（反转）和快慢指针**是其他高级算法（回文、重排、环检测）的基础。
- **画图找 test case** 是最好的 debug 方式，直接思考易漏边界。
- **尾部置空**：链表结构改变后，最终指针 `next = null` 很容易漏掉，如 `removeElements` 最后需要 `pre.next = null`。
- 如果链表发生了改变，最后都需要检查尾部。

### 反转链表

pre / cur / next 三指针。

```
pre = null, cur = head
while cur != null:
    next = cur.next
    cur.next = pre
    pre = cur
    cur = next
return pre
```

### 找链表的中间节点

分两种情况（影响循环条件）：

- **偶数长度时，slow 指向中间的前一个节点**（用于 Reorder、回文等，需要保留中间左边）
- **偶数长度时，slow 指向中间的后一个节点**（标准 middleNode）

前者有特殊的其他用途，如断链操作。

### 有序链表的合并

- 如果可以复用原空间，有简化版本（就地拼接）。
- 如果要全部新空间，则无法简化。

### Remove Duplicates from Sorted List I / II

- **I（保留一个）**：直接遍历，重复则跳过 next。
- **II（不保留任何）**：通过 `pre.next` 是否变化判断有无重复。

### Remove Nth Node from End of the List

快慢指针，fast 先走 n 步，slow 和 fast 一起走到 fast 结束，此时 slow 指向待删除节点的前一个。

### 判断单链表有环 & 环入口点

**有环判断**：快慢指针相遇则有环，走到 null 则无环。

**入口点（数学推导）**：
1. fast 与 slow 相遇时，slow 肯定没遍历完链表，fast 已在环内转了 n 圈。
2. 设 slow 走了 d 步，fast 走了 2d 步。设环前长度 L，环长 S，相遇点在环内 X 处：
   - slow: L + X, fast: L + KS + X
   - 2(L + X) = L + KS + X → L + X = KS → **fast 再走 L 步即可到环入口**。
3. **slow 从头走，fast 从 X 处走，相遇点即为入口**。
4. slow 一定没遍历完链表：最差情况（环长 S，slow 在 1，fast 在 2），slow 走 S-1 步时 fast 走 2(S-1) 步，在 S 处相遇，slow 此时还没走完一圈。

### 单链表相交 & 第一个相交点

- 先遍历链表 A 到尾，再遍历链表 B，如果也能走到同一节点，则相交。
- 记下两链表长度，长链表先走 `lenMax - lenMin` 步，然后同步前进，相遇点即为第一相交点。

### Reorder List

三步走：① 找中间节点 ② 反转后半段 ③ 合并两段。

### Sort List (O(nlgn))

归并排序：找中点断开 → 递归排序两个子链表 → 合并有序链表。

### Copy List with Random Pointer

与 Clone Graph 一样，HashMap 记录原节点 → 新节点的映射。

### Convert Sorted List to BST

使用找中间节点的技巧，注意递归时尾部置空（中间节点的前一个节点的 next = null）。
