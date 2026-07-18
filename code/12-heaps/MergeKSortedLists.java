import java.util.*;

public class MergeKSortedLists {

    // Minimal singly linked list node (page 08).
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    // LeetCode 23. Merge k sorted linked lists into one sorted list.
    public static ListNode mergeKLists(ListNode[] lists) {
        // A min-heap holds ONE candidate per list: its current head.
        // The global smallest remaining element is always one of these k heads
        // (each list is sorted!), so poll() hands us the next output node.
        PriorityQueue<ListNode> heap =
                new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));
        for (ListNode head : lists) {
            if (head != null) heap.offer(head);   // skip empty lists
        }

        ListNode dummy = new ListNode(0);   // dummy head: no "first node" special case
        ListNode tail = dummy;
        while (!heap.isEmpty()) {
            ListNode smallest = heap.poll();      // global minimum, O(log k)
            tail.next = smallest;                 // append to the merged result
            tail = smallest;
            if (smallest.next != null) {
                heap.offer(smallest.next);        // that list's next head replaces it
            }
        }
        return dummy.next;  // total: n nodes each pushed+popped once -> O(n log k)
    }

    // -------- test helpers --------
    private static ListNode fromArray(int... vals) {
        ListNode dummy = new ListNode(0), t = dummy;
        for (int v : vals) { t.next = new ListNode(v); t = t.next; }
        return dummy.next;
    }
    private static List<Integer> toList(ListNode head) {
        List<Integer> out = new ArrayList<>();
        for (; head != null; head = head.next) out.add(head.val);
        return out;
    }

    public static void main(String[] args) {
        // Classic: [[1,4,5],[1,3,4],[2,6]] -> [1,1,2,3,4,4,5,6]
        ListNode merged = mergeKLists(new ListNode[]{
                fromArray(1, 4, 5), fromArray(1, 3, 4), fromArray(2, 6)});
        assert toList(merged).equals(List.of(1, 1, 2, 3, 4, 4, 5, 6));
        // All-empty input
        assert mergeKLists(new ListNode[]{}) == null;
        assert mergeKLists(new ListNode[]{null, null}) == null;
        // Single list passes through untouched
        assert toList(mergeKLists(new ListNode[]{fromArray(7, 8)})).equals(List.of(7, 8));
        System.out.println("MergeKSortedLists: OK");
    }
}
