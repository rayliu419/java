package leetcode;


/**
 * 主要是一些关于String和Array的操作题型
 *
 */
public class ArrayStringOperation {

    /**
     * 移除字符串中多余的空格，去掉首尾空格，单词间只保留一个空格。
     * 这个算法核心是能用简洁的方式覆盖各个edge case，没有引用很多flag标记位来操作
     * 边界覆盖：
     * - 空串 ""                          → ""
     * - 纯空格 "   "                     → ""
     * - 前导空格 "  abc"                 → "abc"
     * - 尾随空格 "abc   "                → "abc"
     * - 单词间多空格 "a   b   c"         → "a b c"
     * - 混合 "  hello   world  "         → "hello world"
     * - 单个字符且带空格 " a "           → "a"
     * - 无多余空格 "hello world"         → "hello world"
     */
    public String removeSpace(String s) {
        int slow = 0;
        char[] chars = s.toCharArray();
        /*
         * 虽然用了双层循环，但是循环的职责都很干净
         * 外层循环用来过滤空格。
         * 内存循环拷贝真实的单词
         */
        for (int fast = 0; fast < s.length(); fast++) {
            if (chars[fast] != ' ') {
                // 这个处理是经典
                if (slow != 0) {
                    chars[slow++] = ' ';
                }
                while (fast < s.length() && chars[fast] != ' ') {
                    chars[slow++] = chars[fast++];
                }
            }
        }
        return new String(chars, 0, slow);
    }

    /**
     * 注意：String 不可变，s.toCharArray() 拿到的是副本，修改副本不影响原字符串。
     * 所以必须传 char[] 进来直接操作。
     */
    private void revert(char[] chars, int left, int right) {
        while (left < right) {
            char c = chars[left];
            chars[left] = chars[right];
            chars[right] = c;
            left++;
            right--;
        }
    }

    /**
     * https://leetcode.com/problems/reverse-words-in-a-string/
     *
     */
    public String reverseWords(String s) {
        String cur = removeSpace(s);
        if (cur.isEmpty()) return "";
        char[] chars = cur.toCharArray();
        revert(chars, 0, chars.length - 1);
        int slow = 0;
        for (int fast = 0; fast < chars.length; fast++) {
            if (chars[fast] == ' ') {
                revert(chars, slow, fast - 1);
                slow = fast + 1;
            }
        }
        revert(chars, slow, chars.length - 1);
        return new String(chars);
    }

    /**
     * https://leetcode.com/problems/add-strings/
     *
     */
    public String addStrings(String num1, String num2) {
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        StringBuilder result = new StringBuilder();
        int carry = 0;
        while (i >= 0 || j >= 0 || carry > 0) {
            int n1 = i >= 0 ? num1.charAt(i) - '0' : 0;
            int n2 = j >= 0 ? num2.charAt(j) - '0' : 0;
            int cur = (n1 + n2 + carry) % 10;
            carry = (n1 + n2 + carry) / 10;
            result.append(cur);
            i--;
            j--;
        }
        return result.reverse().toString();
    }
}
