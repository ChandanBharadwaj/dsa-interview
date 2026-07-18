public class BitwiseAndRange {

    // AND over [left, right] keeps exactly the bits where left and right
    // AGREE from the top down — everything below the first disagreement
    // cycles through 0 somewhere in the range. Find the common prefix.
    public static int rangeBitwiseAnd(int left, int right) {
        int shift = 0;
        while (left != right) {         // strip differing low bits
            left >>= 1;
            right >>= 1;
            shift++;
        }
        return left << shift;           // common high prefix, zero-padded
    }

    public static void main(String[] args) {
        assert rangeBitwiseAnd(5, 7) == 4;        // 101 & 110 & 111 = 100
        assert rangeBitwiseAnd(0, 0) == 0;
        assert rangeBitwiseAnd(1, 2147483647) == 0;
        assert rangeBitwiseAnd(10, 11) == 10;     // 1010 & 1011
        assert rangeBitwiseAnd(6, 6) == 6;        // single-element range
        // brute-force cross-check on small ranges
        for (int lo = 0; lo <= 40; lo++)
            for (int hi = lo; hi <= 40; hi++) {
                int brute = lo;
                for (int x = lo + 1; x <= hi; x++) brute &= x;
                assert rangeBitwiseAnd(lo, hi) == brute;
            }
        System.out.println("BitwiseAndRange: OK");
    }
}
