import java.util.Arrays;

// LeetCode 167 — Two Sum II (Input Array Is Sorted)
// Pattern: converging pointers. Sortedness lets each comparison
// discard one end of the search space for good.
public class TwoSumII {

    public static int[] twoSum(int[] numbers, int target) {
        int l = 0, r = numbers.length - 1;   // start at the two extremes
        while (l < r) {
            int sum = numbers[l] + numbers[r];
            if (sum == target) {
                // Problem wants 1-indexed positions
                return new int[]{l + 1, r + 1};
            }
            if (sum < target) {
                // Sum too small. numbers[r] is already the LARGEST partner
                // available for numbers[l], so numbers[l] can never work —
                // discard it forever by moving l right.
                l++;
            } else {
                // Sum too big. numbers[l] is the SMALLEST partner available
                // for numbers[r], so numbers[r] can never work — discard it.
                r--;
            }
        }
        // Problem guarantees a solution; unreachable on valid input
        return new int[]{-1, -1};
    }
    // Time O(n): pointers move toward each other, n steps total.
    // Space O(1): two ints — this is why the problem forbids the HashMap.

    public static void main(String[] args) {
        assert Arrays.equals(twoSum(new int[]{2, 7, 11, 15}, 9), new int[]{1, 2});
        assert Arrays.equals(twoSum(new int[]{1, 3, 4, 6, 8, 11}, 10), new int[]{3, 4});
        assert Arrays.equals(twoSum(new int[]{-1, 0}, -1), new int[]{1, 2});
        System.out.println("TwoSumII: OK");
    }
}
