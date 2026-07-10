package leetcode;

import java.util.Stack;

public class Evaluation {

    /**
     * 加减法计算器（仅支持 + 和 -）
     *
     * @param s
     * @return 计算结果
     */
    int calculateAddMinus(String s) {
        if (s == null || s.isEmpty()) return 0;

        int res = 0;
        int num = 0;
        char sign = '+';
        int len = s.length();

        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);

            // 1. 组合多位数
            if (Character.isDigit(ch)) {
                num = num * 10 + (ch - '0');
            }

            // 2. 遇到运算符或者是最后一个字符，触发结算
            if (ch == '+' || ch == '-' || i == len - 1) {
                if (sign == '+') {
                    res += num;
                } else {
                    res -= num;
                }
                sign = ch;
                num = 0;
            }
        }

        return res;
    }

    /**
     * LeetCode 227
     * 引入* / 计算符
     * 3 + 2 * 5 / 2 + 4
     */
    static int calculateMultiplyDivide(String s) {
        if (s == null || s.isEmpty()) return 0;

        int res = 0;
        int lastNum = 0;
        int num = 0;
        char sign = '+';

        int len = s.length();
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);

            // 1. 组合多位数
            if (Character.isDigit(ch)) {
                num = num * 10 + (ch - '0');
            }

            // 2. 遇到运算符或者是最后一个字符，触发结算
            // 检查 ch（当前字符）而非 sign：运算符是触发结算的信号
            if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || i == len - 1) {
                if (sign == '+') {
                    res += lastNum;
                    lastNum = num;
                } else if (sign == '-') {
                    res += lastNum;
                    lastNum = -num;
                } else if (sign == '*') {
                    lastNum = lastNum * num;
                } else if (sign == '/') {
                    lastNum = lastNum / num;
                }

                sign = ch;
                num = 0;
            }
        }

        res += lastNum;
        return res;
    }

    /**
     * 带括号的加减法
     * @param s
     * @return
     */
    int calculateWithParentheses(String s) {
        if (s == null || s.isEmpty()) return 0;

        int res = 0;
        int num = 0;
        int sign = 1;
        Stack<Integer> stack = new Stack<>();
        int len = s.length();

        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            }

            if (c == '+' || c == '-' || c == '(' || c == ')' || i == len - 1) {
                switch (c) {
                    case '+':
                        res += sign * num;
                        sign = 1;
                        num = 0;
                        break;
                    case '-':
                        res += sign * num;
                        sign = -1;
                        num = 0;
                        break;
                    case '(':
                        stack.push(res);
                        stack.push(sign);
                        res = 0;
                        sign = 1;
                        num = 0;
                        break;
                    case ')':
                        res += sign * num;
                        int lastSign = stack.pop();
                        int lastRes = stack.pop();
                        res = lastRes + lastSign * res;
                        num = 0;
                        break;
                    default:
                        res += sign * num;
                        break;
                }
            }
        }

        return res;
    }
}


