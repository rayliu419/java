package main.leetcode;

public class RemoveLinkedListElements {

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

}
