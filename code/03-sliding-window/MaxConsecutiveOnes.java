public class MaxConsecutiveOnes {

    // A run counter is the degenerate sliding window: it grows on 1s and
    // resets (rather than shrinks) on 0s.
    public static int findMaxConsecutiveOnes(int[] nums) {
        int best = 0, run = 0;
        for (int x : nums) {
            run = (x == 1) ? run + 1 : 0;   // extend the streak or start over
            best = Math.max(best, run);
        }
        return best;
    }

    public static void main(String[] args) {
        assert findMaxConsecutiveOnes(new int[]{1, 1, 0, 1, 1, 1}) == 3;
        assert findMaxConsecutiveOnes(new int[]{1, 0, 1, 1, 0, 1}) == 2;
        assert findMaxConsecutiveOnes(new int[]{0, 0}) == 0;
        System.out.println("MaxConsecutiveOnes: OK");
    }
}
