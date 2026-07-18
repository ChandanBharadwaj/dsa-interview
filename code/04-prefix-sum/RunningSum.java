import java.util.Arrays;

/**
 * LeetCode 1480 — Running Sum of 1d Array. The prefix-sum build loop
 * in isolation. Mirrors pages/04-prefix-sum.html.
 */
public class RunningSum {

    public static int[] runningSum(int[] nums) {
        int[] sum = new int[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++)
            sum[i] = sum[i - 1] + nums[i];   // each entry = previous total + one element
        return sum;
    }

    public static void main(String[] args) {
        assert Arrays.equals(runningSum(new int[]{1, 2, 3, 4}), new int[]{1, 3, 6, 10});
        assert Arrays.equals(runningSum(new int[]{3, 1, 2, 10, 1}), new int[]{3, 4, 6, 16, 17});
        assert Arrays.equals(runningSum(new int[]{5}), new int[]{5});
        System.out.println("RunningSum: OK");
    }
}
