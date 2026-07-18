// LeetCode 61 — Rotate List
public class RotateList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    // Close the list into a ring, then cut it at the right place.
    // Rotating right by k = new head at index n − (k % n).
    public static ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null) return head;

        int n = 1;
        ListNode tail = head;
        while (tail.next != null) {     // find length and tail together
            tail = tail.next;
            n++;
        }
        k %= n;                          // k may far exceed the length
        if (k == 0) return head;

        tail.next = head;                // 1. close the ring
        ListNode newTail = head;
        for (int i = 0; i < n - k - 1; i++)   // 2. walk to the cut point
            newTail = newTail.next;
        ListNode newHead = newTail.next; // 3. cut
        newTail.next = null;
        return newHead;
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
        assert render(rotateRight(build(1, 2, 3, 4, 5), 2)).equals("4,5,1,2,3,");
        assert render(rotateRight(build(0, 1, 2), 4)).equals("2,0,1,");
        assert render(rotateRight(build(1), 99)).equals("1,");
        assert render(rotateRight(build(1, 2), 2)).equals("1,2,");
        System.out.println("RotateList: OK");
    }
}
