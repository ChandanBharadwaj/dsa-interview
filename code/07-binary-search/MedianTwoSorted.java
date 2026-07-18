/**
 * LeetCode 4 — Median of Two Sorted Arrays. Binary search the partition
 * cut in the shorter array: O(log min(m,n)).
 * Mirrors pages/07-binary-search.html.
 */
public class MedianTwoSorted {

    public static double findMedianSortedArrays(int[] A, int[] B) {
        if (A.length > B.length) return findMedianSortedArrays(B, A);  // search the SHORTER array
        int m = A.length, n = B.length;
        int half = (m + n + 1) / 2;            // size of the combined left part (+1 favors left on odd)

        int lo = 0, hi = m;                    // i = how many of A's elements go left (0..m)
        while (lo <= hi) {
            int i = lo + (hi - lo) / 2;        // cut in A
            int j = half - i;                  // cut in B is forced: left parts must total `half`

            // The four border values (±infinity when a cut touches an edge)
            int aLeft  = (i == 0) ? Integer.MIN_VALUE : A[i - 1];
            int aRight = (i == m) ? Integer.MAX_VALUE : A[i];
            int bLeft  = (j == 0) ? Integer.MIN_VALUE : B[j - 1];
            int bRight = (j == n) ? Integer.MAX_VALUE : B[j];

            if (aLeft <= bRight && bLeft <= aRight) {
                // Valid partition: all-left <= all-right. Median from the borders:
                if (((m + n) & 1) == 1)
                    return Math.max(aLeft, bLeft);                    // odd total: left part's max
                return (Math.max(aLeft, bLeft) + Math.min(aRight, bRight)) / 2.0;
            }
            if (aLeft > bRight) hi = i - 1;    // A contributes too much — move A's cut left
            else lo = i + 1;                   // A contributes too little — move it right
        }
        throw new IllegalArgumentException("inputs not sorted");
    }

    public static void main(String[] args) {
        assert findMedianSortedArrays(new int[]{1, 3}, new int[]{2}) == 2.0;
        assert findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}) == 2.5;
        assert findMedianSortedArrays(new int[]{}, new int[]{1}) == 1.0;
        assert findMedianSortedArrays(new int[]{2, 2, 4, 4}, new int[]{2, 2, 4, 4}) == 3.0;
        System.out.println("MedianTwoSorted: OK");
    }
}
