import java.util.*;

public class SubarraysDivByK {

    // A subarray (i, j] sums to a multiple of k exactly when
    // prefix[j] % k == prefix[i] % k — so count prefixes by remainder class.
    public static int subarraysDivByK(int[] nums, int k) {
        int[] byRemainder = new int[k];
        byRemainder[0] = 1;             // empty prefix: remainder 0 seen once
        int prefix = 0, count = 0;
        for (int x : nums) {
            prefix += x;
            int r = ((prefix % k) + k) % k;   // Java's % is negative for negatives
            count += byRemainder[r];    // pair with every earlier equal remainder
            byRemainder[r]++;
        }
        return count;
    }

    public static void main(String[] args) {
        assert subarraysDivByK(new int[]{4, 5, 0, -2, -3, 1}, 5) == 7;
        assert subarraysDivByK(new int[]{5}, 9) == 0;
        assert subarraysDivByK(new int[]{-1, 2, 9}, 2) == 2;
        System.out.println("SubarraysDivByK: OK");
    }
}
