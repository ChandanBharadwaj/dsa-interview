import java.util.*;

public class CombinationSum {

    // LeetCode 39. Distinct candidates, each usable UNLIMITED times.
    // Return all unique combinations summing to target.
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);   // lets us break early once a candidate exceeds what's left
        backtrack(candidates, 0, target, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(int[] cand, int start, int remaining,
                                  List<Integer> path, List<List<Integer>> result) {
        // Base case: we spent the target exactly -> path is a valid combination.
        if (remaining == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        // Only consider candidates from `start` onward. This is THE trick that
        // avoids duplicates: [2,3] and [3,2] can never both appear, because once
        // we move past 2 we never pick it again.
        for (int i = start; i < cand.length; i++) {
            if (cand[i] > remaining) break; // prune: sorted, so the rest are bigger too
            path.add(cand[i]);              // choose
            // pass `i`, NOT `i + 1`: same element may be reused (unlimited supply)
            backtrack(cand, i, remaining - cand[i], path, result);
            path.remove(path.size() - 1);   // un-choose
        }
    }

    public static void main(String[] args) {
        // Classic example: [2,3,6,7], target 7 -> [[2,2,3],[7]]
        List<List<Integer>> r = combinationSum(new int[]{2, 3, 6, 7}, 7);
        assert r.size() == 2 : "expected 2 combinations, got " + r.size();
        assert r.contains(List.of(2, 2, 3));
        assert r.contains(List.of(7));
        // No way to make 1 from [2] -> empty result
        assert combinationSum(new int[]{2}, 1).isEmpty();
        // [2,3,5], target 8 -> [[2,2,2,2],[2,3,3],[3,5]]
        assert combinationSum(new int[]{2, 3, 5}, 8).size() == 3;
        System.out.println("CombinationSum: OK");
    }
}
