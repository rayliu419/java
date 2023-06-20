package leetcode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 这一类题目中都是通过k个有序合并的思路。
 *
 * 1. 普通的合并k个有序链表
 * 2. 两个有序数组中查找pair最小的k组
 * 3. 有序矩阵中查找第k大的数。
 *
 * #2, #3还可以采用二分法来找，其中#2其实有点类似于快排分治中用的查找第k大的数
 *
 */
public class MergeKSortedList {

    public static class Node {

        int value;

        Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    /**
     * 经典的merge k 个有序的链表
     *
     * @param sortedList
     * @return
     */
    public static List<Integer> mergeKSortedList(List<Node> sortedList) {
        PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.value - o2.value;
            }
        });
        List<Integer> result = new ArrayList<>();
        // head in
        for (int i = 0; i < sortedList.size(); i++) {
            pq.add(sortedList.get(i));
        }
        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            Node next = cur.next;
            if (next != null) {
                pq.add(next);
            }
            pq.add(cur);
        }
        return result;
    }

    public static void testMergeKSortedList() {
        Node head11 = new Node(1, new Node(3, new Node(5, new Node(7, null))));
        Node head12 = new Node(2, new Node(4, new Node(6, new Node(8, null))));
        Node head13 = new Node(10, new Node(11, null));
        Node head14 = new Node(9, null);
        List<Node> lists = new ArrayList<>();
        lists.add(head11);
        lists.add(head12);
        lists.add(head13);
        lists.add(head14);

        List<Integer> result = mergeKSortedList(lists);
        result.forEach(e -> {
            System.out.println(e);
        });
    }

    /**
     * 两个有序数组，返回两个有序数组之和从小到大的k个对
     * <p>
     * 等同于nums[].length个有序数组求前k个数
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> result = new ArrayList<>();
        // int[]中存了3个值，第一个和第二个值是为了存位置用来推断下一个数。第三个数用来计算排序
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[2] - o2[2];
            }
        });
        // head in
        for (int i = 0; i < nums1.length; i++) {
            pq.add(new int[]{i, 0, nums1[i] + nums2[0]});
        }
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int row = cur[0];
            int col = cur[1];
            result.add(List.of(nums1[row], nums2[col]));
            if (result.size() == k) {
                return result;
            }
            if (col + 1 < nums2.length) {
                int[] next = new int[]{row, col + 1, nums1[row] + nums2[col + 1]};
                pq.add(next);
            }
        }
        return result;
    }

    /**
     * 在一个行和列都有序的矩阵中查找第K大的元素
     *
     * 等同于M个有序数组中查找第K大的元素
     *
     * @param matrix
     * @param k
     * @return
     */
    public static int findKthInMatrix(int[][] matrix, int k) {
        // int[]中存了3个值，第一个和第二个值是为了存位置用来推断下一个数。第三个数用来计算排序
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[2] - o2[2];
            }
        });
        // head in
        for (int i = 0; i < matrix.length; i++) {
            pq.add(new int[]{i, 0, matrix[i][0]});
        }
        int count = 0;
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int row = cur[0];
            int col = cur[1];
            count++;
            if (count == k) {
                return cur[2];
            }
            if (col + 1 < matrix[0].length) {
                int[] next = new int[]{row, col + 1, matrix[row][col + 1]};
                pq.add(next);
            }
        }
        return -1;
    }

}
