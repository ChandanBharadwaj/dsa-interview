/**
 * LeetCode 143 — Reorder List. Middle (fast/slow) + reverse second half
 * + interleave. Mirrors pages/08-linked-lists.html.
 */
public class ReorderList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        // Step 1: find the middle (fast/slow) — slow ends at the split point.
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode second = slow.next;
        slow.next = null;                      // cut: first half ends at slow

        // Step 2: reverse the second half (the three-pointer flip).
        ListNode prev = null;
        while (second != null) {
            ListNode next = second.next;
            second.next = prev;
            prev = second;
            second = next;
        }
        second = prev;                         // head of the reversed back half

        // Step 3: interleave — alternate one node from each half.
        ListNode first = head;
        while (second != null) {               // second is the shorter (or equal) half
            ListNode n1 = first.next, n2 = second.next;   // save both continuations
            first.next = second;               // L0 -> Ln
            second.next = n1;                  // Ln -> L1
            first = n1;                        // advance in the front half
            second = n2;                       // advance in the back half
        }
    }

    private static ListNode build(int n) {
        ListNode head = new ListNode(1), t = head;
        for (int v = 2; v <= n; v++) { t.next = new ListNode(v); t = t.next; }
        return head;
    }

    private static boolean equals(ListNode head, int... vals) {
        for (int v : vals) {
            if (head == null || head.val != v) return false;
            head = head.next;
        }
        return head == null;
    }

    public static void main(String[] args) {
        ListNode a = build(4);
        reorderList(a);
        assert equals(a, 1, 4, 2, 3);
        ListNode b = build(5);
        reorderList(b);
        assert equals(b, 1, 5, 2, 4, 3);
        ListNode c = build(1);
        reorderList(c);
        assert equals(c, 1);
        System.out.println("ReorderList: OK");
    }
}
