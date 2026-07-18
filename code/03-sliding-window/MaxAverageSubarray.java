/**
 * LeetCode 643 — Maximum Average Subarray I. Two variations:
 * recount-per-window O(n*k) vs incremental fixed window O(n).
 * Mirrors pages/03-sliding-window.html.
 */
public class MaxAverageSubarray {

    public static double findMaxAverageBrute(int[] nums, int k) {
        double best = Double.NEGATIVE_INFINITY;
        for (int start = 0; start + k <= nums.length; start++) {
            long sum = 0;
            for (int i = start; i < start + k; i++) sum += nums[i];  // k adds per window
            best = Math.max(best, (double) sum / k);
        }
        return best;
    }

    public static double findMaxAverage(int[] nums, int k) {
        long sum = 0;
        for (int i = 0; i < k; i++) sum += nums[i];   // seed: sum of the first window
        long best = sum;
        for (int i = k; i < nums.length; i++) {
            sum += nums[i] - nums[i - k];   // add the entering element, drop the leaving one
            best = Math.max(best, sum);     // compare SUMS — same k, so same ranking as averages
        }
        return (double) best / k;           // divide once at the end
    }

    public static void main(String[] args) {
        assert Math.abs(findMaxAverage(new int[]{1, 12, -5, -6, 50, 3}, 4) - 12.75) < 1e-9;
        assert Math.abs(findMaxAverage(new int[]{5}, 1) - 5.0) < 1e-9;
        assert Math.abs(findMaxAverageBrute(new int[]{1, 12, -5, -6, 50, 3}, 4) - 12.75) < 1e-9;
        System.out.println("MaxAverageSubarray: OK");
    }
}
