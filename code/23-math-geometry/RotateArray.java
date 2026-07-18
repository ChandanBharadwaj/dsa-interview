import java.util.Arrays;

/**
 * LeetCode 189 — Rotate Array. Copy-shift vs the three-reversal trick.
 * Mirrors pages/23-math-geometry.html.
 */
public class RotateArray {

    public static void rotateCopy(int[] nums, int k) {
        int n = nums.length;
        int[] out = new int[n];
        for (int i = 0; i < n; i++)
            out[(i + k) % n] = nums[i];    // every element jumps k slots, wrapping
        System.arraycopy(out, 0, nums, 0, n);
    }

    public static void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;                            // rotations wrap: k = n is a no-op
        reverse(nums, 0, n - 1);           // whole array
        reverse(nums, 0, k - 1);           // first k
        reverse(nums, k, n - 1);           // the rest — done
    }

    private static void reverse(int[] a, int i, int j) {
        while (i < j) {
            int t = a[i]; a[i] = a[j]; a[j] = t;
            i++; j--;
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 7};
        rotate(a, 3);
        assert Arrays.equals(a, new int[]{5, 6, 7, 1, 2, 3, 4});
        int[] b = {-1, -100, 3, 99};
        rotate(b, 2);
        assert Arrays.equals(b, new int[]{3, 99, -1, -100});
        int[] c = {1, 2};
        rotate(c, 4);                      // k > n wraps to no-op
        assert Arrays.equals(c, new int[]{1, 2});
        int[] d = {1, 2, 3, 4, 5, 6, 7};
        rotateCopy(d, 3);
        assert Arrays.equals(d, new int[]{5, 6, 7, 1, 2, 3, 4});
        System.out.println("RotateArray: OK");
    }
}
