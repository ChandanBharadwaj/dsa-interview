import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 362 — Design Hit Counter. Sliding-window log (queue) vs
 * 300 circular buckets with self-invalidating timestamps.
 * Mirrors pages/24-advanced-structures.html.
 */
public class HitCounter {

    // Variation 2: fixed 300 buckets (shown as the main class).
    private final int[] times = new int[300];
    private final int[] counts = new int[300];

    public void hit(int timestamp) {
        int i = timestamp % 300;             // this second's bucket
        if (times[i] != timestamp) {         // bucket still holds an OLD second
            times[i] = timestamp;            // claim it for the current second
            counts[i] = 0;
        }
        counts[i]++;
    }

    public int getHits(int timestamp) {
        int total = 0;
        for (int i = 0; i < 300; i++)
            if (timestamp - times[i] < 300)  // bucket's second is inside the window
                total += counts[i];
        return total;
    }

    // Variation 1: exact queue-of-timestamps log.
    static class HitCounterQueue {
        private final Deque<Integer> hits = new ArrayDeque<>();
        public void hit(int timestamp) { hits.offer(timestamp); }
        public int getHits(int timestamp) {
            while (!hits.isEmpty() && hits.peek() <= timestamp - 300)
                hits.poll();
            return hits.size();
        }
    }

    public static void main(String[] args) {
        HitCounter hc = new HitCounter();
        hc.hit(1); hc.hit(2); hc.hit(3);
        assert hc.getHits(4) == 3;
        hc.hit(300);
        assert hc.getHits(300) == 4;
        assert hc.getHits(301) == 3;         // the t=1 hit expired

        HitCounterQueue q = new HitCounterQueue();
        q.hit(1); q.hit(2); q.hit(3); q.hit(300);
        assert q.getHits(300) == 4 && q.getHits(301) == 3;
        System.out.println("HitCounter: OK");
    }
}
