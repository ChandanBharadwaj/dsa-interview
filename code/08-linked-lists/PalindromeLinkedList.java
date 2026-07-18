// LeetCode 234 — Palindrome Linked List
public class PalindromeLinkedList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    // O(1) space: find the middle (fast/slow), reverse the second half,
    // compare the two halves in lockstep. Three sub-skills chained.
    public static boolean isPalindrome(ListNode head) {
        // 1. middle: slow lands at mid (second half's start for even length)
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 2. reverse from slow onward
        ListNode prev = null, curr = slow;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        // 3. march from both ends toward the middle
        ListNode a = head, b = prev;              // b = reversed tail's head
        while (b != null) {                       // second half is the shorter
            if (a.val != b.val) return false;
            a = a.next;
            b = b.next;
        }
        return true;
    }

    private static ListNode build(int... vals) {
        ListNode dummy = new ListNode(0), t = dummy;
        for (int v : vals) { t.next = new ListNode(v); t = t.next; }
        return dummy.next;
    }

    public static void main(String[] args) {
        assert isPalindrome(build(1, 2, 2, 1));
        assert !isPalindrome(build(1, 2));
        assert isPalindrome(build(7));
        assert isPalindrome(build(1, 2, 3, 2, 1));
        assert !isPalindrome(build(1, 2, 3, 3, 1));
        System.out.println("PalindromeLinkedList: OK");
    }
}
