public class PivotIndex {

    // leftSum grows as we walk; the right side is total − leftSum − nums[i].
    // No arrays needed — the prefix is carried as one running number.
    public static int pivotIndex(int[] nums) {
        int total = 0;
        for (int x : nums) total += x;

        int leftSum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (leftSum == total - leftSum - nums[i])   // left == right
                return i;
            leftSum += nums[i];                          // i joins the left side
        }
        return -1;
    }

    public static void main(String[] args) {
        assert pivotIndex(new int[]{1, 7, 3, 6, 5, 6}) == 3;
        assert pivotIndex(new int[]{1, 2, 3}) == -1;
        assert pivotIndex(new int[]{2, 1, -1}) == 0;    // empty left side counts as 0
        System.out.println("PivotIndex: OK");
    }
}
