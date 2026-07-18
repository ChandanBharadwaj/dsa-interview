public class SortedSquares {

    // The largest square lives at one END of a sorted array (most-negative or
    // most-positive). Two pointers compare the ends and fill the result from
    // the BACK — one pass, already sorted.
    public static int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int[] out = new int[n];
        int left = 0, right = n - 1;
        for (int fill = n - 1; fill >= 0; fill--) {   // fill largest-first
            int ls = nums[left] * nums[left];
            int rs = nums[right] * nums[right];
            if (ls > rs) {
                out[fill] = ls;
                left++;                                // consumed the left end
            } else {
                out[fill] = rs;
                right--;                               // consumed the right end
            }
        }
        return out;
    }

    public static void main(String[] args) {
        assert java.util.Arrays.equals(sortedSquares(new int[]{-4, -1, 0, 3, 10}),
                new int[]{0, 1, 9, 16, 100});
        assert java.util.Arrays.equals(sortedSquares(new int[]{-7, -3, 2, 3, 11}),
                new int[]{4, 9, 9, 49, 121});
        System.out.println("SortedSquares: OK");
    }
}
