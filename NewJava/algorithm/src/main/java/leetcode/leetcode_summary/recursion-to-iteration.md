# 递归 → 非递归的机械推导方法

## 核心洞察

**递归算法的本质是操作系统帮你维护了一个调用栈。** 非递归版本就是自己管这个栈。

树遍历的递归写法和非递归写法之间存在**机械的、一一对应的推导关系**，尤其是对于"线性递归"模式（递归调用在函数体中按顺序出现一次或多次）。

## 推导规则

给递归版本每行编号，然后按规则映射：

### 中序遍历

```java
// 递归版本                             // 非递归版本
void traverse(TreeNode node) {           while (cur != null || !stack.isEmpty()) {
    if (node == null) return;
                                             while (cur != null) {    // ← 对应 L1
    traverse(node.left);         /* L1 */        stack.push(cur);    //    左调用前存档
    visit(node);                 /* L2 */        cur = cur.left;
    traverse(node.right);        /* L3 */    }
}                                        cur = stack.pop();         // ← 从 L1 返回
                                         visit(cur);                // ← L2 执行访问
                                         cur = cur.right;           // ← L3 尾调用，直接转向
                                     }
```

| 递归中的事件 | 对应非递归操作 | 原因 |
|---|---|---|
| 调用 `traverse(left)` | `push(cur); cur = cur.left` | 调用前把当前帧存到栈里，然后跳转到左子树 |
| 从 `traverse(left)` 返回 | `cur = stack.pop()` | 左子树走完了，恢复上一层 |
| 执行 `visit(node)` | 直接访问 | 返回后继续执行下一行 |
| 调用 `traverse(right)` | `cur = cur.right` | 尾（末尾）调用，没有后续操作，无需存档 |

### 推导法则总结

```
中间位置的递归调用  →  push(当前节点) + 转向子节点（需要存档，回来还有代码要执行）
末尾位置的递归调用  →  直接转向子节点（尾调用，无需存档，没有后续代码）
访问操作的位置     →  决定 visit() 在 push 前还是 pop 后
```

## 从前序推导验证

```java
// 前序递归
void preorder(TreeNode node) {
    if (node == null) return;
    visit(node);                   // L1 — 先访问
    preorder(node.left);           // L2 — 中间调用
    preorder(node.right);          // L3 — 尾调用
}

// 按规则推导 → 非递归
void preorder(TreeNode root) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode cur = root;
    while (cur != null || !stack.isEmpty()) {
        while (cur != null) {
            visit(cur);             // L1 — push 之前先访问（跟中序的区别就在这里）
            stack.push(cur);        // L2 — 中间调用，存档
            cur = cur.left;
        }
        cur = stack.pop();
        cur = cur.right;            // L3 — 尾调用，直接转向
    }
}
```

**中序和前序的非递归代码结构完全一致，唯一区别是 `visit()` 的位置：**
- **前序**：`visit` 在 `push` 之前（先访问根，再向左）
- **中序**：`visit` 在 `pop` 之后（左子树访问完，再访问根）

这恰好反映了递归中 visit 语句相对于递归调用的位置。

## 后序为什么更复杂

```java
void postorder(TreeNode node) {
    if (node == null) return;
    postorder(node.left);    // L1 — 中间调用
    postorder(node.right);   // L2 — 中间调用（两个都是中间的！）
    visit(node);             // L3 — 末尾访问
}
```

从 L1 返回时，不是直接 visit，而是还要调用 L2。调用 L2 再返回后才能 visit。

**问题**：压栈时存的节点不足以判断"返回到哪条指令"。需要额外状态：
- 是从左子树返回的（接下来调 L2）
- 还是从右子树返回的（接下来 visit）

这就是后序需要**标记法（每个节点入栈两次）**或**双栈法**的根本原因 — 不是规则失效了，而是规则说"你需要额外状态来区分返回位置"。

### 标记法实现

```java
void postorder(TreeNode root) {
    Deque<Object[]> stack = new ArrayDeque<>(); // [node, visited]
    TreeNode cur = root;

    while (cur != null || !stack.isEmpty()) {
        while (cur != null) {
            stack.push(new Object[]{cur, false}); // false = 左边还没处理完
            cur = cur.left;
        }
        Object[] pair = stack.pop();
        cur = (TreeNode) pair[0];
        boolean visited = (boolean) pair[1];

        if (!visited) {
            stack.push(new Object[]{cur, true}); // 标记为"已处理左边"
            cur = cur.right;                     // L2 — 转向右
        } else {
            visit(cur);                          // L3 — 左右都处理完了，访问
            cur = null;                          // 继续弹栈
        }
    }
}
```

## 更多例子

### 例 1：二分查找（尾递归 → while 循环）

**尾递归**是转换最 trivial 的模式 — 递归调用在末尾，没有后续操作，直接改成循环即可。

