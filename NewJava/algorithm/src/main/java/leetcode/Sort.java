package leetcode;

import java.util.Arrays;
import java.util.Random;

public class Sort {

    public void quickSort(int[] nums) {
        doQuickSort(nums, 0, nums.length - 1);
    }

    private void doQuickSort(int[] nums, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(nums, low, high);
            doQuickSort(nums, low, pivotIndex - 1);
            doQuickSort(nums, pivotIndex + 1, high);
        }
    }

    /**
     *  1. 初始i = low - 1，i表示现在小于pivot的
     *  2. for循环从[low, high)
     *  3. 先i++，给交换腾出位置;
     *  这是个最简洁且精妙的写法，尤其是初始i=low-1的赋值，和后续先i++，再交换的写法
     *
     * @param nums
     * @param low
     * @param high
     * @return
     */
    private int partition(int[] nums, int low, int high) {
        Random random = new Random();
        int index = random.nextInt(low, high + 1);
        // 把标杆放到high的位置
        swap(nums, index, high);
        int pivot = nums[high];
        // i设置为low-1，考虑到最后可能pivot是最小的数
        int i = low - 1;
        // [low, i] 放的小于pivot的，j是当前扫描的, [i+1, j]放的是大于pivot的数
        for (int j = low; j < high; j++) {
            // j不需要到high，因为high存放的是pivot
            if (nums[j] <= pivot) {
                i++;
                swap(nums, i, j);
            }
        }
        swap(nums, i + 1, high);
        return i + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


    public static void main(String[] args) {
        int[] array = new int[]{3, 2, 1};
        new Sort().quickSort(array);
        Arrays.stream(array).forEach(e -> System.out.println(e));

        int[] array2 = new int[]{3, 2, 1, 4};
        new Sort().quickSort(array2);
        Arrays.stream(array2).forEach(e -> System.out.println(e));
    }
}
