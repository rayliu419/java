# BFS (Breadth-First Search)

> 核心性质：**BFS 先访问到的节点一定距离最短（无权图）**；**BFS 天然按层遍历 → 适合层次、最短路径、拓扑排序**

## 题目列表（按面试频次降序）

| 频次 | 题号 | 题目 | 难度 | 核心思路 | 状态 |
|:---:|:----:|------|:----:|---------|:----:|
| ⭐⭐⭐⭐⭐ | 102 | Binary Tree Level Order Traversal | 🟡 Medium | 队列逐层遍历，每层先取当前 queue size | ⬜ |
| ⭐⭐⭐⭐⭐ | 200 | Number of Islands | 🟡 Medium | BFS/DFS 遍历整个网格，每遇到一个 '1' 启动 BFS 并将相连的 '1' 标记 | ⬜ |
| ⭐⭐⭐⭐⭐ | 207/210 | Course Schedule I/II | 🟡 Medium | 拓扑排序（入度表 + BFS 队列），完成顺序即入度 0 的出队顺序 | ⬜ |
| ⭐⭐⭐⭐⭐ | 103 | Binary Tree Zigzag Level Order Traversal | 🟡 Medium | 层次遍历 + 奇偶层方向反转（deque 或 reverse） | ⬜ |
| ⭐⭐⭐⭐⭐ | 994 | Rotting Oranges | 🟡 Medium | 多源 BFS：所有腐烂橘子同时入队作为第一批 | ✅ BFS.java |
| ⭐⭐⭐⭐ | 127/126 | Word Ladder I/II | 🔴 Hard | 单词差一字符即为边 → 图 BFS；II 需记录所有最短路径（前驱 map + 回溯） | ⬜ |
| ⭐⭐⭐⭐ | 199 | Binary Tree Right Side View | 🟡 Medium | 层次遍历取每层最后一个节点 | ⬜ |
| ⭐⭐⭐⭐ | 130 | Surrounded Regions | 🟡 Medium | 从边界 'O' 出发 BFS/DFS 标记，未被标记的 'O' 全变 'X' | ⬜ |
| ⭐⭐⭐⭐ | 542 | 01 Matrix | 🟡 Medium | 多源 BFS 从所有 0 出发，逐层扩散计算距离 | ⬜ |
| ⭐⭐⭐⭐ | 107 | Binary Tree Level Order Traversal II | 🟡 Medium | 层次遍历结果反转（List.add(0, level)） | ⬜ |
| ⭐⭐⭐ | 111 | Minimum Depth of Binary Tree | 🟢 Easy | BFS 首遇叶子节点即返回深度（比 DFS 更优） | ⬜ |
| ⭐⭐⭐ | 116/117 | Populating Next Right Pointers in Each Node | 🟡 Medium | 层次遍历连接同层节点，或用 O(1) 空间利用已建立的 next 指针 | ⬜ |
| ⭐⭐⭐ | 637 | Average of Levels in Binary Tree | 🟢 Easy | 层次遍历每层求和取平均 | ⬜ |
| ⭐⭐⭐ | 429 | N-ary Tree Level Order Traversal | 🟡 Medium | 同二叉树层次遍历，children list 代替 left/right | ⬜ |
| ⭐⭐⭐ | 513 | Find Bottom Left Tree Value | 🟡 Medium | BFS 按层遍历，记录每层首节点 | ⬜ |
| ⭐⭐⭐ | 417 | Pacific Atlantic Water Flow | 🟡 Medium | 从边界反流 BFS/DFS，两洋可达区域取交集 | ⬜ |
| ⭐⭐⭐ | 310 | Minimum Height Trees | 🟡 Medium | 拓扑排序剥洋葱法：从所有叶子开始逐层删除 | ⬜ |
| ⭐⭐ | Top View of Binary Tree | 🟡 Medium | 层次遍历 + 水平坐标 map，记录每个水平坐标首个访问到的节点（即最上层节点） | ⬜ |
| ⭐⭐ | 329 | Longest Increasing Path in a Matrix | 🔴 Hard | DFS + 记忆化搜索（BFS 也可但 DFS 更优；依赖拓扑序的本质） | ✅ BFS.java |
| ⭐⭐ | 279 | Perfect Squares | 🟡 Medium | 从 0 开始 BFS，每次加一个完全平方数，先到达 n 的路径最短 | ⬜ |
| ⭐⭐ | 909 | Snakes and Ladders | 🟡 Medium | 编号棋盘 BFS，蛇梯强制跳跃 | ⬜ |
| ⭐⭐ | 787 | Cheapest Flights Within K Stops | 🟡 Medium | BFS + Bellman-Ford（层次约束的最短路径） | ⬜ |

## 核心套路