```java
// 递归版本                                    // 非递归版本
int binarySearch(int[] arr, int t, int l, int r) {    int l = 0, r = arr.length - 1;
    if (l > r) return -1;                              while (l <= r) {           // ← 对应基准情形取反
    int m = l + (r - l) / 2;        /* L1 */               int m = l + (r - l) / 2;
    if (arr[m] == t) return m;      /* L2 */               if (arr[m] == t) return m;
    else if (arr[m] > t)                                   if (arr[m] > t)
        return binarySearch(arr, t, l, m - 1); /* L3a */       r = m - 1;         // ← 尾调用，直接改参数
    else                                                     else
        return binarySearch(arr, t, m + 1, r); /* L3b */       l = m + 1;         // ← 尾调用，直接改参数
    }                                                        }
    // 隐式 return -1                                    return -1;
}
```

**模式**：尾调用 → 不需要栈，直接**更新参数 + 跳转到循环开头**。

**判断标准**：`return f(...)` 是函数最后一条执行的语句，返回后没有代码需要执行。

### 例 2：二叉树最大深度（后序 + 返回值）

带返回值的递归（分治模式）怎么转？关键在于模拟调用栈的同时，还要模拟返回值如何在调用链中向上传递。

```java
// 递归版本
int maxDepth(TreeNode node) {                // 推导分析：
    if (node == null) return 0;              // 基准情形

    int left = maxDepth(node.left);   /* L1 */  // 中间调用①，回来还有代码
    int right = maxDepth(node.right); /* L2 */  // 中间调用②，回来还有代码
    return Math.max(left, right) + 1;    /* L3 */  // 后处理
}
```

L1 和 L2 都是中间调用（返回后还有代码），所以两个都需要压栈存档。但这里出现了新问题：**L1 的返回值 `left` 在 L2 和 L3 还要用**。

**推导结果 — 双栈（节点栈 + 返回值栈）**：

```java
int maxDepth(TreeNode root) {
    if (root == null) return 0;
    Deque<TreeNode> nodeStack = new ArrayDeque<>();
    Deque<Integer> retStack = new ArrayDeque<>();  // 存子树的返回值
    TreeNode cur = root;
    int lastRet = 0;  // 上一个子树的返回值

    while (cur != null || !nodeStack.isEmpty()) {
        while (cur != null) {                         // L1 — 一路向左入栈
            nodeStack.push(cur);
            retStack.push(0);                          // 占位，稍后更新
            cur = cur.left;
        }
        cur = nodeStack.pop();
        int leftRet = retStack.pop();
        int rightRet = cur.right == null ? 0 : maxDepth(cur.right); // L2
        lastRet = Math.max(leftRet, rightRet) + 1;                  // L3
        if (!nodeStack.isEmpty()) {
            // 把结果写回上一层栈帧的返回值位置
            retStack.pop();
            retStack.push(lastRet);
        }
        cur = null;  // 继续弹栈
    }
    return lastRet;
}
```

**更好的思路**：对于"后序 + 返回值"模式，很多情况下用**自底向上的 DP** 或 **BFS** 更简洁。这里的推导只是为了展示通用规则仍然适用。

### 例 3：快排（分治 + 子问题递推）

分治算法的递归模型是 **先 partition，再递归处理两个子区间**。非递归版用栈存待处理的区间边界。

```java
// 递归版本                                    // 推导分析：
void quickSort(int[] arr, int l, int r) {
    if (l >= r) return;                        // 基准情形

    int p = partition(arr, l, r);      /* L1 */    // 中间处理，不递归
    quickSort(arr, l, p - 1);          /* L2 */    // 中间调用①
    quickSort(arr, p + 1, r);          /* L3 */    // 尾调用②
}
```

L2 是中间调用（L3 还要执行），所以需要存档。L3 是尾调用，直接更新参数即可。但是 **L2 和 L3 的执行顺序不影响正确性**（子问题独立），所以实际上两个都可以当成"子问题"存到栈里。

```java
// 非递归版本 — 栈存子问题
void quickSort(int[] arr, int l, int r) {
    Deque<int[]> stack = new ArrayDeque<>();
    stack.push(new int[]{l, r});

    while (!stack.isEmpty()) {
        int[] range = stack.pop();
        l = range[0]; r = range[1];
        if (l >= r) continue;                // 基准情形

        int p = partition(arr, l, r);        // L1 — 分区

        // 两个子问题入栈（等价于递归调用 L2, L3）
        // 注意入栈顺序：想让左区间先处理，就后入栈
        stack.push(new int[]{p + 1, r});     // 右子问题
        stack.push(new int[]{l, p - 1});     // 左子问题
    }
}
```

**关键观察**：当两个递归调用**顺序无关**（子问题独立），可以用栈/队列来管理待处理任务，不需要区分"从哪个调用返回"。这种模式叫**工作栈模式（worklist algorithm）**，宽度优先就换队列。

### 例 4：子集 / 全排列（回溯）

回溯的递归结构是 **循环内嵌递归**，每一层递归对应循环的一层嵌套。非递归版要用栈来管理"当前走到哪一步了"。

