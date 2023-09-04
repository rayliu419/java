package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            // i如果相同，其他的数也会是相同的，忽略
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum == 0) {
                    ans.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    // j如果是重复的，就过滤掉
                    while (j < k && nums[j] == nums[j + 1]) {
                        j++;
                    }
                    // k同样
                    while (j < k && nums[k] == nums[k - 1]) {
                        k--;
                    }
                    j++;
                    k--;
                } else if (sum < 0) {
                    // Sum is less than zero, increment j to increase the sum
                    j++;
                } else {
                    // Sum is greater than zero, decrement k to decrease the sum
                    k--;
                }
            }
        }
        return ans;
    }
}
