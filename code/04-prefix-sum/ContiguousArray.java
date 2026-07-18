import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 525 — Contiguous Array. Two variations: brute-force balance
 * scan O(n^2) vs first-seen prefix map O(n) (0 re-encoded as -1).
 * Mirrors pages/04-prefix-sum.html.
 */
public class ContiguousArray {

    public static int findMaxLengthBrute(int[] nums) {
        int best = 0;
        for (int start = 0; start < nums.length; start++) {
            int balance = 0;                       // (#1s) - (#0s) in nums[start..end]
            for (int end = start; end < nums.length; end++) {
                balance += nums[end] == 1 ? 1 : -1;
                if (balance == 0)                  // equal counts
                    best = Math.max(best, end - start + 1);
            }
        }
        return best;
    }

    public static int findMaxLength(int[] nums) {
        // firstSeen.get(b) = earliest index where the running balance was b.
        // Balance b repeating at j means nums[(first index)+1 .. j] is balanced.
        Map<Integer, Integer> firstSeen = new HashMap<>();
        firstSeen.put(0, -1);              // balance 0 "before the array starts"

        int balance = 0, best = 0;
        for (int i = 0; i < nums.length; i++) {
            balance += nums[i] == 1 ? 1 : -1;   // 0 counts as -1
            if (firstSeen.containsKey(balance)) {
                // Same balance seen before -> the stretch between is perfectly mixed.
                // Use the EARLIEST occurrence for the longest stretch.
                best = Math.max(best, i - firstSeen.get(balance));
            } else {
                firstSeen.put(balance, i);      // record only the first time
            }
        }
        return best;
    }

    public static void main(String[] args) {
        assert findMaxLength(new int[]{0, 1}) == 2;
        assert findMaxLength(new int[]{0, 1, 0}) == 2;
        assert findMaxLength(new int[]{0, 0, 1, 0, 1, 1}) == 6;
        assert findMaxLengthBrute(new int[]{0, 0, 1, 0, 1, 1}) == 6;
        assert findMaxLength(new int[]{0}) == 0;
        System.out.println("ContiguousArray: OK");
    }
}
