import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * LeetCode 26 — Remove Duplicates from Sorted Array. Two variations:
 * extra-set copy vs in-place read/write pointers.
 * Mirrors pages/02-two-pointers.html.
 */
public class RemoveDuplicates {

    public static int removeDuplicatesExtra(int[] nums) {
        // LinkedHashSet keeps insertion order while dropping repeats
        Set<Integer> set = new LinkedHashSet<>();
        for (int x : nums) set.add(x);
        int i = 0;
        for (int x : set) nums[i++] = x;     // copy the survivors back
        return set.size();
    }

    public static int removeDuplicates(int[] nums) {
        int write = 1;                        // nums[0] is always kept
        for (int read = 1; read < nums.length; read++) {
            // Sorted input => duplicates are adjacent. Keep nums[read] only
            // if it differs from the last KEPT value.
            if (nums[read] != nums[write - 1]) {
                nums[write] = nums[read];     // safe: write <= read always
                write++;
            }
        }
        return write;                         // first `write` slots = deduped array
    }

    public static void main(String[] args) {
        int[] a = {1, 1, 2};
        assert removeDuplicates(a) == 2;
        assert Arrays.equals(Arrays.copyOf(a, 2), new int[]{1, 2});
        int[] b = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        assert removeDuplicates(b) == 5;
        assert Arrays.equals(Arrays.copyOf(b, 5), new int[]{0, 1, 2, 3, 4});
        int[] c = {5, 5, 5};
        assert removeDuplicatesExtra(c) == 1 && c[0] == 5;
        System.out.println("RemoveDuplicates: OK");
    }
}