```
二叉树层次遍历         → queue + size 分层
树的 Right Side View   → 取每层最后一个
树的 Zigzag            → 层次遍历 + 奇偶方向反转
Z 形 / Bottom 遍历      → 层次遍历变体（deque / reverse / 反转结果）
Top View               → 层次遍历 + 水平坐标 map（首次覆盖即最上层）

多源 BFS              → 所有起点同时入队作为第一批（腐烂橘子 / 01 矩阵）
矩阵 BFS              → 方向数组 + visited + 边界检查
从边界 BFS            → 从外围出发向内标记（Surrounded Regions / 太平洋大西洋）
图 BFS（无向/有向）    → 邻接表 + visited 状态（0/1/2 标记三种状态避免无限循环）
字符串/隐式图 BFS      → 差一个字符即为一条边（Word Ladder）

拓扑排序              → 入度表（indegree[]）+ 队列维护入度 0 节点
拓扑 BFS（层次）       → 支持区分层次（两个 set/vector cur/next），类似剥洋葱（Minimum Height Trees）

最短路径（无权图）     → BFS 首达即最短；需要记录路径时 → 父节点 map + 回溯
路径记录（多条）       → map<节点, vector<前驱>> + 回溯（Word Ladder II 关键技巧）
BFS + DP              → BFS 顺序天然满足最优子问题（01 Matrix）
```

## 刷题路线

```
第一梯队（必会）：102 → 200 → 207/210 → 103 → 994
第二梯队（高频）：127 → 199 → 130 → 542 → 107
第三梯队（进阶）：111 → 116 → 637 → 513 → 417
第四梯队（扩展）：Top View → 310 → 329 → 279 → 909
```

## 实现文件

- `algorithm/src/main/java/leetcode/BFS.java` — 主实现
- `algorithm/src/main/java/leetcode/Tree.java` — 树相关 BFS
- `algorithm/src/test/java/leetcode/BFSTest.java` — 测试

---

## 我的笔记

### BFS 的本质

BFS 就是逐层遍历。这个「层」的概念在二叉树中是指树的深度，在图中指距离起点的步数，在矩阵中指曼哈顿距离。**BFS 先访问到的节点一定具有最短距离（无权图）**，这是 BFS 最核心的性质。

### 二叉树层次遍历

- 经典写法：queue + 每层先取 `int size = queue.size()`，然后 for 循环处理当前层所有节点。
- 这个框架稍加改动就能解多道题：Right Side View（取每层最后一个）、Zigzag（加奇偶方向判断）、Bottom Level Order（结果反转）、Top View（加水平坐标）、Find Bottom Left（每层第一个）。

### 多源 BFS

- 腐烂橘子（994）和 01 矩阵（542）都是多源 BFS。
- 多源 BFS 没有特别的，就是把所有起点一起塞进 queue 作为第一批，然后逐层扩散。

### 图 BFS & 拓扑排序

- 图 BFS 和树 BFS 的最大区别是要记录 visited，避免重复访问。对于有向图拓扑排序，用 indegree 数组 + queue 维护入度为 0 的节点。
- Course Schedule 系列是拓扑排序的经典题，需要会构建邻接表。

### Word Ladder（高频难题）

Word Ladder II 是 BFS 综合性最强的题目，涉及多个技巧：

1. **隐式图构建**：两个单词差一个字符即视为有一条边。
2. **层次区分**：用两个 set（cur/next）代替 queue 来区分层，避免 queue 难以标记层次深度的问题。
3. **路径记录**：`map<string, vector<string>>` 记录每个单词的多个前驱（因为 BFS 中一个节点可能有多个最短前驱）。
4. **访问控制**：当前层的单词从 dict 中 erase 掉，避免后续层重复访问。但对于同层中不同路径都可达的节点，前驱信息仍要记录（这就是要用 set 的原因——即使单词重复出现，前驱不一样）。

回溯法从 endWord 开始，根据前驱 map 递归组装路径。

### Surrounded Regions 的关键技巧

- 本题的核心洞察：**被 'X' 包围的 'O' 一定不连通到边界**。
- 所以只需要从边界的 'O' 出发 BFS，标记所有能到达的 'O'（它们不会被翻转），剩下的 'O' 全部翻转为 'X'。
- 一个小技巧是引入新字符 '$' 来标记被访问过的 'O'，避免使用额外的 visited 数组，也能区分三种状态（'X'、'O'、被标记过的 'O'）。

### 01 Matrix 的两种思路

- **直接法**：对每个 1 做 BFS，遇到 0 即计算距离。需要多个队列区分层次。复杂度高。
- **BFS + DP**（更优）：从所有 0 出发多源 BFS。第一次访问到某个 1 时的路径长度一定是最短距离。这利用了 BFS 按层遍历的性质，距离动态累积。

### 矩阵 BFS 通用模板

```
int[] dirs = {0, 1, 0, -1, 1, 0, -1, 0}; // 或者 {{0,1},{1,0},{0,-1},{-1,0}}
Queue<int[]> queue = new LinkedList<>();
boolean[][] visited = new boolean[m][n];
// 多源则所有起点入队
queue.offer(new int[]{startX, startY});
visited[startX][startY] = true;
int steps = 0;
while (!queue.isEmpty()) {
    int size = queue.size();
    for (int i = 0; i < size; i++) {
        int[] cur = queue.poll();
        // 处理当前节点
        for (int d = 0; d < 4; d++) {
            int nx = cur[0] + dirs[d];
            int ny = cur[1] + dirs[d + 4]; // 或者 dirs[d*2], dirs[d*2+1]
            if (inBound(nx, ny) && !visited[nx][ny] && 条件) {
                visited[nx][ny] = true;
                queue.offer(new int[]{nx, ny});
            }
        }
    }
    steps++;
}
```
