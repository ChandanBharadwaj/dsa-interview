import java.util.*;

public class ShortestSubarraySumK {

    // Negatives break the simple shrinking window (page 03), because growing
    // the window can now DECREASE the sum. Fix: prefix sums + a monotonic
    // deque of candidate start points.
    public static int shortestSubarray(int[] nums, int k) {
        int n = nums.length;
        long[] prefix = new long[n + 1];         // sums can exceed int range
        for (int i = 0; i < n; i++)
            prefix[i + 1] = prefix[i] + nums[i];

        int best = n + 1;
        Deque<Integer> dq = new ArrayDeque<>();  // indices, prefix increasing
        for (int j = 0; j <= n; j++) {
            // 1) Harvest: while the smallest candidate start works, take it
            //    and RETIRE it — any later j would only give a longer answer.
            while (!dq.isEmpty() && prefix[j] - prefix[dq.peekFirst()] >= k)
                best = Math.min(best, j - dq.pollFirst());
            // 2) Maintain monotonicity: a start with a bigger prefix than us
            //    is strictly worse (starts earlier AND leaves more to cover).
            while (!dq.isEmpty() && prefix[dq.peekLast()] >= prefix[j])
                dq.pollLast();
            dq.offerLast(j);
        }
        return best == n + 1 ? -1 : best;
    }

    public static void main(String[] args) {
        assert shortestSubarray(new int[]{1}, 1) == 1;
        assert shortestSubarray(new int[]{1, 2}, 4) == -1;
        assert shortestSubarray(new int[]{2, -1, 2}, 3) == 3;
        assert shortestSubarray(new int[]{84, -37, 32, 40, 95}, 167) == 3;
        // brute-force cross-check on random arrays
        Random r = new Random(11);
        for (int t = 0; t < 300; t++) {
            int n = 1 + r.nextInt(12);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = r.nextInt(21) - 10;
            int kk = 1 + r.nextInt(15);
            int brute = n + 1;
            for (int i = 0; i < n; i++) {
                long s = 0;
                for (int j = i; j < n; j++) {
                    s += a[j];
                    if (s >= kk) { brute = Math.min(brute, j - i + 1); break; }
                }
            }
            int got = shortestSubarray(a, kk);
            assert got == (brute == n + 1 ? -1 : brute);
        }
        System.out.println("ShortestSubarraySumK: OK");
    }
}
