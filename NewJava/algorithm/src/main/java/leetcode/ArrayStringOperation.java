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
     * 反转 int 数组的指定区间 [left, right]
     */
    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }

    /**
     * https://leetcode.com/problems/rotate-array/
     *
     * 将数组右移 k 步。
     *
     * 解法一：三次翻转（最优解，O(n) 时间，O(1) 空间）
     *
     * 核心洞察：
     *   右移 k 步就是把末尾 k 个元素（B 段）移到开头，前 n-k 个（A 段）后移，
     *   相当于把数组从 A|B 变成 B|A。
     *   利用 reverse 的可逆性：reverse(A+B) = reverse(B) + reverse(A)，
     *   再分别对两段 reverse 就能恢复内部顺序。
     *
     *   以 [1,2,3,4,5,6,7], k=3 为例：
     *     原始:     [1,2,3,4, 5,6,7]        A = [1,2,3,4], B = [5,6,7]
     *     ① 整体:   [7,6,5, 4,3,2,1]        reverse(A+B) = reverse(B) + reverse(A)
     *     ② 前 k:   [5,6,7, 4,3,2,1]        reverse(reverse(B)) = B
     *     ③ 后 n-k: [5,6,7, 1,2,3,4] ✓      reverse(reverse(A)) = A
     *
     * 解法二：额外数组（O(n) 时间，O(n) 空间）
     *   新数组每个元素放到 (i + k) % n 位置，最直观但占额外空间。
     *
     * 解法三：环状替换（O(n) 时间，O(1) 空间）
     *   从下标 0 开始，把 nums[i] 放到 (i+k)%n，取出目标值继续替换，
     *   直到回到起点。若一轮没覆盖所有元素（n 和 k 不互质），
     *   从下一个未处理位置继续。代码容易出 bug，面试不优先。
     */
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        if (n <= 1) return;
        k %= n;          // k 可能大于 n，取模归一
        if (k == 0) return;
        reverse(nums, 0, n - 1);  // 整体翻转
        reverse(nums, 0, k - 1);  // 翻转前 k 个
        reverse(nums, k, n - 1);  // 翻转后 n-k 个
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
