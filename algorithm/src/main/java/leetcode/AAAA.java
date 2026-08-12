package leetcode;


public class AAAA {

    // 只包含'+', '-'，没有括号和'*', "/"
    int calculate(String s) {
        char sign = '+';
        int result = 0;
        int num = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            }
            if (c == '+' || c == '-' || i == s.length() - 1) {
                if (sign == '+') {
                    result = result + num;
                    sign = c;
                    num = 0;
                } else if (sign == '-') {
                    result = result - num;
                    sign = c;
                    num = 0;
                }
            }
        }
        return result;
    }

    int calculate2(String s) {
        int num = 0;
        int result = 0;
        int sign = '+';
        int subSum = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            }
            if (c == '+' || c == '-' || c == '*' || c == '/' || i == s.length() - 1) {

            }
        }
    }
}
