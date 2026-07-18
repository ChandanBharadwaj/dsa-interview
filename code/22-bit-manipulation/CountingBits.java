import java.util.Arrays;

// LeetCode 338 — Counting Bits. ans[i] = number of 1-bits in i, for all i in 0..n,
// in O(n) total via a one-line DP: i >> 1 is i with its last bit dropped, so
// bits(i) = bits(i >> 1) + (last bit of i). Each answer reuses a smaller one.
public class CountingBits {

    public static int[] countBits(int n) {
        int[] dp = new int[n + 1];               // dp[0] = 0: zero has no set bits (base case)
        for (int i = 1; i <= n; i++) {
            // i >> 1 < i, so dp[i >> 1] is already computed when we need it
            dp[i] = dp[i >> 1] + (i & 1);        // popcount of the prefix + the dropped last bit
        }
        return dp;
    }

    public static void main(String[] args) {
        assert Arrays.equals(countBits(2), new int[]{0, 1, 1});
        assert Arrays.equals(countBits(5), new int[]{0, 1, 1, 2, 1, 2});
        assert countBits(255)[255] == 8;         // 255 = 11111111
        System.out.println("CountingBits: OK");
    }
}
