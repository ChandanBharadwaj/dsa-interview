// LeetCode 83 — Remove Duplicates from Sorted List
public class RemoveDuplicatesSortedList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    // Sorted input means duplicates are adjacent: compare each node with its
    // successor and splice equal ones out.
    public static ListNode deleteDuplicates(ListNode head) {
        ListNode curr = head;
        while (curr != null && curr.next != null) {
            if (curr.next.val == curr.val)
                curr.next = curr.next.next;   // skip the duplicate; DON'T advance —
            else                              // the next node might also be equal
                curr = curr.next;
        }
        return head;
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
        assert render(deleteDuplicates(build(1, 1, 2))).equals("1,2,");
        assert render(deleteDuplicates(build(1, 1, 2, 3, 3))).equals("1,2,3,");
        assert render(deleteDuplicates(build(1, 1, 1))).equals("1,");
        assert deleteDuplicates(null) == null;
        System.out.println("RemoveDuplicatesSortedList: OK");
    }
}