```java
// 递归版本 — 子集
void subsets(int[] nums, int start, List<Integer> path, List<List<Integer>> res) {
    res.add(new ArrayList<>(path));                    // L1 — 收集结果

    for (int i = start; i < nums.length; i++) {        // L2 — 循环
        path.add(nums[i]);                             // L2.1
        subsets(nums, i + 1, path, res);      /* L3 */ // 中间调用
        path.remove(path.size() - 1);                  // L2.2 回溯
    }
}
```

非递归的关键：**栈帧 = (start索引, 循环当前位置i, path快照)**。

```java
// 非递归版本
List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    Deque<Object[]> stack = new ArrayDeque<>();
    stack.push(new Object[]{0, 0, new ArrayList<Integer>()}); // {start, i, path}

    while (!stack.isEmpty()) {
        Object[] frame = stack.pop();
        int start = (int) frame[0];
        List<Integer> path = (List<Integer>) frame[2];

        res.add(new ArrayList<>(path));              // L1 — 收集

        for (int i = start; i < nums.length; i++) {
            List<Integer> newPath = new ArrayList<>(path);
            newPath.add(nums[i]);
            stack.push(new Object[]{i + 1, 0, newPath}); // L3 — 子问题入栈
        }
    }
    return res;
}
```

对于全排列（需要 `used[]` 数组），栈帧中还要包含已使用元素的状态。模式不变：**栈帧 = 这一层需要的所有局部变量**。

### 例 5：合并两个有序链表（线性递归 → 指针迭代）

链表操作中常见的递归模式：递归构建结果链，返回向上传递。这里的特点也是**尾递归**。

```java
// 递归版本                                    // 非递归版本
ListNode merge(ListNode l1, ListNode l2) {
    if (l1 == null) return l2;                 // 基准
    if (l2 == null) return l1;

    if (l1.val < l2.val) {
        l1.next = merge(l1.next, l2);          // 尾调用
        return l1;
    } else {
        l2.next = merge(l1, l2.next);          // 尾调用
        return l2;
    }
}

// 非递归版本 — dummy + 尾指针迭代
ListNode merge(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode cur = dummy;

    while (l1 != null && l2 != null) {         // 对应递归的基准情形取反
        if (l1.val < l2.val) {
            cur.next = l1;
            l1 = l1.next;                      // 更新参数，"递归"处理
        } else {
            cur.next = l2;
            l2 = l2.next;
        }
        cur = cur.next;
    }
    cur.next = l1 != null ? l1 : l2;           // 对应两个基准情形
    return dummy.next;
}
```

## 完整模式图谱

| 递归模式 | 特征 | 非递归策略 | 是否需要栈 | 例子 |
|---------|------|-----------|-----------|------|
| **尾递归** | `return f(...)` 是最后一条语句 | 直接改循环（更新参数） | ❌ | 二分查找、阶乘、链表反转 |
| **单递归调用（非尾）** | 调用完还有代码要执行 | push 存档 → 处理子问题 → pop 恢复 | ✅ 节点栈 | 树遍历、二分插入 |
| **双递归调用（树形）** | 两个子问题调用 | ① 标记法区分返回位置 ② 工作栈法 | ✅ 带状态栈 | 树遍历、分治 |
| **分治（子问题独立）** | 先分割，再并行处理子问题 | 工作栈法（子问题入栈/队列） | ✅ 子问题队列 | 快排、归并排序 |
| **回溯（循环+递归）** | for 循环内嵌递归，每层递归对应一层循环 | 栈帧 = 局部变量打包，子问题入栈 | ✅ 帧栈 | 全排列、子集、组合 |
| **线性递归（含返回值）** | 递归调用结果参与运算 | 节点栈 + 返回值栈 | ✅ 双栈 | 树最大深度、斐波那契 |

## 从模式到代码：一个推导工作流

遇到递归转非递归，按这个流程思考：

```
① 找到所有递归调用位置
   ├── 全部在末尾 → 尾递归，直接改循环（例 1、例 5）
   └── 有中间调用 → 需要栈

② 找中间调用（后面还有代码要执行）
   ├── 都需要区分"返回到哪条指令"吗？
   │   ├── 是 → 用标记法（后序遍历模式）
   │   └── 否 → 工作栈法（子问题独立，例 3、例 4）
   └── 需要保存返回值吗？
       ├── 是 → 需要双栈（例 2）
       └── 否 → 单栈足够

③ 确定栈帧结构
   └── 栈帧里存什么 = 递归函数中所有"需要跨调用存活"的局部变量
```

## 理解路径

理解递归→非递归推导，有三个层次：

1. **记忆层** — 记住口诀"一路向左入栈，弹出访问，转向右"
2. **理解层** — 理解非递归是在**手动模拟递归调用栈**，每一行递归代码可以机械地映射为对栈的操作
3. **应用层** — 遇到新型递归结构时，用同样的推导规则写出非递归版本

## 可视化

交互式演示见项目根目录的 [bst-visual.html](../bst-visual.html)，可逐步骤观察栈的变化。
