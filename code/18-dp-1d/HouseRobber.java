public class HouseRobber {

    // dp[i] = the most money you can have after CONSIDERING houses 0..i
    // At each house you face one binary choice:
    //   rob it  -> you must have skipped i-1, so you get dp[i-2] + nums[i]
    //   skip it -> you keep whatever was best through i-1, i.e. dp[i-1]
    public static int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];
        int prev2 = nums[0];                        // best through house 0
        int prev1 = Math.max(nums[0], nums[1]);     // best through house 1
        for (int i = 2; i < n; i++) {
            int robIt  = prev2 + nums[i];           // take this house + best two houses back
            int skipIt = prev1;                     // pass, carry the previous best forward
            int cur = Math.max(robIt, skipIt);      // the DP choice
            prev2 = prev1;                          // slide the 2-value window
            prev1 = cur;
        }
        return prev1;                               // best after considering every house
    }

    public static void main(String[] args) {
        assert rob(new int[]{1, 2, 3, 1}) == 4;     // rob houses 0 and 2
        assert rob(new int[]{2, 7, 9, 3, 1}) == 12; // rob houses 0, 2, 4
        assert rob(new int[]{2, 1, 1, 2}) == 4;     // rob houses 0 and 3 (skip TWO in a row is fine)
        assert rob(new int[]{5}) == 5;
        System.out.println("HouseRobber: OK");
    }
}
