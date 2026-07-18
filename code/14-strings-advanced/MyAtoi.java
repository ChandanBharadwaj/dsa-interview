public class MyAtoi {

    // Parse in the mandated order: whitespace, one optional sign, digits,
    // stop at anything else — clamping to int range DURING the loop.
    public static int myAtoi(String s) {
        int i = 0, n = s.length();
        while (i < n && s.charAt(i) == ' ') i++;        // 1. leading spaces

        int sign = 1;
        if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
            sign = s.charAt(i) == '-' ? -1 : 1;         // 2. at most one sign
            i++;
        }

        long value = 0;                                  // long: safe headroom
        while (i < n && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
            value = value * 10 + (s.charAt(i) - '0');
            // 3. clamp EARLY — value can't be allowed to overflow even long
            if (sign == 1 && value > Integer.MAX_VALUE) return Integer.MAX_VALUE;
            if (sign == -1 && -value < Integer.MIN_VALUE) return Integer.MIN_VALUE;
            i++;
        }
        return (int) (sign * value);
    }

    public static void main(String[] args) {
        assert myAtoi("42") == 42;
        assert myAtoi("   -042") == -42;
        assert myAtoi("1337c0d3") == 1337;    // stop at the first non-digit
        assert myAtoi("0-1") == 0;
        assert myAtoi("words and 987") == 0;  // no leading digits at all
        assert myAtoi("-91283472332") == Integer.MIN_VALUE;   // clamp low
        assert myAtoi("91283472332") == Integer.MAX_VALUE;    // clamp high
        assert myAtoi("+-12") == 0;           // second sign is a terminator
        assert myAtoi("") == 0;
        System.out.println("MyAtoi: OK");
    }
}
