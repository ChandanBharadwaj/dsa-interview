import java.util.*;

public class MajorityElement {

    // Variation 1: frequency map — count everything, return the one over n/2.
    public static int majorityByCounting(int[] nums) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : nums) {
            int c = freq.merge(x, 1, Integer::sum); // increment and read back
            if (c > nums.length / 2) return x;      // strictly more than half
        }
        throw new IllegalArgumentException("no majority element");
    }

    // Variation 2: Boyer-Moore voting — O(1) space.
    // Pair off each candidate occurrence against a different value; the true
    // majority (> n/2 copies) can never be fully cancelled out.
    public static int majorityElement(int[] nums) {
        int candidate = 0, count = 0;
        for (int x : nums) {
            if (count == 0) candidate = x;          // previous run cancelled out
            count += (x == candidate) ? 1 : -1;     // vote for or against
        }
        return candidate;                           // guaranteed majority survives
    }

    public static void main(String[] args) {
        assert majorityElement(new int[]{3, 2, 3}) == 3;
        assert majorityElement(new int[]{2, 2, 1, 1, 1, 2, 2}) == 2;
        assert majorityByCounting(new int[]{2, 2, 1, 1, 1, 2, 2}) == 2;
        assert majorityElement(new int[]{1}) == 1;
        System.out.println("MajorityElement: OK");
    }
}
