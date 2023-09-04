package datastructure;

public class MyHeap {

    /**
     * 输入一个数组，将其构造成最小堆
     * 从最后一个分支节点往前面构造，当构造到某个中间节点时，其左子树和右子树已经是最小堆了。
     *
     * @param array
     * @return
     */
    public static int[] buildMaxHeap(int[] array) {
        if (array.length == 0) {
            return null;
        }
        if (array.length == 1) {
            return array;
        }
        int lastNodeIndex = array.length - 1;
        // 最后一个分支节点的index
        int lastBranchNodeIndex = (lastNodeIndex - 1) / 2;
        for (int j = lastBranchNodeIndex; j >= 0; j--) {
            heapify(array, j, lastNodeIndex);
        }
        return array;
    }

    private static void heapify(int[] array, int curIndex, int maxNodeIndex) {
        int largestIndex = curIndex;
        int left = 2 * curIndex + 1;
        int right = 2 * curIndex + 2;
        if (left < maxNodeIndex && array[largestIndex] < array[left]) {
            largestIndex = left;
        }
        if (right < maxNodeIndex && array[largestIndex] < array[right]) {
            largestIndex = right;
        }
        if (largestIndex != curIndex) {
            swap(array, curIndex, largestIndex);
            heapify(array, largestIndex, maxNodeIndex);
        }
    }

    private static void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }


    /**
     * 输入一个数组，将其构造成最小堆
     * 始终保证当前插入的时已经是最小堆，每插入一个，就重构成最小堆。
     *
     * @param array
     * @return
     */
    public static int[] buildMaxHeap2(int[] array) {


        return array;
    }
}
