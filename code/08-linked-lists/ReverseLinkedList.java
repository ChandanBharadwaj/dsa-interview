// LeetCode 206 — Reverse Linked List
// The single most-asked pointer question. Iterative 3-pointer + recursive version.
public class ReverseLinkedList {

    // Minimal singly linked node — interviews always hand you this shape.
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    // Iterative: walk the list once, flipping each .next backwards.
    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;                  // everything BEFORE curr, already reversed (starts empty)
        ListNode curr = head;                  // the node whose arrow we're about to flip
        while (curr != null) {
            ListNode next = curr.next;         // 1. save the rest of the list BEFORE we cut the link
            curr.next = prev;                  // 2. flip: curr now points backwards
            prev = curr;                       // 3. the reversed prefix grows by one node
            curr = next;                       // 4. step into the saved remainder
        }
        return prev;                           // curr fell off the end; prev is the new head
    }

    // Recursive: trust reverseList(rest) to hand back the reversed tail,
    // then hook the old head onto the end of it.
    public static ListNode reverseRecursive(ListNode head) {
        if (head == null || head.next == null) return head;   // base: empty or single node is already reversed
        ListNode newHead = reverseRecursive(head.next);        // leap of faith: rest is now reversed; newHead = old tail
        head.next.next = head;                 // my old successor is now the reversed tail's last node — point it back at me
        head.next = null;                      // I'm the new tail; sever my stale forward link
        return newHead;                        // the new head never changes on the way back up
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
        assert render(reverseList(build(1,2,3,4,5))).equals("5,4,3,2,1,");
        assert render(reverseList(build(1))).equals("1,");
        assert render(reverseList(null)).equals("");
        assert render(reverseRecursive(build(1,2,3))).equals("3,2,1,");
        System.out.println("ReverseLinkedList: OK");
    }
}
