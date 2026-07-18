// LeetCode 86 — Partition List
public class PartitionList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    // Two dummy-headed builder lists — smalls and bigs — then stitch.
    // Stability (original relative order) comes free from append-only building.
    public static ListNode partition(ListNode head, int x) {
        ListNode smallHead = new ListNode(0), small = smallHead;
        ListNode bigHead = new ListNode(0), big = bigHead;
        for (ListNode n = head; n != null; n = n.next) {
            if (n.val < x) {
                small.next = n;         // append to the smalls
                small = n;
            } else {
                big.next = n;           // append to the bigs
                big = n;
            }
        }
        big.next = null;                // CRITICAL: cut the old tail link
        small.next = bigHead.next;      // stitch smalls -> bigs
        return smallHead.next;
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
        assert render(partition(build(1, 4, 3, 2, 5, 2), 3)).equals("1,2,2,4,3,5,");
        assert render(partition(build(2, 1), 2)).equals("1,2,");
        assert render(partition(build(1), 0)).equals("1,");
        System.out.println("PartitionList: OK");
    }
}
