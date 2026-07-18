/**
 * LeetCode 876 — Middle of the Linked List. Two-pass count vs one-pass
 * fast/slow. Mirrors pages/08-linked-lists.html.
 */
public class MiddleOfList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode middleNodeTwoPass(ListNode head) {
        int n = 0;
        for (ListNode c = head; c != null; c = c.next) n++;   // pass 1: length
        ListNode mid = head;
        for (int i = 0; i < n / 2; i++) mid = mid.next;       // pass 2: walk n/2
        return mid;
    }

    public static ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;          // 1 step
            fast = fast.next.next;     // 2 steps — when fast finishes, slow is halfway
        }
        return slow;                   // odd length: exact middle; even: second middle
    }

    private static ListNode build(int n) {
        ListNode head = new ListNode(1), t = head;
        for (int v = 2; v <= n; v++) { t.next = new ListNode(v); t = t.next; }
        return head;
    }

    public static void main(String[] args) {
        assert middleNode(build(5)).val == 3;
        assert middleNode(build(6)).val == 4;          // second middle
        assert middleNodeTwoPass(build(6)).val == 4;
        assert middleNode(build(1)).val == 1;
        System.out.println("MiddleOfList: OK");
    }
}
