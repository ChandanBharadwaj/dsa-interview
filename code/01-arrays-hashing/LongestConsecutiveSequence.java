import java.util.HashSet;
import java.util.Set;

// LeetCode 128 — Longest Consecutive Sequence
// Pattern: seen-set + "only start counting at a run's true start".
// Looks O(n^2) at a glance, but is genuinely O(n).
public class LongestConsecutiveSequence {

    public static int longestConsecutive(int[] nums) {
        // Dump everything in a set: O(1) membership tests, duplicates collapse
        Set<Integer> set = new HashSet<>();
        for (int n : nums) set.add(n);

        int best = 0;
        for (int n : set) {
            // KEY INSIGHT: only count from the START of a run.
            // If n-1 exists, n is mid-run — skip it, the run's start will
            // count it later. This is what keeps the total work O(n).
            if (set.contains(n - 1)) continue;
            // n starts a run: walk forward while consecutive neighbors exist
            int len = 1;
            while (set.contains(n + len)) len++;
            best = Math.max(best, len);
        }
        return best;
    }
    // Time O(n): each element is touched at most twice
    // (once by the outer loop, once inside exactly one run's walk).
    // Space O(n) for the set.

    public static void main(String[] args) {
        assert longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}) == 4;   // 1,2,3,4
        assert longestConsecutive(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}) == 9;
        assert longestConsecutive(new int[]{}) == 0;
        assert longestConsecutive(new int[]{5}) == 1;
        System.out.println("LongestConsecutiveSequence: OK");
    }
}
