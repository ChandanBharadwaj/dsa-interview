import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode 41 — First Missing Positive. Two variations from the page:
 * seen-set (O(n) space, violates the constraint) and cyclic sort
 * (O(n) time, O(1) space). Mirrors pages/01-arrays-hashing.html.
 */
public class FirstMissingPositive {

    public static int firstMissingPositiveSet(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int x : nums) set.add(x);
        for (int v = 1; v <= nums.length + 1; v++)  // answer is in 1..n+1
            if (!set.contains(v)) return v;
        return -1;                                   // unreachable
    }

    public static int firstMissingPositive(int[] nums) {
        int n = nums.length;
        // Phase 1: swap every value v in 1..n to its home index v-1.
        for (int i = 0; i < n; i++) {
            // Keep swapping until nums[i] is out of range, or already home,
            // or its home already holds a correct copy (guards duplicates).
            while (nums[i] >= 1 && nums[i] <= n
                   && nums[nums[i] - 1] != nums[i]) {
                int home = nums[i] - 1;             // where nums[i] belongs
                int t = nums[home];                 // classic swap
                nums[home] = nums[i];
                nums[i] = t;
            }
        }
        // Phase 2: the first index whose value disagrees exposes the answer.
        for (int i = 0; i < n; i++)
            if (nums[i] != i + 1) return i + 1;
        return n + 1;                               // 1..n all present -> answer is n+1
    }

    public static void main(String[] args) {
        assert firstMissingPositive(new int[]{1, 2, 0}) == 3;
        assert firstMissingPositive(new int[]{3, 4, -1, 1}) == 2;
        assert firstMissingPositive(new int[]{7, 8, 9, 11, 12}) == 1;
        assert firstMissingPositive(new int[]{1, 1}) == 2;   // duplicate guard case
        assert firstMissingPositiveSet(new int[]{1, 2, 3}) == 4;
        System.out.println("FirstMissingPositive: OK");
    }
}
