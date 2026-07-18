import java.util.*;

public class Subsets {

    // LeetCode 78. Return ALL subsets (the power set) of a set of distinct ints.
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // path = the choices made so far on the way down one branch
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(int[] nums, int i, List<Integer> path,
                                  List<List<Integer>> result) {
        // Base case: we have made a decision for EVERY element -> path is one
        // complete subset. Copy it (path itself keeps mutating afterwards!).
        if (i == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        // Branch 1: EXCLUDE nums[i]. No state change, just move on.
        backtrack(nums, i + 1, path, result);

        // Branch 2: INCLUDE nums[i].
        path.add(nums[i]);                  // choose
        backtrack(nums, i + 1, path, result); // explore
        path.remove(path.size() - 1);       // un-choose: restore state for caller
    }

    public static void main(String[] args) {
        // 3 elements -> 2^3 = 8 subsets
        List<List<Integer>> r = subsets(new int[]{1, 2, 3});
        assert r.size() == 8 : "expected 8 subsets, got " + r.size();
        assert r.contains(List.of()) : "must contain the empty subset";
        assert r.contains(List.of(1, 2, 3)) : "must contain the full set";
        assert r.contains(List.of(1, 3)) : "must contain {1,3}";
        // 0 elements -> just the empty subset
        assert subsets(new int[]{}).size() == 1;
        System.out.println("Subsets: OK");
    }
}
