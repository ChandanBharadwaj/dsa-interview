// LeetCode 875 — Koko Eating Bananas
// Pattern: binary search on the ANSWER space, not the input array.
// The predicate canFinish(speed) is monotonic: false,false,...,true,true.
// We want the FIRST speed that is true — the boundary template again.
public class KokoBananas {

    public static int minEatingSpeed(int[] piles, int h) {
        int lo = 1, hi = 0;                    // candidate speeds: 1 .. max(pile)
        for (int p : piles) hi = Math.max(hi, p);  // eating faster than the biggest pile is pointless
        while (lo < hi) {                      // "first true" template: shrink to a single candidate
            int mid = lo + (hi - lo) / 2;      // overflow-safe midpoint speed to test
            if (canFinish(piles, mid, h)) {
                hi = mid;                      // mid works — the minimum is mid or something slower
            } else {
                lo = mid + 1;                  // mid too slow — minimum must be faster
            }
        }
        return lo;                             // lo == hi == slowest speed that still finishes in h hours
    }

    // The predicate: can Koko finish every pile within h hours at this speed?
    static boolean canFinish(int[] piles, int speed, int h) {
        long hours = 0;                        // long: up to 10^4 piles * 10^9 bananas — int would overflow
        for (int p : piles) {
            hours += (p + (long) speed - 1) / speed;   // ceil(p / speed) without floating point
        }
        return hours <= h;                     // monotonic: a faster speed never needs MORE hours
    }

    public static void main(String[] args) {
        assert minEatingSpeed(new int[]{3,6,7,11}, 8) == 4;
        assert minEatingSpeed(new int[]{30,11,23,4,20}, 5) == 30;
        assert minEatingSpeed(new int[]{30,11,23,4,20}, 6) == 23;
        assert minEatingSpeed(new int[]{1000000000}, 2) == 500000000;
        System.out.println("KokoBananas: OK");
    }
}
