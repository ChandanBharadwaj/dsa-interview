/**
 * LeetCode 371 — Sum of Two Integers without + or -. The half-adder loop:
 * XOR = per-column sum, AND<<1 = carries; ripple until carries vanish.
 * Mirrors pages/22-bit-manipulation.html.
 */
public class SumTwoIntegers {

    public static int getSum(int a, int b) {
        while (b != 0) {
            int carry = (a & b) << 1;  // positions where BOTH have 1 generate a
                                       // carry into the next column
            a = a ^ b;                 // XOR adds each column, ignoring carries
            b = carry;                 // now add the carries in — repeat until none
        }
        return a;
    }

    public static void main(String[] args) {
        assert getSum(1, 2) == 3;
        assert getSum(-2, 3) == 1;
        assert getSum(-5, -7) == -12;
        assert getSum(0, 0) == 0;
        assert getSum(2147483647, -1) == 2147483646;
        // subtraction via two's complement: a - b = a + (~b + 1)
        assert getSum(10, getSum(~4, 1)) == 6;
        System.out.println("SumTwoIntegers: OK");
    }
}
