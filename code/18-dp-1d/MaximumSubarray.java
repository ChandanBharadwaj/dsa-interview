import java.util.*;

public class MaximumSubarray {

    // Variation 1: brute force — every start, extend to every end. O(n²)
    public static int maxSubArrayBrute(int[] nums) {
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                best = Math.max(best, sum);
            }
        }
        return best;
    }

    // Variation 2: Kadane — DP where endingHere[i] = best sum ENDING at i:
    // either extend the previous run or start fresh at nums[i]. O(n), O(1).
    public static int maxSubArray(int[] nums) {
        int endingHere = nums[0];       // best subarray ending at this index
        int best = nums[0];
        for (int i = 1; i < nums.length; i++) {
            // a negative prefix only drags us down — drop it and restart
            endingHere = Math.max(nums[i], endingHere + nums[i]);
            best = Math.max(best, endingHere);
        }
        return best;
    }

    public static void main(String[] args) {
        assert maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}) == 6;  // [4,-1,2,1]
        assert maxSubArray(new int[]{1}) == 1;
        assert maxSubArray(new int[]{5, 4, -1, 7, 8}) == 23;
        assert maxSubArray(new int[]{-3, -1, -2}) == -1;    // all negative
        Random r = new Random(17);
        for (int t = 0; t < 300; t++) {                     // cross-check variants
            int[] a = new int[1 + r.nextInt(25)];
            for (int i = 0; i < a.length; i++) a[i] = r.nextInt(21) - 10;
            assert maxSubArray(a) == maxSubArrayBrute(a);
        }
        System.out.println("MaximumSubarray: OK");
    }
}
