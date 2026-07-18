import java.util.*;

public class ThreeSumClosest {

    // 3Sum's engine, different goal: instead of hunting exact zero, keep the
    // best sum seen while the pointer pair chases the target.
    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);                           // pointers need sorted input
        int best = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (Math.abs(sum - target) < Math.abs(best - target))
                    best = sum;                      // strictly closer — keep it
                if (sum == target) return sum;       // can't get closer than exact
                if (sum < target) left++;            // need more -> move left up
                else right--;                        // need less -> move right down
            }
        }
        return best;
    }

    public static void main(String[] args) {
        assert threeSumClosest(new int[]{-1, 2, 1, -4}, 1) == 2;   // -1+2+1
        assert threeSumClosest(new int[]{0, 0, 0}, 1) == 0;
        assert threeSumClosest(new int[]{1, 1, 1, 0}, -100) == 2;
        System.out.println("ThreeSumClosest: OK");
    }
}
