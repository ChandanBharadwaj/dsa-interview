// LeetCode 160 — Intersection of Two Linked Lists
public class IntersectionTwoLists {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    // The pointer-switch trick: walk a then b's list, walk b then a's list.
    // Both pointers travel lenA + lenB steps, so they align on the shared
    // tail — or hit null together when there is none.
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA, b = headB;
        while (a != b) {
            a = (a == null) ? headB : a.next;   // exhausted A? restart on B
            b = (b == null) ? headA : b.next;   // exhausted B? restart on A
        }
        return a;                                // intersection node, or null
    }

    public static void main(String[] args) {
        // Build: A = 4->1->8->4->5, B = 5->6->1->(8->4->5 shared)
        ListNode shared = new ListNode(8);
        shared.next = new ListNode(4);
        shared.next.next = new ListNode(5);
        ListNode a = new ListNode(4);
        a.next = new ListNode(1);
        a.next.next = shared;
        ListNode b = new ListNode(5);
        b.next = new ListNode(6);
        b.next.next = new ListNode(1);
        b.next.next.next = shared;
        assert getIntersectionNode(a, b) == shared;

        ListNode c = new ListNode(2);            // disjoint lists -> null
        c.next = new ListNode(6);
        ListNode d = new ListNode(1);
        assert getIntersectionNode(c, d) == null;
        System.out.println("IntersectionTwoLists: OK");
    }
}
