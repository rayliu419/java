package leetcode;

import infra.ListNode;

import static infra.ListNode.buildList;

public class LinkList {


    public static void testDeleteDuplicates() {
        int[] a = new int[]{1, 1, 2, 3, 3};
        ListNode head1 = buildList(a);
        ListNode result = deleteDuplicates1(head1);
        ListNode.printList(result);
    }

    public static void main(String[] args) {
//        testDeleteDuplicates();
        testDeleteDuplicates2();
    }

    public static void testDeleteDuplicates2() {
        int[] a = new int[]{1, 1, 2, 3, 3};
        ListNode head1 = buildList(a);
        ListNode result = deleteDuplicates2(head1);
        ListNode.printList(result);
    }
    /**
     * https://leetcode.com/problems/remove-duplicates-from-sorted-list
     *
     * @param head
     * @return
     */
    public static ListNode deleteDuplicates1(ListNode head) {
        ListNode cur = head;
        ListNode result = head;
        ListNode next = null;
        while (cur != null) {
            next = cur.next;
            while (next != null) {
                if (next.val == cur.val) {
                    next = next.next;
                } else {
                    cur.next = next;
                    cur = next;
                }
            }
            if (next == null) {
                cur.next = null;
                break;
            }
        }
        return result;
    }

    /**
     * https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii
     * TODO: has bug.
     * @param head
     * @return
     */
    public static ListNode deleteDuplicates2(ListNode head) {
        ListNode result = new ListNode(Integer.MIN_VALUE);
        ListNode finalResult = result;
        ListNode cur = head;
        boolean dup = false;
        while (cur != null) {
            int val = cur.val;
            while (cur.next != null && cur.next.val == val) {
                dup = true;
                cur = cur.next;
            }
            if (!dup) {
                result.next = cur;
                result = cur;
            }
            // 1. cur.next.val != cur.val -> 下一个cur待判断
            // 2. cur.next = null. -> 到最后节点
            cur = cur.next;
            dup = false;
        }
        result.next = null;

        return finalResult.next;
    }


    public ListNode removeElements(ListNode head, int val) {
        ListNode newHeader = new ListNode();
        ListNode temp = newHeader;
        ListNode cur = head;
        while (cur != null) {
            if (cur.val != val) {
                temp.next = cur;
                temp = cur;
            }
            cur = cur.next;
        }
        temp.next = null;
        return newHeader.next;
    }

    /**
     * 反转链表
     *
     * @param head
     */
    public ListNode reverseList(ListNode head) {
        ListNode temp = new ListNode(Integer.MIN_VALUE, head);
        ListNode pre = temp;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        head.next = null;
        return pre;
    }

}
