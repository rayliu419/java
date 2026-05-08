package leetcode;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

public class StackS {

    /**
     * <a href="https://leetcode.cn/problems/next-greater-element-i">...</a>
     *
     * 题目：在一个无序数组中，找出每个元素右边第一个比它大的元素，不存在则返回 -1。
     *
     * 思路分析：
     * 暴力解法是 O(n²) —— 每个元素都向右扫描找到第一个更大值。
     * 单调栈优化到 O(n) 的核心是：利用"元素之间的大小关系"及时丢弃不可能成为答案的候选者。
     *
     * 从左到右遍历，维护一个栈底到栈顶单调递减的栈（存下标）：
     * - 当前元素 nums[i] 比栈顶大 → 栈顶找到了它的下一个更大元素，出栈
     * - 当前元素 nums[i] 比栈顶小或等于 → 入栈（成为新的候选）
     * - 遍历结束后栈中剩余元素 → 无下一个更大元素，返回 -1
     *
     * 为什么能丢弃？因为当 nums[stack.peek()] < nums[i] 时，
     * 这个栈顶元素已经被 nums[i]"挡住"了，它右边的元素即使更大也不可能是"第一个"更大，
     * 而且 nums[i] 比它大且更靠右，对再左边的元素来说也更有资格成为候选。
     * 每出栈一个元素，它的答案就确定了 —— 这就是单调栈"及时结算"的思想。
     *
     * 时间复杂度 O(n)，空间复杂度 O(n)
     */
    public static int[] nextGreaterElement(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        // 初始化为 -1，栈中剩余元素无需再处理
        Arrays.fill(result, -1);
        // 单调递减栈，存下标
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            // 当前元素比栈顶大 → 栈顶找到了下一个更大元素
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                result[stack.pop()] = nums[i];
            }
            stack.push(i);
        }

        return result;
    }

    /**
     * <a href="https://leetcode.cn/problems/trapping-rain-water/">接雨水</a>
     *
     * 题目：给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算下雨后能接多少雨水。
     *
     * 思路分析（单调栈 + 逆序对视角）：
     * 接雨水本质上是在找"凹槽"——两侧高、中间低的结构。
     * 用单调递减栈来维护可能的左墙壁。当遇到 height[i] > height[stack.peek()] 时，
     * 形成了一个逆序对（当前 > 栈顶，但栈是递减的预期），这说明出现了凹槽的右壁。
     *
     * 具体过程：
     * - 栈中存下标，高度从栈底到栈顶递减
     * - 遍历到 i 时，如果 height[i] > height[stack.peek()]：
     *   1. pop 出栈顶作为"底部"
     *   2. 如果栈空了 → 没有左壁，盛不了水，跳出
     *   3. 新的栈顶就是左壁，i 是右壁
     *   4. 积水 = (min(height[左壁], height[i]) - height[底部]) × (i - 左壁 - 1)
     * - 注意这里是 height[i] > height[stack.peek()]（严格大于），
     *   因为高度相等时不会形成能蓄水的凹槽
     *
     * 与柱状图最大矩形的区别：
     * - 柱状图最大矩形：找左右第一个更矮的（递增栈，逆序对触发结算）
     * - 接雨水：找左右第一个更高的（递减栈，逆序对形成凹槽）
     * - 矩形结算的是栈顶自身高度；雨水结算的是栈顶被 pop 后的左右壁形成的"水坑"
     *
     * 时间复杂度 O(n)，空间复杂度 O(n)
     */
    public static int trap(int[] height) {
        int n = height.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int totalWater = 0;

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && height[stack.peek()] < height[i]) {
                // 弹出底部（凹槽最低处）
                int bottom = stack.pop();
                if (stack.isEmpty()) {
                    // 没有左壁，无法形成凹槽
                    break;
                }
                int left = stack.peek();
                // 积水高度 = min(左壁, 右壁) - 底部
                int h = Math.min(height[left], height[i]) - height[bottom];
                // 积水宽度 = 距离 - 1
                int w = i - left - 1;
                totalWater += h * w;
            }
            stack.push(i);
        }

        return totalWater;
    }

    /**
     * https://leetcode.com/problems/basic-calculator-ii/
     * 仅仅包含加减乘除的计算器
     *
     * @param s
     * @return
     */
    public static int calculate(String s) {
        // 存放当前阶段计算的结果
        int result = 0;
        // 存放被+-/隔开的中间运算结果。例如 2-3*4/2+1，processing在计算完3*4/2才会合并到result里
        int processing = 0;
        // 存放当前正在parse的数
        int curNum = 0;
        // 在发现当前是一个计算符时，需要用到上次见到的计算符来做运算，第一次遇到运算符时，是不能马上计算的。
        char lastOp = '+';
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            /**
             * 遇到的字符主要分为两大类：
             * 1. 数字，字符要parse成curNum，逻辑比较简单。
             *    如果不是最后一个，将累计的字符parse到curNum，逻辑比较简单。
             *    如果是最后一个，先解析，然后最后要收尾了，既要计算processing，也要合并到result中
             * 2. 计算符号
             *    +：此时需要将processing和curNum结合lastOp计算，同时将processing累计到result
             *    -：同+
             *    *: 此时需要将processing和curNum结合lastOp计算，不过按照我们的思路，此时不需要累加到result
             *    /: 同*
             *    经过分析，计算符号都需要计算processing，区别在于要不要合并到result
             */
            if (Character.isDigit(c)) {
                /**
                 * 一般的思路应该是发现是数字解析完continue，这里继续往下的原因是如果这里直接continue，
                 * 对于最后一个数，需要在循环外再按照循环里面的方式同样处理，代码冗余
                 */
                curNum = curNum * 10 + s.charAt(i) - '0';
            }
            // 由于上面没有用continue，所以下面要单独判断字符的情况
            if (c == '+' || c == '-' || c == '*' || c == '/' || i == s.length() - 1) {
                /**
                 *  这个逻辑是把+，-当成数字的符号一起处理了。curRes可能为负。
                 *  考虑1-5/2的情况，处理到/时，上一个op是-，processing=0, curNum=5，
                 *  所以下面的处理把processing变成了-5。后序的处理一直带着这个符号在做乘除。最后只需要result+=curRes。
                 */
                // 所有符号放在一起处理，都计算processing
                switch (lastOp) {
                    case '+':
                        processing = processing + curNum;
                        break;
                    case '-':
                        processing = processing - curNum;
                        break;
                    case '*':
                        processing = processing * curNum;
                        break;
                    case '/':
                        processing = processing / curNum;
                        break;
                }
                // 如果是+，-或者最后一个数，要把processing合并到result，processing清零
                if (c == '+' || c == '-' || i == s.length() - 1) {
                    result += processing;
                    processing = 0;
                }
                // 只要是计算符号，都需要更新curNum和lastOp。
                lastOp = c;
                curNum = 0;
            }
        }
        return result;
    }


    /**
     *
     * https://leetcode.com/problems/basic-calculator/
     * 带+-()的表达式
     * 解法1：
     *    类似于calculate的算法，但是感觉很tricky，需要在某些特殊情况下改lastOp。比较难以理解
     * 解法2：
     *    使用栈。对于表达式 2+((3+4+5-1)+(1-3))，与calculate类似，可以用两段计算：
     *    碰到(，则认为要开始一段新的计算过程，将当前的符号和已有的运算结果存下来。
     *    碰到)，则可以把这段新的计算过程结束了，并且把和以前栈里的结果合并到一起了。
     *    +-比较简单
     * @param s
     * @return
     */
    public static int calculate2(String s) {
        // 存放当前正在计算的结果
        int result = 0;
//        不在需要processing，用栈来保存上一段
//        int processing = 0;
        // 存放当前正在parse的数
        int curNum = 0;
        // 没有*/，所以用sign表示正负，其实作用类似lastOp
        int sign = 1;
        Stack<Integer> stack = new Stack();
        for (int i = 0; i < s.length(); i++) {
            /**
             * 遇到的字符只有两类：
             * 1. 数字，字符要parse成curNum，逻辑比较简单。
             *    如果不是最后一个，将累计的字符parse到curNum，逻辑比较简单。
             *    如果是最后一个，先解析，然后最后要收尾了，要合并到result中
             * 2. 计算符号或者括号
             *    +：此时需要将result和curNum结合sign计算，sign=1
             *    -：同+，sign=-1
             *    (: 需要将当前的计算结果保存起来，连同lastOp(sign)
             *    ): 将当前的计算结果计算完，同时合并栈里面上一段结果，更新整个结果
             */
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                curNum = curNum * 10 + s.charAt(i) - '0';
            }
            if (c == '+' || c == '-' || c == '(' || c == ')' || i == s.length() - 1) {
                switch (c) {
                    case '+': {
                        result = result + sign * curNum;
                        sign = 1;
                        curNum = 0;
                        break;
                    }
                    case '-': {
                        result = result + sign * curNum;
                        sign = -1;
                        curNum = 0;
                        break;
                    }
                    case '(': {
                        stack.push(result);
                        stack.push(sign);
                        // 为新的一段计算初始化，与循环进来的初始化条件一样
                        result = 0;
                        sign = 1;
                        curNum = 0;
                        break;
                    }
                    case ')': {
                        // 计算上一轮遗留的
                        result = result + sign * curNum;
                        int lastSign = stack.pop();
                        int lastResult = stack.pop();
                        result = lastResult + lastSign * result;
                        curNum = 0;
                        break;
                    }
                }
                if (i == s.length() - 1 && Character.isDigit(c)) {
                    // 上面没处理的
                    result = result + sign * curNum;
                }
            }
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println(calculate2("1+1"));
        System.out.println(calculate2("2-1+2"));
        System.out.println(calculate2("(1+(4+5+2)-3)+(6+8)")); // 1 + 8 + 14
        System.out.println(calculate2("(1+(4+5+2)-3)-(6+8)")); // 1 + 8 - 14
        System.out.println(calculate2("(1+(4+5+2)-3)-10")); // 1+ 8 - 10
    }
}
