import java.util.Arrays;

public class LongestIncreasingSubsequence {

    // ---------- O(n^2) DP: dp[i] = length of the longest increasing subsequence ENDING at i ----------
    public static int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);                       // every element alone is an LIS of length 1
        int best = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {         // try appending nums[i] to every earlier LIS...
                if (nums[j] < nums[i]) {          // ...that it can legally extend
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            best = Math.max(best, dp[i]);         // the answer can end anywhere, so track the max
        }
        return best;
    }

    // ---------- O(n log n) "patience" version ----------
    // tails[k] = the SMALLEST possible tail value of any increasing subsequence of length k+1.
    // tails is always sorted, so each element binary-searches its landing spot.
    public static int lengthOfLISFast(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;                             // how many pile tails are in use
        for (int x : nums) {
            // find the FIRST tail >= x (lower bound) — that pile's tail can be improved to x
            int lo = 0, hi = size;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (tails[mid] < x) lo = mid + 1;
                else hi = mid;
            }
            tails[lo] = x;                        // replace (improve) that tail, or...
            if (lo == size) size++;               // ...x beats every tail: a longer subsequence exists
        }
        return size;                              // number of piles = LIS length
    }

    public static void main(String[] args) {
        assert lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}) == 4;   // 2,3,7,101 (or 2,5,7,18)
        assert lengthOfLIS(new int[]{0, 1, 0, 3, 2, 3}) == 4;             // 0,1,2,3
        assert lengthOfLIS(new int[]{7, 7, 7, 7}) == 1;                   // strictly increasing!
        assert lengthOfLISFast(new int[]{10, 9, 2, 5, 3, 7, 101, 18}) == 4;
        assert lengthOfLISFast(new int[]{0, 1, 0, 3, 2, 3}) == 4;
        assert lengthOfLISFast(new int[]{7, 7, 7, 7}) == 1;
        System.out.println("LongestIncreasingSubsequence: OK");
    }
}
