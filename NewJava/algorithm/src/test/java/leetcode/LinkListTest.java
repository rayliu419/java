package leetcode;

import org.junit.Test;

import java.util.List;

import static leetcode.ListNode.printList;

public class LinkListTest {

    @Test
    public void testRemove() {
        LinkList linkList = new LinkList();
        ListNode l1 = new ListNode(6, null);
        ListNode l2 = new ListNode(5, l1);
        ListNode l3 = new ListNode(4, l2);
        ListNode l4 = new ListNode(6, l3);
        ListNode l5 = new ListNode(1, l4);

        ListNode res = linkList.removeElements(l5, 6);

        printList(res);
    }


    @Test
    public void testReverse() {
        LinkList linkList = new LinkList();
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, null)));

        ListNode result = linkList.reverseList(head);

        printList(result);
    }

}