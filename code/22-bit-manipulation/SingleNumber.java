// LeetCode 136 — Single Number. Every element appears twice except one; find it
// in O(n) time and O(1) space. XOR is the whole trick: a^a=0 and a^0=a, and XOR
// is commutative, so the pairs annihilate no matter how the array is ordered.
public class SingleNumber {

    public static int singleNumber(int[] nums) {
        int acc = 0;                     // XOR identity: a ^ 0 = a
        for (int x : nums) {
            acc ^= x;                    // pairs cancel (a ^ a = 0); the loner survives
        }
        return acc;                      // all duplicates vanished — only the single remains
    }

    public static void main(String[] args) {
        assert singleNumber(new int[]{2, 2, 1}) == 1;
        assert singleNumber(new int[]{4, 1, 2, 1, 2}) == 4;
        assert singleNumber(new int[]{-3, 7, -3}) == 7;   // works for negatives: XOR is bit-blind
        System.out.println("SingleNumber: OK");
    }
}
