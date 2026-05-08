package infra;

import lombok.Data;

@Data
public class ListNode {

    public int val;
    public ListNode next;


    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static void printList(ListNode head) {
        ListNode cur = head;
        while (cur != null) {
            System.out.print(cur.val + "-->");
            cur = cur.next;
        }
        System.out.print("null");
    }

    public static ListNode buildList(int[] a) {
        ListNode head = new ListNode(a[0]);
        ListNode cur = head;
        for (int i = 1; i < a.length; i++) {
            ListNode newNode = new ListNode(a[i]);
            cur.next = newNode;
            cur = cur.next;
        }
        return head;
    }
}
