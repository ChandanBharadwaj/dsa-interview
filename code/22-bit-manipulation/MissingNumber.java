// LeetCode 268 — Missing Number. nums holds n distinct values from 0..n with one
// missing. XOR version: XOR every index 0..n with every value — each present
// number pairs up with its equal index and cancels; only the missing one is left
// unpaired. No overflow risk (unlike the sum formula) and O(1) space.
public class MissingNumber {

    public static int missingNumber(int[] nums) {
        int acc = nums.length;               // seed with n: the index that has no array slot
        for (int i = 0; i < nums.length; i++) {
            acc ^= i;                        // fold in the index 0..n-1
            acc ^= nums[i];                  // fold in the value; value==index pairs cancel out
        }
        return acc;                          // every number in 0..n appeared twice except the missing one
    }

    public static void main(String[] args) {
        assert missingNumber(new int[]{3, 0, 1}) == 2;
        assert missingNumber(new int[]{0, 1}) == 2;
        assert missingNumber(new int[]{9, 6, 4, 2, 3, 5, 7, 0, 1}) == 8;
        assert missingNumber(new int[]{0}) == 1;
        System.out.println("MissingNumber: OK");
    }
}
