package leetcode;

import leetcode.ListNode;
import leetcode.RemoveLinkedListElements;
import org.junit.Test;

import static leetcode.ListNode.printList;

public class RemoveLinkedListElementsTest {

    @Test
    public void testRemove() {
        RemoveLinkedListElements removeLinkedListElements = new RemoveLinkedListElements();
        ListNode l1 = new ListNode(6, null);
        ListNode l2 = new ListNode(5, l1);
        ListNode l3 = new ListNode(4, l2);
        ListNode l4 = new ListNode(6, l3);
        ListNode l5 = new ListNode(1, l4);

        ListNode res = removeLinkedListElements.removeElements(l5, 6);

        printList(res);
    }

}