import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * LeetCode 1046 — Last Stone Weight. Max-heap simulation:
 * poll two heaviest, push back the difference.
 * Mirrors pages/12-heaps.html.
 */
public class LastStoneWeight {

    public static int lastStoneWeight(int[] stones) {
        // The simulation repeatedly needs "the two largest" — a max-heap's
        // specialty. Java's PriorityQueue is a MIN-heap, so reverse it.
        PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int s : stones) heap.offer(s);

        while (heap.size() > 1) {
            int a = heap.poll();                  // heaviest
            int b = heap.poll();                  // second heaviest
            if (a != b) heap.offer(a - b);        // the remnant re-enters the pool
        }
        return heap.isEmpty() ? 0 : heap.peek();
    }

    public static void main(String[] args) {
        assert lastStoneWeight(new int[]{2, 7, 4, 1, 8, 1}) == 1;
        assert lastStoneWeight(new int[]{1}) == 1;
        assert lastStoneWeight(new int[]{3, 3}) == 0;
        System.out.println("LastStoneWeight: OK");
    }
}
