/**
 * LeetCode 190 — Reverse Bits. Peel the lowest bit, push onto the output.
 * Mirrors pages/22-bit-manipulation.html.
 */
public class ReverseBits {

    public static int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result <<= 1;              // make room at the bottom of the output
            result |= n & 1;           // move n's lowest bit into it
            n >>>= 1;                  // consume that bit (unsigned shift!)
        }
        return result;
    }

    public static void main(String[] args) {
        assert reverseBits(0b00000010100101000001111010011100) == 0b00111001011110000010100101000000;
        assert reverseBits(0) == 0;
        assert reverseBits(1) == Integer.MIN_VALUE;   // bit 0 -> bit 31
        assert reverseBits(reverseBits(123456789)) == 123456789;
        System.out.println("ReverseBits: OK");
    }
}
