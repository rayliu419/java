package leetcode;

public class Calculation {

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
                sign = s.charAt(i) == '+' ? 1: -1;
                num = 0;
            }
            i++;
        }
        curResult += sign * num;
        return curResult;
    }

    public static void main(String[] args) {
        Calculation calculation = new Calculation();
        System.out.println(calculation.calculate1("1+2+3"));

        System.out.println(calculation.calculate1("1-2+3"));

        System.out.println(calculation.calculate1("-1+2+3"));

        System.out.println(calculation.calculate1("1+2-3"));
    }

}
