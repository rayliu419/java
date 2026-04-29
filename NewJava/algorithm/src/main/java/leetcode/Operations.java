package leetcode;

// https://leetcode.com/problems/final-value-of-variable-after-performing-operations/
public class Operations {

    public int finalValueAfterOperations(String[] operations) {
        int count = 0;
        for (String s : operations) {
            count += calculate(s);
        }
        return count;
    }

    private int calculate(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '+') {
                return 1;
            } else if (s.charAt(i) == '-') {
                return -1;
            }
        }
        // never happen.
        return 0;
    }

    public static void main(String[] args) {
        String[] case1 = new String[]{"--X","X++","X++"};
        String[] case2 = new String[]{"++X","++X","X++"};
        String[] case3 = new String[]{"X++","++X","--X","X--"};

        Operations operations = new Operations();
        System.out.println(operations.finalValueAfterOperations(case1));
        System.out.println(operations.finalValueAfterOperations(case2));
        System.out.println(operations.finalValueAfterOperations(case3));

    }

}
