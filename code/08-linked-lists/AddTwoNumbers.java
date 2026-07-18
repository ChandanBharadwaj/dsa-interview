/**
 * LeetCode 2 — Add Two Numbers. Grade-school addition over reverse-order
 * digit lists, with the carry able to mint a final node.
 * Mirrors pages/08-linked-lists.html.
 */
public class AddTwoNumbers {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0), tail = dummy;
        int carry = 0;
        // Keep going while EITHER list has digits OR a carry is pending —
        // the carry alone can mint one final node (99 + 1 = 100).
        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;
            if (l1 != null) { sum += l1.val; l1 = l1.next; }   // consume a digit if present
            if (l2 != null) { sum += l2.val; l2 = l2.next; }
            tail.next = new ListNode(sum % 10);   // this position's digit
            carry = sum / 10;                     // 0 or 1, into the next position
            tail = tail.next;
        }
        return dummy.next;
    }

    private static ListNode build(int... digits) {
        ListNode dummy = new ListNode(0), t = dummy;
        for (int d : digits) { t.next = new ListNode(d); t = t.next; }
        return dummy.next;
    }

    private static boolean equals(ListNode head, int... digits) {
        for (int d : digits) {
            if (head == null || head.val != d) return false;
            head = head.next;
        }
        return head == null;
    }

    public static void main(String[] args) {
        assert equals(addTwoNumbers(build(2, 4, 3), build(5, 6, 4)), 7, 0, 8);  // 342+465=807
        assert equals(addTwoNumbers(build(9, 9), build(1)), 0, 0, 1);           // 99+1=100
        assert equals(addTwoNumbers(build(0), build(0)), 0);
        System.out.println("AddTwoNumbers: OK");
    }
}
