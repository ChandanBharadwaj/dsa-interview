/**
 * LeetCode 191 — Number of 1 Bits. 32-position scan vs Kernighan's
 * clear-the-lowest-set-bit loop. Mirrors pages/22-bit-manipulation.html.
 */
public class NumberOf1Bits {

    public static int hammingWeightScan(int n) {
        int count = 0;
        for (int i = 0; i < 32; i++)
            count += (n >>> i) & 1;      // >>> (unsigned shift): works for negatives too
        return count;
    }

    public static int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            n &= n - 1;    // n-1 flips the lowest 1 and everything under it;
            count++;       // AND-ing erases exactly that lowest 1
        }
        return count;      // loop ran once per set bit — not per bit position
    }

    public static void main(String[] args) {
        assert hammingWeight(11) == 3;
        assert hammingWeight(128) == 1;
        assert hammingWeight(-3) == 31;
        assert hammingWeightScan(-3) == 31;
        assert hammingWeight(0) == 0;
        assert hammingWeight(-1) == 32;
        System.out.println("NumberOf1Bits: OK");
    }
}
