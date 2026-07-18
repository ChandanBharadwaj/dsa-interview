public class MinSizeSubarraySum {

    // Variable window hunting a MINIMUM length: expand until the sum reaches
    // the target, then shrink as hard as possible while it still qualifies.
    // Works because all elements are positive (sum is monotonic in width).
    public static int minSubArrayLen(int target, int[] nums) {
        int best = Integer.MAX_VALUE;
        int sum = 0, left = 0;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];                    // expand
            while (sum >= target) {                // qualifies -> record & shrink
                best = Math.min(best, right - left + 1);
                sum -= nums[left++];               // pull the left edge in
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }

    public static void main(String[] args) {
        assert minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3}) == 2;   // [4,3]
        assert minSubArrayLen(4, new int[]{1, 4, 4}) == 1;
        assert minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1}) == 0;
        System.out.println("MinSizeSubarraySum: OK");
    }
}
