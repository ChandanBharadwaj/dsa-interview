import java.util.*;

public class CheckSubarraySum {

    // Same remainder-class trick as SubarraysDivByK, but we need LENGTH ≥ 2:
    // remember the EARLIEST index of each remainder and compare distances.
    public static boolean checkSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> firstAt = new HashMap<>();
        firstAt.put(0, -1);                  // empty prefix, "index" -1
        int prefix = 0;
        for (int i = 0; i < nums.length; i++) {
            prefix += nums[i];
            int r = prefix % k;              // nums are non-negative here
            Integer first = firstAt.putIfAbsent(r, i);  // keep the earliest only
            if (first != null && i - first >= 2)        // same remainder, ≥2 apart
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        assert checkSubarraySum(new int[]{23, 2, 4, 6, 7}, 6);      // [2,4]
        assert checkSubarraySum(new int[]{23, 2, 6, 4, 7}, 6);      // whole array = 42
        assert !checkSubarraySum(new int[]{23, 2, 6, 4, 7}, 13);
        assert !checkSubarraySum(new int[]{1, 0}, 2);               // length-1 zero doesn't count
        assert checkSubarraySum(new int[]{0, 0}, 2);                // sum 0 is a multiple
        System.out.println("CheckSubarraySum: OK");
    }
}
