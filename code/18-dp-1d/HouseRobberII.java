/**
 * LeetCode 213 — House Robber II (circular street). Reduce to two line
 * problems: exclude house 0 or exclude house n-1.
 * Mirrors pages/18-dp-1d.html.
 */
public class HouseRobberII {

    public static int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];
        // The circle only adds ONE constraint: house 0 and house n-1 can't
        // both be robbed. So the answer is the better of two LINE problems:
        //   case A: house 0 allowed, house n-1 excluded  -> rob nums[0..n-2]
        //   case B: house 0 excluded, house n-1 allowed  -> rob nums[1..n-1]
        return Math.max(robLine(nums, 0, n - 2),
                        robLine(nums, 1, n - 1));
    }

    // Plain House Robber (LC 198) on nums[lo..hi] with two rolling variables.
    private static int robLine(int[] nums, int lo, int hi) {
        int skip = 0, rob = 0;                 // best if we skip / rob the previous house
        for (int i = lo; i <= hi; i++) {
            int newRob = skip + nums[i];       // rob i: previous must be skipped
            skip = Math.max(skip, rob);        // skip i: carry the better past
            rob = newRob;
        }
        return Math.max(skip, rob);
    }

    public static void main(String[] args) {
        assert rob(new int[]{2, 3, 2}) == 3;
        assert rob(new int[]{1, 2, 3, 1}) == 4;
        assert rob(new int[]{5}) == 5;
        assert rob(new int[]{1, 2}) == 2;
        assert rob(new int[]{1, 2, 3}) == 3;
        System.out.println("HouseRobberII: OK");
    }
}
