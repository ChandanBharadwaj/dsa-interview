import java.util.Arrays;

/**
 * LeetCode 1365 — How Many Numbers Are Smaller Than the Current Number.
 * Counting sort + prefix counts, O(n + k) for values bounded 0..100.
 * Mirrors pages/06-sorting.html.
 */
public class SmallerNumbersThanCurrent {

    public static int[] smallerNumbersBrute(int[] nums) {
        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++)
            for (int j = 0; j < nums.length; j++)
                if (nums[j] < nums[i]) res[i]++;
        return res;
    }

    public static int[] smallerNumbersThanCurrent(int[] nums) {
        // Values are 0..100 — the constraint that unlocks counting sort.
        int[] count = new int[101];
        for (int x : nums) count[x]++;            // tally each value

        // prefix[v] = how many elements are <= v. After this loop,
        // count[v-1] answers "how many are STRICTLY smaller than v".
        for (int v = 1; v <= 100; v++)
            count[v] += count[v - 1];

        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++)
            res[i] = nums[i] == 0 ? 0 : count[nums[i] - 1];
        return res;
    }

    public static void main(String[] args) {
        assert Arrays.equals(smallerNumbersThanCurrent(new int[]{8, 1, 2, 2, 3}),
                             new int[]{4, 0, 1, 1, 3});
        assert Arrays.equals(smallerNumbersThanCurrent(new int[]{6, 5, 4, 8}),
                             new int[]{2, 1, 0, 3});
        assert Arrays.equals(smallerNumbersBrute(new int[]{7, 7, 7, 7}),
                             new int[]{0, 0, 0, 0});
        System.out.println("SmallerNumbersThanCurrent: OK");
    }
}
