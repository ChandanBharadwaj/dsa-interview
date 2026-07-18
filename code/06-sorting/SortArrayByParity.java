public class SortArrayByParity {

    // A two-way partition — quicksort's partition step with "is even" as the
    // pivot test instead of a value comparison.
    public static int[] sortArrayByParity(int[] nums) {
        int write = 0;                          // boundary: [0, write) is even
        for (int read = 0; read < nums.length; read++)
            if (nums[read] % 2 == 0) {
                int t = nums[read];             // swap the even back to the boundary
                nums[read] = nums[write];
                nums[write++] = t;
            }
        return nums;
    }

    public static void main(String[] args) {
        int[] a = sortArrayByParity(new int[]{3, 1, 2, 4});
        int i = 0;
        while (i < a.length && a[i] % 2 == 0) i++;   // evens first...
        while (i < a.length && a[i] % 2 == 1) i++;   // ...then odds, nothing else
        assert i == a.length;
        assert sortArrayByParity(new int[]{0})[0] == 0;
        System.out.println("SortArrayByParity: OK");
    }
}
