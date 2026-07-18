import java.util.*;

public class DisappearedNumbers {

    // Values are 1..n, so the array itself can be the hash table:
    // seeing value v, flip index v-1 negative as a "present" mark.
    // Indices left positive were never marked — their values are missing.
    public static List<Integer> findDisappearedNumbers(int[] nums) {
        for (int x : nums) {
            int idx = Math.abs(x) - 1;      // abs: x may already be marked
            if (nums[idx] > 0)
                nums[idx] = -nums[idx];     // mark "value idx+1 exists"
        }
        List<Integer> missing = new ArrayList<>();
        for (int i = 0; i < nums.length; i++)
            if (nums[i] > 0)                // never marked
                missing.add(i + 1);
        return missing;
    }

    public static void main(String[] args) {
        assert findDisappearedNumbers(new int[]{4, 3, 2, 7, 8, 2, 3, 1})
                .equals(Arrays.asList(5, 6));
        assert findDisappearedNumbers(new int[]{1, 1}).equals(Arrays.asList(2));
        assert findDisappearedNumbers(new int[]{1, 2, 3}).isEmpty();
        System.out.println("DisappearedNumbers: OK");
    }
}
