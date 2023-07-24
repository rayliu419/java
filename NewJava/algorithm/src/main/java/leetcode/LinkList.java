package leetcode;

public class LinkList {

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
        while(cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        head.next = null;
        return pre;
    }

}
