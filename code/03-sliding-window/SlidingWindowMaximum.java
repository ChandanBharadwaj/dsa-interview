import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * LeetCode 239 — Sliding Window Maximum. Two variations:
 * rescan-per-window O(n*k) vs monotonic deque O(n).
 * Mirrors pages/03-sliding-window.html.
 */
public class SlidingWindowMaximum {

    public static int[] maxSlidingWindowBrute(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n - k + 1];
        for (int start = 0; start + k <= n; start++) {
            int mx = nums[start];
            for (int i = start + 1; i < start + k; i++)   // k-1 comparisons per window
                mx = Math.max(mx, nums[i]);
            res[start] = mx;
        }
        return res;
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n - k + 1];
        // Deque holds INDEXES; their values are kept strictly decreasing.
        // Front = index of the current window's maximum.
        Deque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            // 1. Expire the front if it slid out of the window [i-k+1 .. i]
            if (!dq.isEmpty() && dq.peekFirst() <= i - k)
                dq.pollFirst();
            // 2. The new value kills every smaller value at the back: they
            //    are older AND smaller, so they can never be a future max.
            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i])
                dq.pollLast();
            dq.offerLast(i);
            // 3. Window is complete from i == k-1 on: front is its max
            if (i >= k - 1)
                res[i - k + 1] = nums[dq.peekFirst()];
        }
        return res;
    }

    public static void main(String[] args) {
        assert Arrays.equals(
            maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3),
            new int[]{3, 3, 5, 5, 6, 7});
        assert Arrays.equals(maxSlidingWindow(new int[]{1}, 1), new int[]{1});
        assert Arrays.equals(
            maxSlidingWindowBrute(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3),
            new int[]{3, 3, 5, 5, 6, 7});
        System.out.println("SlidingWindowMaximum: OK");
    }
}
