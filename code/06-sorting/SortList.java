/**
 * LeetCode 148 — Sort List. Merge sort on a singly linked list:
 * fast/slow split, recursive sort, merge by relinking.
 * Mirrors pages/06-sorting.html.
 */
public class SortList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;   // 0/1 nodes: sorted

        // 1) Split in half: slow ends at the middle (fast moves 2x).
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode second = slow.next;
        slow.next = null;                     // cut the list in two

        // 2) Recursively sort each half.
        ListNode a = sortList(head);
        ListNode b = sortList(second);

        // 3) Merge two sorted lists by relinking nodes (no allocation).
        ListNode dummy = new ListNode(0), tail = dummy;
        while (a != null && b != null) {
            if (a.val <= b.val) { tail.next = a; a = a.next; }
            else                { tail.next = b; b = b.next; }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;      // append the leftover run
        return dummy.next;
    }

    private static ListNode build(int... vals) {
        ListNode dummy = new ListNode(0), t = dummy;
        for (int v : vals) { t.next = new ListNode(v); t = t.next; }
        return dummy.next;
    }

    private static boolean equals(ListNode head, int... vals) {
        for (int v : vals) {
            if (head == null || head.val != v) return false;
            head = head.next;
        }
        return head == null;
    }

    public static void main(String[] args) {
        assert equals(sortList(build(4, 2, 1, 3)), 1, 2, 3, 4);
        assert equals(sortList(build(-1, 5, 3, 4, 0)), -1, 0, 3, 4, 5);
        assert equals(sortList(build(1)), 1);
        assert sortList(null) == null;
        System.out.println("SortList: OK");
    }
}
