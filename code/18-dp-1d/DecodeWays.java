public class DecodeWays {

    // dp[i] = ways to decode the first i characters. Each position can close
    // a 1-digit letter (1-9) or a 2-digit letter (10-26) — climbing stairs
    // with validity conditions on each "step".
    public static int numDecodings(String s) {
        int n = s.length();
        if (n == 0 || s.charAt(0) == '0') return 0;   // leading zero: dead
        int prev2 = 1;                  // dp[0]: empty prefix, one way
        int prev1 = 1;                  // dp[1]: first char (non-zero) decodes
        for (int i = 2; i <= n; i++) {
            int curr = 0;
            if (s.charAt(i - 1) != '0')               // 1-digit step "X"
                curr += prev1;
            int two = (s.charAt(i - 2) - '0') * 10 + (s.charAt(i - 1) - '0');
            if (two >= 10 && two <= 26)               // 2-digit step "XY"
                curr += prev2;
            if (curr == 0) return 0;                  // stuck (e.g. "30")
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }

    public static void main(String[] args) {
        assert numDecodings("12") == 2;      // AB or L
        assert numDecodings("226") == 3;     // BZ, VF, BBF
        assert numDecodings("06") == 0;
        assert numDecodings("10") == 1;
        assert numDecodings("100") == 0;     // the second 0 has no partner
        assert numDecodings("11106") == 2;
        assert numDecodings("27") == 1;      // 27 > 26: only 2,7
        System.out.println("DecodeWays: OK");
    }
}
