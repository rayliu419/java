package leetcode;

import infra.ListNode;
import org.junit.Test;

import static infra.ListNode.buildList;
import static org.junit.Assert.*;

public class LinkListTest {

    private final LinkList linkList = new LinkList();

    @Test
    public void testRemove() {
        ListNode l1 = new ListNode(6, null);
        ListNode l2 = new ListNode(5, l1);
        ListNode l3 = new ListNode(4, l2);
        ListNode l4 = new ListNode(6, l3);
        ListNode l5 = new ListNode(1, l4);

        ListNode res = linkList.removeElements(l5, 6);

        assertNotNull(res);
        assertEquals(1, res.val);
        assertEquals(4, res.next.val);
        assertEquals(5, res.next.next.val);
        assertNull(res.next.next.next);
    }


    @Test
    public void testRemoveElements_removeHead() {
        ListNode head = buildList(new int[]{1, 2, 3, 4});
        ListNode res = linkList.removeElements(head, 1);
        assertNotNull(res);
        assertEquals(2, res.val);
        assertEquals(3, res.next.val);
        assertEquals(4, res.next.next.val);
        assertNull(res.next.next.next);
    }

    @Test
    public void testRemoveElements_removeTail() {
        ListNode head = buildList(new int[]{1, 2, 3, 4});
        ListNode res = linkList.removeElements(head, 4);
        int[] expected = {1, 2, 3};
        ListNode cur = res;
        for (int val : expected) {
            assertEquals(val, cur.val);
            cur = cur.next;
        }
        assertNull(cur);
    }

    @Test
    public void testRemoveElements_consecutiveDup() {
        ListNode head = buildList(new int[]{1, 2, 2, 2, 3});
        ListNode res = linkList.removeElements(head, 2);
        int[] expected = {1, 3};
        ListNode cur = res;
        for (int val : expected) {
            assertEquals(val, cur.val);
            cur = cur.next;
        }
        assertNull(cur);
    }

    @Test
    public void testRemoveElements_valNotExist() {
        ListNode head = buildList(new int[]{1, 2, 3});
        ListNode res = linkList.removeElements(head, 4);
        int[] expected = {1, 2, 3};
        ListNode cur = res;
        for (int val : expected) {
            assertEquals(val, cur.val);
            cur = cur.next;
        }
        assertNull(cur);
    }

    @Test
    public void testRemoveElements_allRemoved() {
        ListNode head = buildList(new int[]{2, 2, 2});
        assertNull(linkList.removeElements(head, 2));
    }

    @Test
    public void testRemoveElements_null() {
        assertNull(linkList.removeElements(null, 1));
    }

    @Test
    public void testRemoveElements_singleMatch() {
        assertNull(linkList.removeElements(new ListNode(1), 1));
    }

    @Test
    public void testRemoveElements_singleNotMatch() {
        ListNode res = linkList.removeElements(new ListNode(1), 2);
        assertEquals(1, res.val);
        assertNull(res.next);
    }

