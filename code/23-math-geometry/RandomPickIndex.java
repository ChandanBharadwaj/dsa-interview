import java.util.*;

public class RandomPickIndex {

    // Reservoir sampling (k=1): scanning matches of `target`, replace the
    // held index with probability 1/countSoFar. Every match ends up equally
    // likely, using O(1) memory and no precomputation.
    private final int[] nums;
    private final Random rand = new Random();

    public RandomPickIndex(int[] nums) {
        this.nums = nums;
    }

    public int pick(int target) {
        int count = 0, chosen = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != target) continue;
            count++;
            if (rand.nextInt(count) == 0)   // prob 1/count: replace the choice
                chosen = i;
        }
        return chosen;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 3, 3};
        RandomPickIndex rpi = new RandomPickIndex(a);
        assert rpi.pick(1) == 0;                    // unique target: deterministic
        int[] hits = new int[5];
        for (int t = 0; t < 30000; t++)
            hits[rpi.pick(3)]++;
        assert hits[0] == 0 && hits[1] == 0;        // only indices 2,3,4 valid
        for (int i = 2; i <= 4; i++)                // ~10000 each; loose bounds
            assert hits[i] > 8500 && hits[i] < 11500 : "biased: " + hits[i];
        System.out.println("RandomPickIndex: OK");
    }
}
