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
     * 此题不熟练，看起来简单，其实不好做。
     * https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii
     * remove 有两个题型，一个是重复的保留一个，一个是重复的不保留任何。
     * 本题是不保留任何
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode();
        ListNode cur = head;
        ListNode pre = dummy;
        pre.next = head;
        while (cur != null) {
            while (cur.next != null && cur.next.val == cur.val) {
                cur = cur.next;
            }
            // 重点在这：
            if (pre.next == cur) {
                // pre.next 在循环过程中从未变过
                // 如果 cur 被移动过（有重复），pre.next != cur
                // 如果 cur 没被移动过（无重复），pre.next == cur
                pre.next = cur;
                pre = cur;
            } else {
                pre.next = cur.next;
            }
            cur = cur.next;
        }
        pre.next = null;
        return dummy.next;
    }
}