    @Test
    public void testReverse() {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, null)));

        ListNode result = linkList.reverseList(head);

        assertEquals(3, result.val);
        assertEquals(2, result.next.val);
        assertEquals(1, result.next.next.val);
        assertNull(result.next.next.next);
    }


    @Test
    public void testMiddleNode_null() {
        assertNull(linkList.middleNode(null));
    }

    @Test
    public void testMiddleNode_singleNode() {
        ListNode head = new ListNode(1);
        ListNode mid = linkList.middleNode(head);
        assertEquals(1, mid.val);
    }

    @Test
    public void testMiddleNode_twoNodes() {
        ListNode head = buildList(new int[]{1, 2});
        ListNode mid = linkList.middleNode(head);
        assertEquals(2, mid.val);
    }

    @Test
    public void testMiddleNode_threeNodes() {
        ListNode head = buildList(new int[]{1, 2, 3});
        ListNode mid = linkList.middleNode(head);
        assertEquals(2, mid.val);
    }

    @Test
    public void testMiddleNode_fourNodes() {
        ListNode head = buildList(new int[]{1, 2, 3, 4});
        ListNode mid = linkList.middleNode(head);
        assertEquals(3, mid.val);
    }

    @Test
    public void testMiddleNode_fiveNodes() {
        ListNode head = buildList(new int[]{1, 2, 3, 4, 5});
        ListNode mid = linkList.middleNode(head);
        assertEquals(3, mid.val);
    }

    @Test
    public void testHasCycle_noCycle() {
        ListNode head = buildList(new int[]{1, 2, 3, 4, 5});
        assertFalse(linkList.hasCycle(head));
    }

    @Test
    public void testHasCycle_hasCycle() {
        ListNode head = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        head.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n2; // cycle back to n2
        assertTrue(linkList.hasCycle(head));
    }

    @Test
    public void testHasCycle_null() {
        assertFalse(linkList.hasCycle(null));
    }

    @Test
    public void testHasCycle_singleNodeNoCycle() {
        assertFalse(linkList.hasCycle(new ListNode(1)));
    }

    @Test
    public void testDetectCycle_noCycle() {
        ListNode head = buildList(new int[]{1, 2, 3});
        assertNull(linkList.detectCycle(head));
    }

    @Test
    public void testDetectCycle_hasCycle() {
        ListNode head = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        head.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n2; // cycle back to n2
        assertEquals(n2, linkList.detectCycle(head));
    }

    @Test
    public void testDetectCycle_entryAtHead() {
        ListNode head = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        head.next = n2;
        n2.next = n3;
        n3.next = head; // cycle back to head
        assertEquals(head, linkList.detectCycle(head));
    }

    @Test
    public void testDetectCycle_null() {
        assertNull(linkList.detectCycle(null));
    }

    @Test
    public void testMergeTwoLists_bothNull() {
        assertNull(linkList.mergeTwoLists(null, null));
    }

    @Test
    public void testMergeTwoLists_oneNull() {
        ListNode head = buildList(new int[]{1, 2, 3});
        ListNode res = linkList.mergeTwoLists(head, null);
        assertEquals(1, res.val);
        assertEquals(2, res.next.val);
        assertEquals(3, res.next.next.val);
        assertNull(res.next.next.next);

        ListNode res2 = linkList.mergeTwoLists(null, head);
        assertEquals(1, res2.val);
        assertEquals(2, res2.next.val);
        assertEquals(3, res2.next.next.val);
        assertNull(res2.next.next.next);
    }

    @Test
    public void testMergeTwoLists_interleaved() {
        ListNode l1 = buildList(new int[]{1, 3, 5});
        ListNode l2 = buildList(new int[]{2, 4, 6});
        ListNode res = linkList.mergeTwoLists(l1, l2);
        int[] expected = {1, 2, 3, 4, 5, 6};
        ListNode cur = res;
        for (int val : expected) {
            assertEquals(val, cur.val);
            cur = cur.next;
        }
        assertNull(cur);
    }

    @Test
    public void testMergeTwoLists_oneSmaller() {
        ListNode l1 = buildList(new int[]{1, 2, 3});
        ListNode l2 = buildList(new int[]{4, 5, 6});
        ListNode res = linkList.mergeTwoLists(l1, l2);
        int[] expected = {1, 2, 3, 4, 5, 6};
        ListNode cur = res;
        for (int val : expected) {
            assertEquals(val, cur.val);
            cur = cur.next;
        }
        assertNull(cur);
    }

    @Test
    public void testMergeTwoLists_withDuplicates() {
        ListNode l1 = buildList(new int[]{1, 2, 4});
        ListNode l2 = buildList(new int[]{2, 3, 4});
        ListNode res = linkList.mergeTwoLists(l1, l2);
        int[] expected = {1, 2, 2, 3, 4, 4};
        ListNode cur = res;
        for (int val : expected) {
            assertEquals(val, cur.val);
            cur = cur.next;
        }
        assertNull(cur);
    }

    @Test
    public void testDeleteDuplicates1_null() {
        assertNull(LinkList.deleteDuplicates1(null));
    }

    @Test
    public void testDeleteDuplicates1_single() {
        ListNode head = new ListNode(1);
        ListNode res = LinkList.deleteDuplicates1(head);
        assertEquals(1, res.val);
        assertNull(res.next);
    }

    @Test
    public void testDeleteDuplicates1_noDup() {
        ListNode head = buildList(new int[]{1, 2, 3});
        ListNode res = LinkList.deleteDuplicates1(head);
        int[] expected = {1, 2, 3};
        ListNode cur = res;
        for (int val : expected) {
            assertEquals(val, cur.val);
            cur = cur.next;
        }
        assertNull(cur);
    }

    @Test
    public void testDeleteDuplicates1_allDup() {
        ListNode head = buildList(new int[]{1, 1, 1});
        ListNode res = LinkList.deleteDuplicates1(head);
        assertEquals(1, res.val);
        assertNull(res.next);
    }

    @Test
    public void testDeleteDuplicates1_consecutiveDup() {
        ListNode head = buildList(new int[]{1, 1, 2, 3, 3, 4});
        ListNode res = LinkList.deleteDuplicates1(head);
        int[] expected = {1, 2, 3, 4};
        ListNode cur = res;
        for (int val : expected) {
            assertEquals(val, cur.val);
            cur = cur.next;
        }
        assertNull(cur);
    }

    @Test
    public void testDeleteDuplicates1_dupAtEnd() {
        ListNode head = buildList(new int[]{1, 2, 3, 3});
        ListNode res = LinkList.deleteDuplicates1(head);
        int[] expected = {1, 2, 3};
        ListNode cur = res;
        for (int val : expected) {
            assertEquals(val, cur.val);
            cur = cur.next;
        }
        assertNull(cur);
    }

    @Test
    public void testMiddleNode_sixNodes() {
        ListNode head = buildList(new int[]{1, 2, 3, 4, 5, 6});
        ListNode mid = linkList.middleNode(head);
        assertEquals(4, mid.val);
    }
}