package leetcode;

public class StackAlgorithm {

    /**
     * https://leetcode.com/problems/basic-calculator-ii/
     * 一个包含加减乘除的计算器
     *
     * @param s
     * @return
     */
    public static int calculate(String s) {
        int result = 0;
        // 存放当前正在计算的结果
        int processing = 0;
        // 存放当前正在parse的数
        int curNum = 0;
        char lastOp = '+';
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                curNum = curNum * 10 + s.charAt(i) - '0';
            }
            if (c == '+' || c == '-' || c == '*' || c == '/' || i == s.length() - 1) {
//                这个逻辑是把+，-当成数字的符号一起处理了。curRes可能为负。
//                考虑1-5/2的情况，处理到/时，上一个op是-，curRes=0, num=5，
//                所以下面的处理把curRes变成了-5。后序的处理一直带着这个符号在做乘除。
//                最后只需要result+=curRes，这个是很容易出错的case。
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
                if (c == '+' || c == '-' || i == s.length() - 1) {
                    result += processing;
                    processing = 0;
                }
                lastOp = c;
                curNum = 0;
            }
        }
        return result;
    }


    public static int calculate2(String s) {
        int result = 0;
        // 存放当前正在计算的结果
        int processing = 0;
        // 存放当前正在parse的数
        int curNum = 0;
        char lastOp = '+';
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                curNum = curNum * 10 + s.charAt(i) - '0';
            }
            if (c == '+' || c == '-' || c == '(' || c == ')' || i == s.length() - 1) {
                switch (lastOp) {
                    case '+':
                        processing = processing + curNum;
                        break;
                    case '-':
                        processing = processing - curNum;
                        break;
                }
                result = result + processing;
                processing = 0;

                lastOp = c;
                if (lastOp == '(' || lastOp == ')') {
                    lastOp = '+';
                }
                curNum = 0;
            }
        }
        return result;
    }


    public static void main(String[] args) {
//        System.out.println(calculate("3+2*2"));
//        System.out.println(calculate("3/2"));
//        System.out.println(calculate("3+5/2"));

        System.out.println(calculate2("1+1"));
        System.out.println(calculate2("2-1+2"));
        System.out.println(calculate2("(1+(4+5+2)-3)+(6+8)"));
    }
}
