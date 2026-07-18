import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode 217 — Contains Duplicate. Three variations from the page:
 * brute-force pairs O(n^2), sort O(n log n), seen-set O(n).
 * Mirrors pages/01-arrays-hashing.html.
 */
public class ContainsDuplicate {

    public static boolean containsDuplicateBrute(int[] nums) {
        for (int i = 0; i < nums.length; i++)
            for (int j = i + 1; j < nums.length; j++)   // every pair (i, j)
                if (nums[i] == nums[j]) return true;
        return false;
    }

    public static boolean containsDuplicateSort(int[] nums) {
        Arrays.sort(nums);                            // duplicates now sit side by side
        for (int i = 1; i < nums.length; i++)
            if (nums[i] == nums[i - 1]) return true;  // just compare neighbors
        return false;
    }

    public static boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int x : nums)
            if (!seen.add(x))      // add() returns false if x was already present
                return true;       // -> x is the duplicate, stop immediately
        return false;
    }

    public static void main(String[] args) {
        assert containsDuplicate(new int[]{1, 2, 3, 1});
        assert !containsDuplicate(new int[]{1, 2, 3, 4});
        assert containsDuplicateBrute(new int[]{1, 1, 1, 3, 3, 4, 3, 2, 4, 2});
        assert !containsDuplicateSort(new int[]{5, 9, 2});
        assert containsDuplicateSort(new int[]{5, 9, 5});
        System.out.println("ContainsDuplicate: OK");
    }
}
