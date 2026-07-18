import java.util.Arrays;

/**
 * LeetCode 238 — Product of Array Except Self. Two variations from the page:
 * two prefix/suffix arrays, then the O(1)-extra-space output-reuse version.
 * Mirrors pages/01-arrays-hashing.html.
 */
public class ProductExceptSelf {

    public static int[] productExceptSelfTwoArrays(int[] nums) {
        int n = nums.length;
        int[] left = new int[n], right = new int[n], ans = new int[n];
        left[0] = 1;                                    // nothing to the left of index 0
        for (int i = 1; i < n; i++)
            left[i] = left[i - 1] * nums[i - 1];        // product of nums[0..i-1]
        right[n - 1] = 1;                               // nothing to the right of the end
        for (int i = n - 2; i >= 0; i--)
            right[i] = right[i + 1] * nums[i + 1];      // product of nums[i+1..n-1]
        for (int i = 0; i < n; i++)
            ans[i] = left[i] * right[i];
        return ans;
    }

    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        ans[0] = 1;
        for (int i = 1; i < n; i++)                 // pass 1: ans[i] = product of the left side
            ans[i] = ans[i - 1] * nums[i - 1];
        int right = 1;                              // running product of the right side
        for (int i = n - 1; i >= 0; i--) {
            ans[i] *= right;                        // left-product * right-product
            right *= nums[i];                       // extend the right side to include nums[i]
        }
        return ans;                                 // output array doesn't count as extra space
    }

    public static void main(String[] args) {
        assert Arrays.equals(productExceptSelf(new int[]{1, 2, 3, 4}), new int[]{24, 12, 8, 6});
        assert Arrays.equals(productExceptSelf(new int[]{-1, 1, 0, -3, 3}), new int[]{0, 0, 9, 0, 0});
        assert Arrays.equals(productExceptSelfTwoArrays(new int[]{1, 2, 3, 4}), new int[]{24, 12, 8, 6});
        System.out.println("ProductExceptSelf: OK");
    }
}
