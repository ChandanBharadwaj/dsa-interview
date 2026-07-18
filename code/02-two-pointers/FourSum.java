import java.util.*;

public class FourSum {

    // 3Sum with one more fixed layer: fix i and j, two-pointer the rest.
    // Same dedup discipline at every level.
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;
        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;      // skip duplicate i
            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;  // duplicate j
                int left = j + 1, right = n - 1;
                while (left < right) {
                    // long: four ints can overflow int range
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        left++;
                        right--;
                        while (left < right && nums[left] == nums[left - 1]) left++;    // dedup
                        while (left < right && nums[right] == nums[right + 1]) right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        assert fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0).size() == 3;
        assert fourSum(new int[]{2, 2, 2, 2, 2}, 8).size() == 1;
        // overflow probe: would be wrong with int arithmetic
        assert fourSum(new int[]{1000000000, 1000000000, 1000000000, 1000000000}, -294967296).isEmpty();
        System.out.println("FourSum: OK");
    }
}
