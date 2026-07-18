/**
 * LeetCode 278 — First Bad Version. Template B over a monotonic predicate
 * (no array at all — the git-bisect shape). Mirrors pages/07-binary-search.html.
 */
public class FirstBadVersion {

    static int firstBad;                       // test fixture: the hidden answer
    static int apiCalls;

    static boolean isBadVersion(int v) {       // the given API
        apiCalls++;
        return v >= firstBad;
    }

    public static int firstBadVersion(int n) {
        int lo = 1, hi = n;                    // answer is guaranteed to exist in [1..n]
        while (lo < hi) {                      // Template B guard: shrink to one candidate
            int mid = lo + (hi - lo) / 2;
            if (isBadVersion(mid)) {
                hi = mid;                      // mid is bad — it COULD be the first bad; keep it
            } else {
                lo = mid + 1;                  // mid is good — first bad is strictly later
            }
        }
        return lo;                             // lo == hi == the first bad version
    }

    public static void main(String[] args) {
        firstBad = 4;
        assert firstBadVersion(5) == 4;
        firstBad = 1;
        assert firstBadVersion(1) == 1;
        firstBad = 1_000_000_000;
        apiCalls = 0;
        assert firstBadVersion(2_000_000_000) == 1_000_000_000;
        assert apiCalls <= 31;                 // truly logarithmic
        System.out.println("FirstBadVersion: OK");
    }
}
