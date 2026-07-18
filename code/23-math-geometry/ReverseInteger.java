public class ReverseInteger {

    // Reverse the digits; return 0 on 32-bit overflow — and detect the
    // overflow BEFORE it happens, since it would corrupt the value.
    public static int reverse(int x) {
        int result = 0;
        while (x != 0) {
            int digit = x % 10;         // works for negatives: -123 % 10 = -3
            x /= 10;
            // about to compute result * 10 + digit — check it fits first
            if (result > Integer.MAX_VALUE / 10
                    || (result == Integer.MAX_VALUE / 10 && digit > 7))
                return 0;
            if (result < Integer.MIN_VALUE / 10
                    || (result == Integer.MIN_VALUE / 10 && digit < -8))
                return 0;
            result = result * 10 + digit;
        }
        return result;
    }

    public static void main(String[] args) {
        assert reverse(123) == 321;
        assert reverse(-123) == -321;
        assert reverse(120) == 21;
        assert reverse(0) == 0;
        assert reverse(1534236469) == 0;    // reversal overflows -> 0
        assert reverse(-2147483648) == 0;
        assert reverse(2147483647) == 0;    // 7463847412 > MAX
        System.out.println("ReverseInteger: OK");
    }
}
