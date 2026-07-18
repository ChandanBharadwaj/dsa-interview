/**
 * LeetCode 152 — Maximum Product Subarray. Kadane with a twin: carry the
 * min product too, because a negative flips extremes.
 * Mirrors pages/18-dp-1d.html.
 */
public class MaxProductSubarray {

    public static int maxProduct(int[] nums) {
        // maxHere / minHere = the largest and smallest product of a subarray
        // ENDING at the current index. We need the min too: multiplied by a
        // negative, the smallest product becomes the largest.
        int maxHere = nums[0], minHere = nums[0], best = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int x = nums[i];
            if (x < 0) {                      // a negative flips the roles:
                int t = maxHere;              // the old min is about to become
                maxHere = minHere;            // the new max, and vice versa
                minHere = t;
            }
            // Either extend the running product, or start fresh at x
            maxHere = Math.max(x, maxHere * x);
            minHere = Math.min(x, minHere * x);
            best = Math.max(best, maxHere);
        }
        return best;
    }

    public static void main(String[] args) {
        assert maxProduct(new int[]{2, 3, -2, 4}) == 6;
        assert maxProduct(new int[]{-2, 0, -1}) == 0;
        assert maxProduct(new int[]{-2, 3, -4}) == 24;
        assert maxProduct(new int[]{-2}) == -2;
        System.out.println("MaxProductSubarray: OK");
    }
}
