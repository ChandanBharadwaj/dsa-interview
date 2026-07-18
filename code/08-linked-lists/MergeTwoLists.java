// LeetCode 21 — Merge Two Sorted Lists
// The dummy-head technique in its purest form. Also the merge step of merge sort (page 06).
public class MergeTwoLists {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);      // fake node BEFORE the real head — kills all "is this the first node?" special cases
        ListNode tail = dummy;                 // tail always points at the last node of the merged result
        while (l1 != null && l2 != null) {     // while both lists still have candidates
            if (l1.val <= l2.val) {            // <= keeps the merge STABLE (ties keep l1's order)
                tail.next = l1;                // splice the smaller node onto the result
                l1 = l1.next;                  // advance only the list we consumed from
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;                  // result grew by one — move tail forward
        }
        tail.next = (l1 != null) ? l1 : l2;    // one list is empty; the other is sorted — attach it whole (no loop needed)
        return dummy.next;                     // the real head lives just after the dummy
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
        assert render(mergeTwoLists(build(1,2,4), build(1,3,4))).equals("1,1,2,3,4,4,");
        assert render(mergeTwoLists(build(), build())).equals("");
        assert render(mergeTwoLists(build(), build(0))).equals("0,");
        assert render(mergeTwoLists(build(5), build(1,2))).equals("1,2,5,");
        System.out.println("MergeTwoLists: OK");
    }
}
