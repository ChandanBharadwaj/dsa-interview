/**
 * LeetCode 1011 — Capacity To Ship Packages Within D Days.
 * Binary search on capacity + greedy day-count checker.
 * Mirrors pages/07-binary-search.html.
 */
public class ShipCapacity {

    public static int shipWithinDays(int[] weights, int days) {
        // Answer range: at least the heaviest single package (it must fit),
        // at most the total weight (everything in one day).
        int lo = 0, hi = 0;
        for (int w : weights) { lo = Math.max(lo, w); hi += w; }

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;      // candidate capacity
            if (daysNeeded(weights, mid) <= days) hi = mid;   // enough — try smaller
            else lo = mid + 1;                                // too small — grow it
        }
        return lo;
    }

    // Greedy checker: fill each day as full as order allows, count the days.
    static int daysNeeded(int[] weights, int capacity) {
        int days = 1, load = 0;
        for (int w : weights) {
            if (load + w > capacity) {         // doesn't fit today —
                days++;                        // start a new day
                load = 0;
            }
            load += w;
        }
        return days;
    }

    public static void main(String[] args) {
        assert shipWithinDays(new int[]{1,2,3,4,5,6,7,8,9,10}, 5) == 15;
        assert shipWithinDays(new int[]{3,2,2,4,1,4}, 3) == 6;
        assert shipWithinDays(new int[]{1,2,3,1,1}, 4) == 3;
        System.out.println("ShipCapacity: OK");
    }
}
