# Stack (栈)

> 核心性质：**利用逆序/正序对的触发时机来高效结算元素；每个元素最多 push/pop 一次 → O(n)**

## 核心套路

```text
右边第一个更大的    → 单调递减栈（逆序对触发结算）
右边第一个更小的    → 单调递增栈（正序对触发结算）
比较条件含等号？    → 等号触发可/否要看是否影响结果
栈的核心在于入栈时就表示了一定的相对顺序关系
识别特征：算法与扫描顺序相关，又不能对数组重新排序 → 可能是栈
```

**Java 工程实践**：
- 队列和栈都使用 `Deque`（`ArrayDeque` 实现）
- 栈：`Deque<Integer> stack = new ArrayDeque<>();` 使用 `push` / `pop` / `peek`
- 队列：`Queue<Integer> queue = new ArrayDeque<>();` 使用 `offer` / `poll` / `peek`

---

## 题目列表（按面试频次降序）

| 频次 | 题号 | 题目 | 难度 | 核心思路 | 状态 |
|:---:|:----:|------|:----:|---------|:----:|
| ★★★★★ | 20 | [Valid Parentheses](#1-valid-parentheses-20) | 🟢 Easy | 栈的入门匹配 | ⬜ |
| ★★★★★ | 155 | [Min Stack](#2-min-stack-155) | 🟢 Easy | 设计题，辅助栈 | ⬜ |
| ★★★★★ | 496 | [Next Greater Element I](#3-next-greater-element-i-496--daily-temperatures-739) | 🟢 Easy | 单调栈入门 | ✅ StackS.java |
| ★★★★☆ | 739 | [Daily Temperatures](#3-next-greater-element-i-496--daily-temperatures-739) | 🟡 Medium | 单调栈+距离 | ⬜ |
| ★★★★☆ | 84 | [Largest Rectangle in Histogram](#4-largest-rectangle-in-histogram-84) | 🔴 Hard | 单调栈经典 | ⬜ |
| ★★★★☆ | 42 | [Trapping Rain Water](#5-trapping-rain-water-42) | 🔴 Hard | 单调栈+凹槽 | ✅ StackS.java |
| ★★★★☆ | 227 | [Basic Calculator II](#6-basic-calculator-ii-227) | 🟡 Medium | 表达式求值，+-*/ | ✅ StackS.java |
| ★★★★☆ | 224 | [Basic Calculator](#7-basic-calculator-224) | 🔴 Hard | 带括号表达式 | ✅ StackS.java |
| ★★★★☆ | 394 | [Decode String](#8-decode-string-394) | 🟡 Medium | 栈+字符串解码 | ⬜ |
| ★★★☆☆ | 32 | [Longest Valid Parentheses](#9-longest-valid-parentheses-32) | 🔴 Hard | 栈存下标求最长 | ⬜ |
| ★★★☆☆ | 150 | [Evaluate Reverse Polish Notation](#10-evaluate-reverse-polish-notation-150) | 🟡 Medium | 后缀表达式求值 | ⬜ |
| ★★★☆☆ | 735 | [Asteroid Collision](#11-asteroid-collision-735) | 🟡 Medium | 栈模拟碰撞 | ⬜ |
| ★★★☆☆ | 71 | [Simplify Path](#12-simplify-path-71) | 🟡 Medium | Unix路径简化 | ⬜ |
| ★★☆☆☆ | 85 | [Maximal Rectangle](#13-maximal-rectangle-85) | 🔴 Hard | 复用Largest Rectangle | ⬜ |
| ★★☆☆☆ | 1047 | [Remove All Adjacent Duplicates](#14-remove-all-adjacent-duplicates-1047) | 🟢 Easy | 栈模拟去重 | ⬜ |
| ★★☆☆☆ | 901 | [Online Stock Span](#15-online-stock-span-901) | 🟡 Medium | 单调栈变体 | ⬜ |
| ★★☆☆☆ | 232 | [Implement Queue using Stack](#16-stack--queue-互相关) | 🟢 Easy | 双栈实现队列 | ⬜ |
| ★★☆☆☆ | 225 | [Implement Stack using Queue](#16-stack--queue-互相关) | 🟢 Easy | 双队列实现栈 | ⬜ |
| ★★☆☆☆ | — | [Implement Stack with Array](#17-implement-stack-with-array) | 🟢 Easy | 扩容设计 | ⬜ |

---

## 刷题路线

```
第一梯队（必会）：20 → 155 → 496 → 84 → 42
第二梯队（高频）：739 → 227 → 224 → 394 → 32
第三梯队（进阶）：150 → 735 → 71 → 85 → 1047
第四梯队（扩展）：901 → 225/232 → 数组实现栈
```

---

## 1. Valid Parentheses (20)

**题目**：判断括号字符串 `()[]{}` 是否有效。

**思路**：左括号入栈，遇到右括号时检查栈顶是否匹配，最后栈应为空。

```java
Deque<Character> stack = new ArrayDeque<>();
for (char c : s.toCharArray()) {
    if (c == '(') stack.push(')');
    else if (c == '[') stack.push(']');
    else if (c == '{') stack.push('}');
    else if (stack.isEmpty() || stack.pop() != c) return false;
}
return stack.isEmpty();
```

---

## 2. Min Stack (155)

**题目**：实现一个栈，支持 push, pop, top, getMin 操作，getMin 在常数时间内返回最小值。

**思路**：使用两个栈，一个主栈，一个辅助栈存当前最小值。压栈时检查新元素是否 ≤ 当前最小值；出栈时如果等于当前最小值则辅助栈也出栈。

**优化**：用一个栈，存差值（diff = val - min），但可读性差。面试推荐双栈法。

---

## 3. Next Greater Element I (496) / Daily Temperatures (739)

**题目**：找出数组中每个元素右边第一个比它大的元素（496）；找出每个元素距离右边第一个更高温度的距离（739）。

**思路**：单调递减栈（存下标）。从左到右遍历，当 `nums[i] > nums[stack.peek()]` 时，栈顶找到了下一个更大元素，弹出并记录答案。时间复杂度 O(n)。

```java
// 下一个更大元素
Deque<Integer> stack = new ArrayDeque<>();
for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
        result[stack.pop()] = nums[i];
    }
    stack.push(i);
}
// 剩余的元素默认 -1
```

**为什么可以丢弃**：当 `nums[stack.peek()] < nums[i]` 时，这个栈顶已经被 nums[i] 挡住了。它右边的元素即使更大也不可能是"第一个"更大，而且 nums[i] 本身就比它大，对左边的元素来说也更有资格成为候选。

**Daily Temperatures** 只是把记录"值"改成记录"距离"：`result[stack.pop()] = i - idx`。

---

## 4. Largest Rectangle in Histogram (84)

**题目**：柱状图中最大的矩形面积。

**思路**：单调**递增**栈。核心是对每个柱子，找到它左右第一个比它矮的柱子，计算以它为高的最大矩形面积。

- 扫描到 i，当 `height[i] <= height[stack.peek()]` 时开始结算
- pop 出栈顶元素 x（高度 h），它的**右边界**就是 i（右边首个小于 x 的高度）
- **左边界**是栈 pop 后的新栈顶 y（高度小于 x），如果栈空则左边界为 -1
- 面积 = `h * (i - left - 1)`

**等号处理细节**：当 `height[i] == height[stack.peek()]` 时也触发结算。虽然第一个相同的柱子右边界算窄了，但后面相同的柱子会补上正确的面积——第一个柱子被弹出后，第二个柱子可以算出正确结果。

```java
Deque<Integer> stack = new ArrayDeque<>();
int maxArea = 0;
for (int i = 0; i <= n; i++) {
    int h = (i == n) ? 0 : heights[i];
    while (!stack.isEmpty() && heights[stack.peek()] >= h) {
        int height = heights[stack.pop()];
        int left = stack.isEmpty() ? -1 : stack.peek();
        maxArea = Math.max(maxArea, height * (i - left - 1));
    }
    stack.push(i);
}
```

---

## 5. Trapping Rain Water (42)

**思路**：单调**递减**栈。找"凹槽"——两侧高、中间低的结构。

当 `height[i] > height[stack.peek()]` 时形成逆序对（栈预期递减，但当前元素更大），说明出现了右壁。过程：
1. pop 出栈顶作为"底部"
2. 如果栈空了 → 没有左壁，盛不了水
3. 新栈顶 = 左壁，i = 右壁
4. 积水面积 = `(min(左壁高, 右壁高) - 底部高) × (i - 左壁 - 1)`

**与 Largest Rectangle 的区别**：
| | 栈类型 | 逆序对含义 | 结算内容 |
|--|--------|-----------|---------|
| Largest Rectangle | 递增栈 | 遇到更矮的→结算弹出元素 | 栈顶自身高度×宽度 |
| Trapping Rain Water | 递减栈 | 遇到更高的→形成凹槽 | 弹出元素的左右壁形成的坑 |

---

## 6. Basic Calculator II (227)

**题目**：仅包含 `+-*/` 的表达式求值，无括号。

**思路**：带符号推入结果。用 `lastOp` 记录上一个操作符，`curNum` 解析当前数字，`processing` 存乘除的中间结果。

```java
int result = 0, processing = 0, curNum = 0;
char lastOp = '+';
for (int i = 0; i < s.length(); i++) {
    char c = s.charAt(i);
    if (Character.isDigit(c)) curNum = curNum * 10 + (c - '0');
    if (c == '+' || c == '-' || c == '*' || c == '/' || i == s.length() - 1) {
        switch (lastOp) {
            case '+' -> processing += curNum;
            case '-' -> processing -= curNum;
            case '*' -> processing *= curNum;
            case '/' -> processing /= curNum;
        }
        if (c == '+' || c == '-' || i == s.length() - 1) {
            result += processing;
            processing = 0;
        }
        lastOp = c;
        curNum = 0;
    }
}
```

**关键**：`+`、`-` 被当作数字的符号一起处理。`processing` 带着符号在做乘除，遇到 `+`/`-` 时才合并到 `result`。

---

## 7. Basic Calculator (224)

**题目**：包含 `+-()` 的表达式求值。

**思路**：括号可以看作是表达式的"分段计算"。遇到 `(` 时将当前结果和符号压栈，重新初始化 result 和 sign；遇到 `)` 时把栈中的结果和当前结果合并。

---

## 8. Decode String (394)

**题目**：解码 `3[a2[c]]` → `accaccacc`。

**思路**：两个栈（或一个栈存辅助信息），一个存数字，一个存字符串。遇到 `]` 时出栈构造重复字符串。比较自然有两种解法：
1. 双栈法（数字栈 + 字符串栈）
2. 递归法（解析递归结构）

---

## 9. Longest Valid Parentheses (32)

**题目**：找出最长有效括号子串的长度。

**思路**：
- 核心思路是栈存 `(` 的下标
- 遇到 `)` 时：如果栈非空 → pop 出一个 `(` 与之匹配，当前有效长度 = `i - stack.peek()`（如果栈空了则 = `i - left`）
- `left` 变量记录了当前可能的匹配起点，当发生无法匹配的 `)` 时（栈为空时的 `)`），更新 `left = i`
- 更巧妙的解法：把不匹配的 `(` 和 `)` 的下标都放到栈里，最后栈中留下的就是无法匹配的位置，计算它们之间的差值即可

---

## 10. Evaluate Reverse Polish Notation (150)

**题目**：计算逆波兰表达式（后缀表达式）的值。

**思路**：遇到数字入栈，遇到运算符则 pop 两个数字运算后入栈。注意 `/` 和 `-` 的顺序（先出栈的是右操作数）。

---

## 11. Asteroid Collision (735)

**题目**：小行星碰撞。正数向右，负数向左，绝对值大的撞碎小的。

**思路**：栈模拟。遍历数组，当 `cur < 0` 且栈顶为正时需要判断碰撞：
- `top > -cur` → cur 爆炸
- `top == -cur` → 两者都爆炸
- `top < -cur` → top 爆炸，继续检查

---

## 12. Simplify Path (71)

**题目**：简化 Unix 风格的绝对路径。

**思路**：按 `/` 分割，遇到 `..` 弹出栈顶，遇到 `.` 或空字符串忽略，其他入栈。最后用 `/` 拼接。

---

## 13. Maximal Rectangle (85)

**题目**：01 矩阵中全为 1 的最大矩形面积。

**思路**：将每一行作为"底"，计算该行往上连续的 1 的高度（遇到 0 重置高度），然后用 Largest Rectangle in Histogram 的算法计算每行的最大矩形面积。逐行更新 → O(n × m)。

**DP 解法**：三个数组 `height`, `left`, `right`，逐行更新边界。

---

## 14. Remove All Adjacent Duplicates (1047)

**题目**：反复删除相邻重复字母，直到不能删除为止。

**思路**：栈（或 StringBuilder 模拟栈）。遍历字符，如果等于栈顶则弹出，否则入栈。最后栈中剩余即为结果。

---

## 15. Online Stock Span (901)

**题目**：设计一个股票跨度类，计算当天价格连续小于或等于今天价格的天数。

**思路**：单调递减栈，栈中存 `(价格, 跨度)` 的 pair。新价格来的时候，把所有小于等于它的栈顶弹出，累计跨度，再入栈。

---

## 16. Stack / Queue 互相关

### Queue using Stack (232)
两个栈实现队列。一个栈用于入队，一个栈用于出队。出队时如果出队栈为空，把入队栈全部倒入出队栈。**均摊 O(1)**。

### Stack using Queue (225)
两个队列实现栈。入栈时把元素加到非空队列，出栈时把 n-1 个元素移到另一个队列，剩下那个就是栈顶。或者用单队列：入栈时先加进去，然后轮转 n-1 次。

---

## 17. Implement Stack with Array

用数组实现栈（或 Deque 的底层实现）的几个要点：
- **扩容**：满时 double size 然后 copy。平摊分析：push N 个元素，总操作数 3N，均摊 O(1)
- **缩容策略**：`if (size == capacity / 4) resize(capacity / 2)`。用 1/4 而不是 1/2 是为了防止 thrashing（缩容后 push → 扩容 → pop → 缩容的震荡）
- **空栈 pop**：应抛出异常，不要返回 null
- Java 的 `ArrayDeque` 内部就是循环数组，按需扩容

---

## 单调栈速查表

| 场景 | 栈类型 | 结算触发条件 | 结算内容 |
|------|--------|-------------|---------|
| 右边第一个更大的 | 递减栈 | `cur > stack.top` | 栈顶的答案 = cur |
| 右边第一个更小的 | 递增栈 | `cur < stack.top` | 栈顶的答案 = cur |
| Largest Rectangle | 递增栈 | `cur <= stack.top` | 栈顶为高，宽 = cur_idx - left_idx - 1 |
| Trapping Rain Water | 递减栈 | `cur > stack.top` | 栈顶为底，左右壁形成凹槽 |
| 左边第一个更小的 | 递增栈 | `cur < stack.top` | 左边的答案在处理过程中结算 |

---

## 实现文件

- `algorithm/src/main/java/leetcode/StackS.java` — nextGreaterElement, trap, calculate, calculate2
