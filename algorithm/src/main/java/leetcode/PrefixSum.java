package leetcode;


/**
 * 一维前缀和
 * 定义对于一个数组a的前缀和数组s，s[i] = a[1]+a[2]+...+a[i].
 * <p>
 * 二维前缀和
 * 设s[i][j]表示所有a[i'][j']的和。（1≤i'≤i,1≤j'≤j）
 */
public class PrefixSum {

    public static int subArraySum(int[] nums, int k) {
        int[] prefixSum = new int[nums.length + 1];
        int count = 0;
        // prefixSum[i]表示前i个元素的和。prefixSum[0]代表前0个元素
        prefixSum[0] = 0;
        for (int i = 1; i <= nums.length; i++) {
            prefixSum[i] = prefixSum[i-1] + nums[i - 1];
        }
        // 计算长度为1-n以上的子数组是否为k
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length + 1; j++) {
                // 设置prefixSum[0]的好处在于将长度为1-n的计算都统一起来了
                if (prefixSum[j] - prefixSum[i] == k) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[] test1 = new int[]{1, 1, 1};
        System.out.println(subArraySum(test1, 2));

        int[] test2 = new int[]{1,2,3};
        System.out.println(subArraySum(test2, 3));
    }

}
