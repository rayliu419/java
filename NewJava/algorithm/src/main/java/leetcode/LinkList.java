package leetcode;

import infra.ListNode;

import java.util.List;

import static infra.ListNode.buildList;

public class LinkList {

    /**
     * https://leetcode.com/problems/reverse-linked-list/
     */
    public ListNode reverseList(ListNode head) {
        ListNode pre, current, next;
        pre = null;
        current = head;
        while (current != null) {
            next = current.next;
            current.next = pre;
            pre = current;
            current = next;
        }
        return pre;
    }

    /**
     * https://leetcode.com/problems/middle-of-the-linked-list/
     */
    public ListNode middleNode(ListNode head) {
        if (head == null) return null;
        ListNode slow, fast;
        slow = fast = head;
        while (fast!= null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * https://leetcode.com/problems/merge-two-sorted-lists/
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode();
        ListNode cur = dummy;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                cur.next = list1;
                list1 = list1.next;
            } else {
                cur.next = list2;
                list2 = list2.next;
            }
            cur = cur.next;
        }
        cur.next = list1 == null ? list2 : list1;
        return dummy.next;
    }

    /**
     * https://leetcode.com/problems/linked-list-cycle/
     * Detect if linked list has a cycle using Floyd's Tortoise and Hare.
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (slow == fast) return true;
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/linked-list-cycle-ii/
     * Find the entry point of the cycle, or null if no cycle.
     * After slow/fast meet, reset one pointer to head and move both at same speed.
     * They meet at the cycle entry point.
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null) return null;
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (slow == fast) {
                slow = head;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
    }

    /**
     * https://leetcode.com/problems/remove-linked-list-elements/
     *
     * 可能有连续的val
     */
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode();
        ListNode pre = dummy;
        ListNode cur = head;
        while (cur != null) {
            if (cur.val == val) {
                cur = cur.next;
            } else {
                pre.next = cur;
                pre = cur;
                cur = cur.next;
            }
        }
        // 非常容易漏掉
        pre.next = null;
        return dummy.next;
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
}
