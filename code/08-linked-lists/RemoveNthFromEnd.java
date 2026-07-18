// LeetCode 19 — Remove Nth Node From End of List
// One pass with a two-pointer GAP: keep fast exactly n nodes ahead of slow.
// When fast falls off the end, slow sits just before the victim.
public class RemoveNthFromEnd {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);      // dummy head: removing the FIRST node becomes a normal case
        dummy.next = head;
        ListNode fast = dummy, slow = dummy;   // both start at the dummy, gap = 0
        for (int i = 0; i < n + 1; i++) {      // advance fast n+1 steps → gap of n+1 nodes
            fast = fast.next;                  // (n+1, not n, so slow lands BEFORE the node to delete)
        }
        while (fast != null) {                 // walk both until fast runs off the end
            fast = fast.next;
            slow = slow.next;                  // gap stays n+1 the whole way
        }
        slow.next = slow.next.next;            // slow is the predecessor — bypass the victim (GC reclaims it)
        return dummy.next;                     // head may have changed (n == list length) — dummy.next is always right
    }

    // --- test helpers ---
    static ListNode build(int... vals) {
        ListNode dummy = new ListNode(0), t = dummy;
        for (int v : vals) { t.next = new ListNode(v); t = t.next; }
        return dummy.next;
    }
    static String render(ListNode n) {
        StringBuilder sb = new StringBuilder();
        for (; n != null; n = n.next) sb.append(n.val).append(',');
        return sb.toString();
    }

    public static void main(String[] args) {
        assert render(removeNthFromEnd(build(1,2,3,4,5), 2)).equals("1,2,3,5,");
        assert render(removeNthFromEnd(build(1), 1)).equals("");
        assert render(removeNthFromEnd(build(1,2), 2)).equals("2,");
        assert render(removeNthFromEnd(build(1,2), 1)).equals("1,");
        System.out.println("RemoveNthFromEnd: OK");
    }
}
