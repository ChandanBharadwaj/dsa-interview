/**
 * LeetCode 137 — Single Number II (others appear three times). Per-bit
 * count mod 3, and the ones/twos state-machine compression.
 * Mirrors pages/22-bit-manipulation.html.
 */
public class SingleNumberII {

    public static int singleNumberBitCount(int[] nums) {
        int result = 0;
        for (int bit = 0; bit < 32; bit++) {
            int count = 0;
            for (int x : nums)
                count += (x >>> bit) & 1;      // how many numbers have this bit set?
            // Triples contribute multiples of 3; a remainder means the
            // single number owns this bit.
            if (count % 3 != 0)
                result |= 1 << bit;
        }
        return result;                          // works for negatives: bit 31 included
    }

    public static int singleNumber(int[] nums) {
        // Per bit position, (twos, ones) counts appearances mod 3:
        // 00 -> 01 -> 10 -> 00. The update formulas implement that cycle
        // for all 32 positions in parallel.
        int ones = 0, twos = 0;
        for (int x : nums) {
            ones = (ones ^ x) & ~twos;   // enter `ones` unless already seen twice
            twos = (twos ^ x) & ~ones;   // enter `twos` when leaving `ones`
        }
        return ones;                     // bits seen 3k+1 times = the single number
    }

    public static void main(String[] args) {
        assert singleNumber(new int[]{2, 2, 3, 2}) == 3;
        assert singleNumber(new int[]{0, 1, 0, 1, 0, 1, 99}) == 99;
        assert singleNumber(new int[]{-2, -2, -2, -7}) == -7;
        assert singleNumberBitCount(new int[]{2, 2, 3, 2}) == 3;
        assert singleNumberBitCount(new int[]{-2, -2, -2, -7}) == -7;
        System.out.println("SingleNumberII: OK");
    }
}
