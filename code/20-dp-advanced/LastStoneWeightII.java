public class LastStoneWeightII {

    // Smashing stones = putting + and - signs: the final weight is
    // |sum(A) - sum(B)| over a partition. Minimize it = find the subset
    // sum closest to total/2. Subset-sum knapsack.
    public static int lastStoneWeightII(int[] stones) {
        int total = 0;
        for (int s : stones) total += s;
        int half = total / 2;

        boolean[] dp = new boolean[half + 1];   // dp[s]: some subset sums to s
        dp[0] = true;
        for (int stone : stones)
            for (int s = half; s >= stone; s--) // backwards: use each stone once
                dp[s] |= dp[s - stone];

        for (int s = half; s >= 0; s--)         // best subset ≤ half
            if (dp[s])
                return total - 2 * s;           // |(total-s) - s|
        return total;
    }

    public static void main(String[] args) {
        assert lastStoneWeightII(new int[]{2, 7, 4, 1, 8, 1}) == 1;
        assert lastStoneWeightII(new int[]{31, 26, 33, 21, 40}) == 5;
        assert lastStoneWeightII(new int[]{1, 2}) == 1;
        assert lastStoneWeightII(new int[]{6}) == 6;
        System.out.println("LastStoneWeightII: OK");
    }
}
