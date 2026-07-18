public class PartitionEqualSubsetSum {

    // Knapsack in disguise: can some subset hit target = totalSum / 2?
    // dp[s] = "is sum s reachable using the items considered so far?"
    public static boolean canPartition(int[] nums) {
        int total = 0;
        for (int x : nums) total += x;
        if (total % 2 != 0) return false;          // odd total can never split into two equal halves
        int target = total / 2;

        boolean[] dp = new boolean[target + 1];
        dp[0] = true;                              // base case: the empty subset reaches sum 0
        for (int num : nums) {                     // 0/1: consider each item exactly once
            // REVERSE loop so this iteration reads only LAST iteration's values —
            // forward would let one item be counted twice (that's the unbounded variant).
            for (int s = target; s >= num; s--) {
                if (dp[s - num]) dp[s] = true;     // reach s by adding num to a reachable s-num
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        assert canPartition(new int[]{1, 5, 11, 5});        // {1,5,5} and {11}
        assert !canPartition(new int[]{1, 2, 3, 5});        // total 11 is odd
        assert canPartition(new int[]{2, 2, 2, 2});
        assert !canPartition(new int[]{1, 1, 3});
        System.out.println("PartitionEqualSubsetSum: OK");
    }
}
