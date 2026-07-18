// LeetCode 24 — Swap Nodes in Pairs
public class SwapNodesInPairs {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    // A dummy head + one "prev" pointer; rewire two nodes at a time.
    // Draw the three arrows before coding — the order matters.
    public static ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;
            ListNode second = first.next;
            first.next = second.next;   // first jumps over second
            second.next = first;        // second points back at first
            prev.next = second;         // the pair's new head hooks in
            prev = first;               // first is now the pair's tail
        }
        return dummy.next;
    }

    private static ListNode build(int... vals) {
        ListNode dummy = new ListNode(0), t = dummy;
        for (int v : vals) { t.next = new ListNode(v); t = t.next; }
        return dummy.next;
    }

    private static String render(ListNode n) {
        StringBuilder sb = new StringBuilder();
        for (; n != null; n = n.next) sb.append(n.val).append(',');
        return sb.toString();
    }

    public static void main(String[] args) {
        assert render(swapPairs(build(1, 2, 3, 4))).equals("2,1,4,3,");
        assert render(swapPairs(build(1))).equals("1,");
        assert render(swapPairs(build(1, 2, 3))).equals("2,1,3,");
        assert swapPairs(null) == null;
        System.out.println("SwapNodesInPairs: OK");
    }
}
