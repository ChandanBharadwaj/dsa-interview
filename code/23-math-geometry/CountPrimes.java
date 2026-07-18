/**
 * LeetCode 204 — Count Primes, via the sieve of Eratosthenes.
 * O(n log log n) time, O(n) space. Mirrors pages/23-math-geometry.html.
 */
public class CountPrimes {

    public static int countPrimes(int n) {
        if (n < 3) return 0;                       // no primes below 2
        boolean[] composite = new boolean[n];      // false = presumed prime
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (composite[i]) continue;            // already crossed out — skip
            count++;                               // i survived every smaller prime -> prime
            // Cross out multiples starting at i*i: anything below that, like 2*i or 3*i,
            // was already handled by the smaller prime factor (2, 3, ...).
            for (long m = (long) i * i; m < n; m += i)  // long: i*i overflows int for i ~ 50k
                composite[(int) m] = true;
        }
        return count;
    }

    public static void main(String[] args) {
        assert countPrimes(10) == 4;       // 2, 3, 5, 7
        assert countPrimes(0) == 0;
        assert countPrimes(1) == 0;
        assert countPrimes(2) == 0;        // primes STRICTLY below n
        assert countPrimes(50) == 15;      // matches the page's animation
        assert countPrimes(1_000_000) == 78_498;
        System.out.println("CountPrimes: OK");
    }
}
