package leetcode;

import java.util.Arrays;
import java.util.Random;

public class Kth {
    /**
     * @return 枢轴最终所在位置
     */
    int partition(int[] array, int low, int high) {
        Random random = new Random();
        // nextInt(n)返回[0, n)，所以需要+1来包含high本身
        int randomInt = random.nextInt(high - low + 1);
        int pivotIndex = low + randomInt;

//     【为何将pivot放到high位置而不是low位置？】
//      将pivot放到high位置后，遍历范围变成[index=low, high)，不需要处理pivot本身，逻辑更简洁。
//      如果把pivot放在low位置：
//      需要额外处理pivot本身（防止自己和自己交换或比较）
//      遍历范围变成[low+1, high]，结束时还需要把pivot放回正确位置，边界条件更复杂，容易出错
        swap(array, pivotIndex, high);
        int pivotValue = array[high];

        // i记录<=枢轴元素的边界，初始化为low-1
        // 每次发现小于等于枢轴的元素，就将i向右移动一位并交换到该位置
        int i = low - 1;

        // 遍历[low, high)范围，不包含枢轴本身（已在high位置）
        for (int index = low; index < high; index++) {
            if (array[index] <= pivotValue) {
                i++;
                swap(array, i, index);
            }
        }

        // 将枢轴放到正确位置：所有<=枢轴的元素之后
        swap(array, i + 1, high);
        return i + 1;
    }

    void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void quickSortHelper(int[] array, int low, int high) {
        if (low < high) {
            int k = partition(array, low, high);
            quickSortHelper(array, low, k - 1);
            quickSortHelper(array, k + 1, high);
        }
    }

    void quickSort(int[] array) {
        quickSortHelper(array, 0, array.length - 1);
    }

    // ===============================================================================================================

    // 快速选择：基于partition缩小搜索范围，平均O(n)，最坏O(n^2)
    // k从1开始（外部调用语义：第1小、第2小...），内部递归时k始终表示"在当前[low,high]子区间内的相对排名"
    int findKthElement(int[] array, int k) {
        if (k <= 0) {
            return -1;
        }
        if (array.length < k ) {
            return -1;
        }
        return findKthElementHelper(array, 0, array.length - 1, k);
    }

    // k的含义：在当前子区间[low, high]中，找第k小的元素（1-based相对排名）
    // 【关键】k必须是相对排名而非全局排名：partition返回的是绝对索引p，
    //   用 rank = p - low + 1 将其转为当前区间内的相对排名，才能与k正确比较。
    //   若直接用 p + 1 == k，当递归进入右半边后k已减去左侧元素数，
    //   但p+1仍是全局排名，两者语义不匹配，会导致死递归或错误结果。
    private int findKthElementHelper(int[] array, int low, int high, int k) {
        if (low > high) {
            return -1;
        }
        int p = partition(array, low, high);
        int rank = p - low + 1; // 将绝对索引p转为当前子区间的相对排名(1-based)
        if (rank == k) {
            // 枢轴恰好是第k小
            return array[p];
        }
        if (rank > k) {
            // 第k小在左半边，k不变（左侧元素排名不变）
            return findKthElementHelper(array, low, p - 1, k);
        }
        // 第k小在右半边，k减去左侧已被排除的元素数（含枢轴本身）
        return findKthElementHelper(array, p + 1, high, k - rank);
    }
}
