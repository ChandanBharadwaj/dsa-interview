import java.util.*;

public class Permutations {

    // LeetCode 46. Return ALL orderings of an array of distinct ints.
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // used[j] = "nums[j] is already placed somewhere in the current path"
        backtrack(nums, new boolean[nums.length], new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(int[] nums, boolean[] used, List<Integer> path,
                                  List<List<Integer>> result) {
        // Base case: every slot is filled -> path is one complete permutation.
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path)); // copy — path keeps mutating
            return;
        }
        // Unlike subsets, EVERY still-unused element is a candidate for the
        // next slot (order matters, so we don't restrict to "later" indices).
        for (int j = 0; j < nums.length; j++) {
            if (used[j]) continue;      // prune: already placed in this branch
            used[j] = true;             // choose nums[j] for the current slot
            path.add(nums[j]);
            backtrack(nums, used, path, result);   // explore deeper
            path.remove(path.size() - 1);          // un-choose (mirror order!)
            used[j] = false;
        }
    }

    public static void main(String[] args) {
        // 3 elements -> 3! = 6 permutations
        List<List<Integer>> r = permute(new int[]{1, 2, 3});
        assert r.size() == 6 : "expected 6 permutations, got " + r.size();
        assert r.contains(List.of(1, 2, 3));
        assert r.contains(List.of(3, 1, 2));
        // all permutations must be distinct
        assert new HashSet<>(r).size() == 6;
        assert permute(new int[]{7}).equals(List.of(List.of(7)));
        System.out.println("Permutations: OK");
    }
}
