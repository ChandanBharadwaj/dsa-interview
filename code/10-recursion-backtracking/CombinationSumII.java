import java.util.*;

public class CombinationSumII {

    // Each candidate usable ONCE, duplicates in input, no duplicate results:
    // sort + same-level skip (like SubsetsII) + advance to i+1 (single use).
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> res = new ArrayList<>();
        backtrack(candidates, 0, target, new ArrayList<>(), res);
        return res;
    }

    private static void backtrack(int[] cand, int start, int remain,
                                  List<Integer> path, List<List<Integer>> res) {
        if (remain == 0) {                       // exact hit — record
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < cand.length; i++) {
            if (i > start && cand[i] == cand[i - 1])
                continue;                        // dedup at this level
            if (cand[i] > remain)
                break;                           // sorted: everything after is too big
            path.add(cand[i]);
            backtrack(cand, i + 1, remain - cand[i], path, res);  // i+1: use once
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> r = combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8);
        assert r.size() == 4;    // [1,1,6] [1,2,5] [1,7] [2,6]
        assert r.contains(Arrays.asList(1, 1, 6)) && r.contains(Arrays.asList(1, 7));
        assert combinationSum2(new int[]{2, 5, 2, 1, 2}, 5).size() == 2;  // [1,2,2] [5]
        System.out.println("CombinationSumII: OK");
    }
}
