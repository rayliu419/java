package leetcode;

import java.util.Stack;

public class StackAlgorithm {

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
        Stack<Integer> stack = new Stack<>();
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
