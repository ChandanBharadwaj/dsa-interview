public class MaxConsecutiveOnesIII {

    // "Longest run of 1s if you may flip k zeros" = longest window containing
    // at most k zeros. Track only the zero count.
    public static int longestOnes(int[] nums, int k) {
        int best = 0, left = 0, zeros = 0;
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) zeros++;         // window absorbs a zero
            while (zeros > k)                      // budget blown -> shrink
                if (nums[left++] == 0) zeros--;
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    public static void main(String[] args) {
        assert longestOnes(new int[]{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, 2) == 6;
        assert longestOnes(new int[]{0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1}, 3) == 10;
        assert longestOnes(new int[]{0, 0, 0}, 0) == 0;
        System.out.println("MaxConsecutiveOnesIII: OK");
    }
}
