/**
 * LeetCode 25 — Reverse Nodes in k-Group. Per-block three-pointer flip
 * with pre-stitched boundaries. Mirrors pages/08-linked-lists.html.
 */
public class ReverseKGroup {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode groupPrev = dummy;            // node just BEFORE the current group

        while (true) {
            // 1) Find the k-th node of this group; if fewer than k remain, done.
            ListNode kth = groupPrev;
            for (int i = 0; i < k && kth != null; i++) kth = kth.next;
            if (kth == null) break;
            ListNode groupNext = kth.next;     // first node AFTER the group

            // 2) Reverse the group in place. Standard flip, but prev starts at
            //    groupNext so the group's old head ends up pointing PAST the group.
            ListNode prev = groupNext;
            ListNode curr = groupPrev.next;    // group's first node (will become its last)
            while (curr != groupNext) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }

            // 3) Stitch: groupPrev must now point at the NEW group head (the old kth).
            ListNode oldGroupHead = groupPrev.next;   // now the group's tail
            groupPrev.next = kth;
            groupPrev = oldGroupHead;          // advance: tail of this group precedes the next
        }
        return dummy.next;
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
        assert equals(reverseKGroup(build(5), 2), 2, 1, 4, 3, 5);
        assert equals(reverseKGroup(build(5), 3), 3, 2, 1, 4, 5);
        assert equals(reverseKGroup(build(4), 1), 1, 2, 3, 4);
        assert equals(reverseKGroup(build(3), 3), 3, 2, 1);
        System.out.println("ReverseKGroup: OK");
    }
}
