/**
 * LeetCode 50 — Pow(x, n), via exponentiation by squaring.
 * O(log n) multiplications; handles n = Integer.MIN_VALUE by widening to long
 * BEFORE negating. Mirrors pages/23-math-geometry.html.
 */
public class FastPower {

    public static double myPow(double x, int n) {
        long e = n;                       // widen BEFORE negating: -(MIN_VALUE) overflows int
        if (e < 0) {
            x = 1 / x;                    // x^-e = (1/x)^e
            e = -e;
        }
        double result = 1;
        while (e > 0) {
            if ((e & 1) == 1)             // this bit of the exponent is set
                result *= x;              // fold the current power of x into the answer
            x *= x;                       // x -> x^2 -> x^4 -> x^8 ...
            e >>= 1;                      // move to the next bit
        }
        return result;
    }

    // Bonus: the modular version used for "answer mod 1e9+7" problems (and by RSA).
    public static long powMod(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1)
                result = result * base % mod;
            base = base * base % mod;
            exp >>= 1;
        }
        return result;
    }

    public static void main(String[] args) {
        assert Math.abs(myPow(2.0, 10) - 1024.0) < 1e-9;
        assert Math.abs(myPow(2.0, -2) - 0.25) < 1e-9;
        assert Math.abs(myPow(2.0, 0) - 1.0) < 1e-9;
        assert myPow(1.0, Integer.MIN_VALUE) == 1.0;   // the overflow trap case
        assert powMod(2, 10, 1_000_000_007L) == 1024;
        assert powMod(3, 200, 50) == powModNaive(3, 200, 50);
        System.out.println("FastPower: OK");
    }

    private static long powModNaive(long b, int e, long m) {
        long r = 1;
        for (int i = 0; i < e; i++) r = r * b % m;
        return r;
    }
}
