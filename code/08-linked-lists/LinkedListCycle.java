import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode 141 + 142 — Linked List Cycle I & II. Seen-set variation,
 * Floyd's fast/slow detection, and the phase-2 reset that finds the
 * cycle's entrance. Mirrors pages/08-linked-lists.html.
 */
public class LinkedListCycle {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static boolean hasCycleSet(ListNode head) {
        Set<ListNode> seen = new HashSet<>();   // identity of NODES, not values
        for (ListNode cur = head; cur != null; cur = cur.next)
            if (!seen.add(cur)) return true;      // revisited the same object -> cycle
        return false;
    }

    public static boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {   // fast does all the null-checking
            slow = slow.next;                          // tortoise: 1 step
            fast = fast.next.next;                     // hare: 2 steps
            if (slow == fast) return true;             // same OBJECT -> they're stuck in a loop
        }
        return false;                                  // fast found the end -> no cycle
    }

    public static ListNode detectCycle(ListNode head) {
        // Phase 1: standard fast/slow until they meet inside the cycle.
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                // Phase 2: restart one pointer at head; walk BOTH at speed 1.
                // They meet exactly at the cycle's entrance.
                ListNode p = head;
                while (p != slow) {
                    p = p.next;
                    slow = slow.next;
                }
                return p;
            }
        }
        return null;                           // fast hit the end -> acyclic
    }

    public static void main(String[] args) {
        // 3 -> 2 -> 0 -> -4 -> (back to 2)
        ListNode a = new ListNode(3), b = new ListNode(2), c = new ListNode(0), d = new ListNode(-4);
        a.next = b; b.next = c; c.next = d; d.next = b;
        assert hasCycle(a) && hasCycleSet(a);
        assert detectCycle(a) == b;

        ListNode x = new ListNode(1); x.next = new ListNode(2);
        assert !hasCycle(x) && detectCycle(x) == null;
        assert !hasCycle(null);
        System.out.println("LinkedListCycle: OK");
    }
}
