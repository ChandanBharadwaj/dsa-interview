import java.util.PriorityQueue;

/**
 * LeetCode 703 — Kth Largest Element in a Stream. Permanent size-k
 * min-heap "club": add() is O(log k). Mirrors pages/12-heaps.html.
 */
public class KthLargestStream {

    private final PriorityQueue<Integer> heap = new PriorityQueue<>(); // min-heap
    private final int k;

    public KthLargestStream(int k, int[] nums) {
        this.k = k;
        for (int x : nums) add(x);            // reuse add() to build the club
    }

    public int add(int val) {
        heap.offer(val);                      // newcomer applies to the club
        if (heap.size() > k) heap.poll();     // evict the weakest of the k+1
        return heap.peek();                   // root = weakest member = k-th largest
    }

    public static void main(String[] args) {
        KthLargestStream s = new KthLargestStream(3, new int[]{4, 5, 8, 2});
        assert s.add(3) == 4;
        assert s.add(5) == 5;
        assert s.add(10) == 5;
        assert s.add(9) == 8;
        assert s.add(4) == 8;
        System.out.println("KthLargestStream: OK");
    }
}
