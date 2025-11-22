package leetcode;


/**
 * 如同像指针处理一样，表达式求值相当于都在最前面添加了额外的表达式来简化算法。
 *  1+2+3 -> 0+1+2+3; lastOp = +, result = 0;
 *  -3+5*2 -> 0+(-3)+5*2; lastOp = +, result = 0;
 *
 */
public class Calculation {

    //简单加减不带括号
    private int calculate1(String s) {
        int curResult = 0;
        int sign = 1;
        int i = 0;
        int num = 0;
        while (i < s.length()) {
            if (Character.isDigit(s.charAt(i))) {
                num = num * 10 + s.charAt(i) - '0';
            } else {
                curResult = curResult + sign * num;
                sign = s.charAt(i) == '+' ? 1 : -1;
                num = 0;
            }
            i++;
        }
        curResult += sign * num;
        return curResult;
    }
    

    /**
     * TODO: 依然不熟练，还需要单独练习。这里对curResult和num的赋值的时机有点模糊。
     * 不带括号的加减乘除，也可以解只有加减的，模版是一样的。
     */
    private int calculate2(String s) {
        // 存放当前阶段计算的结果
        int result = 0;
        // 存放被+-/隔开的中间运算结果。例如 2-3*4/2+1，processing在计算完3*4/2才会合并到result里
        int curResult = 0;
        // 存放当前正在parse的数
        int num = 0;
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
                num = num * 10 + s.charAt(i) - '0';
            }
            // 由于上面没有用continue，所以下面要单独判断字符的情况
            if (c == '+' || c == '-' || c == '*' || c == '/' || i == s.length() - 1) {
                /**
                 *  这个逻辑是把+，-当成数字的符号一起处理了。curResult可能为负。
                 *  考虑1-5/2的情况，处理到/时，上一个op是-，curResult=0, num=5，
                 *  所以下面的处理把curResult变成了-5。后序的处理一直带着这个符号在做乘除。最后只需要result+=curResult。
                 */
                switch (lastOp) {
                    case '+':
                        curResult = curResult + num;
                        break;
                    case '-':
                        curResult = curResult - num;
                        break;
                    case '*':
                        curResult = curResult * num;
                        break;
                    case '/':
                        curResult = curResult / num;
                        break;
                }
                // 如果是+，-或者最后一个数，要把curResult合并到result，curResult清零
                if (c == '+' || c == '-' || i == s.length() - 1) {
                    result += curResult;
                    curResult = 0;
                }
                // 只要是计算符号，都需要更新curNum和lastOp。
                lastOp = c;
                num = 0;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Calculation calculation = new Calculation();
        System.out.println(calculation.calculate1("1+2+3"));
        System.out.println(calculation.calculate1("1-2+3"));
        System.out.println(calculation.calculate1("-1+2+3"));
        System.out.println(calculation.calculate1("1+2-3"));

        System.out.println("===========================================");

        System.out.println(calculation.calculate2("1+2+3"));
        System.out.println(calculation.calculate2("1-2+3"));
        System.out.println(calculation.calculate2("-1+2+3"));
        System.out.println(calculation.calculate2("1+2-3"));
        System.out.println(calculation.calculate2("3-2*5/2+4-3"));

    }
}


