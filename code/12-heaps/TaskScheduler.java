import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * LeetCode 621 — Task Scheduler. Greedy frame formula and the max-heap
 * frame simulation. Mirrors pages/12-heaps.html.
 */
public class TaskScheduler {

    public static int leastIntervalFormula(int[] tasks, int n) {
        int[] freq = new int[26];
        for (int t : tasks) freq[t - 'A']++;
        int max = 0, maxCount = 0;                // highest frequency, and how many
        for (int f : freq) {                      // task types share it
            if (f > max) { max = f; maxCount = 1; }
            else if (f == max && f > 0) maxCount++;
        }
        // Picture the busiest task laid out: (max-1) full frames of size n+1,
        // then one final slot for each task tied at max frequency.
        int frames = (max - 1) * (n + 1) + maxCount;
        // If there are more tasks than frame slots, they simply fill the idles
        // and the answer is just tasks.length.
        return Math.max(frames, tasks.length);
    }

    public static int leastInterval(int[] tasks, int n) {
        int[] freq = new int[26];
        for (int t : tasks) freq[t - 'A']++;
        // Max-heap of remaining counts: always run the most numerous task first
        // (it's the one whose cooldowns threaten to force idles).
        PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int f : freq) if (f > 0) heap.offer(f);

        int time = 0;
        while (!heap.isEmpty()) {
            // One "frame" of n+1 slots: run up to n+1 DIFFERENT tasks — that
            // spacing is exactly what the cooldown demands.
            List<Integer> stillLeft = new ArrayList<>();
            int slots = n + 1;
            int ran = 0;                           // tasks actually run this frame
            for (int i = 0; i < slots && !heap.isEmpty(); i++) {
                int f = heap.poll();               // run the most numerous task once
                ran++;
                if (f - 1 > 0) stillLeft.add(f - 1);   // it still has repeats left
            }
            stillLeft.forEach(heap::offer);        // cooldowns over — re-enter the pool
            // A non-final frame is paid IN FULL (unused slots are forced idles);
            // the last frame costs only the tasks it actually ran.
            time += heap.isEmpty() ? ran : slots;
        }
        return time;
    }

    public static void main(String[] args) {
        int[] t1 = {'A', 'A', 'A', 'B', 'B', 'B'};
        assert leastInterval(t1, 2) == 8 && leastIntervalFormula(t1, 2) == 8;
        assert leastInterval(t1, 0) == 6 && leastIntervalFormula(t1, 0) == 6;
        int[] t2 = {'A', 'A', 'A', 'A', 'A', 'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        assert leastInterval(t2, 2) == 16 && leastIntervalFormula(t2, 2) == 16;
        System.out.println("TaskScheduler: OK");
    }
}
