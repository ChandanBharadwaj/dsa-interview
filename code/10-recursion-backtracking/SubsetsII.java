import java.util.*;

public class SubsetsII {

    // Subsets with duplicates in the input: sort, then at each depth skip
    // repeated values — "same choice at the same level" is what makes
    // duplicate subsets, so forbid exactly that.
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);                       // group duplicates together
        List<List<Integer>> res = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), res);
        return res;
    }

    private static void backtrack(int[] nums, int start,
                                  List<Integer> path, List<List<Integer>> res) {
        res.add(new ArrayList<>(path));          // every node IS a subset
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1])
                continue;                        // same value, same level -> dup
            path.add(nums[i]);
            backtrack(nums, i + 1, path, res);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> r = subsetsWithDup(new int[]{1, 2, 2});
        assert r.size() == 6;    // [], [1], [1,2], [1,2,2], [2], [2,2]
        assert r.contains(Arrays.asList(1, 2, 2)) && r.contains(Arrays.asList(2, 2));
        assert subsetsWithDup(new int[]{0}).size() == 2;
        assert subsetsWithDup(new int[]{2, 2, 2}).size() == 4;
        System.out.println("SubsetsII: OK");
    }
}
