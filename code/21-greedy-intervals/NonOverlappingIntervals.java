import java.util.Arrays;

// LeetCode 435 — Non-overlapping Intervals. Activity selection: sort by END,
// greedily keep every interval that doesn't clash; removals = total - kept.
public class NonOverlappingIntervals {

    public static int eraseOverlapIntervals(int[][] intervals) {
        // Sort by END time: the interval finishing earliest leaves the most room
        // for everything after it (exchange argument: any optimal schedule can
        // swap its first pick for the earliest-ending one without losing a slot).
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
        int kept = 0;                                   // size of the max non-overlapping set
        int end = Integer.MIN_VALUE;                    // end of the last interval we kept
        for (int[] iv : intervals) {
            if (iv[0] >= end) {                         // starts at/after last end -> no overlap, keep it
                kept++;
                end = iv[1];                            // future picks must start at/after this
            }                                           // else: overlaps the kept set -> it's a removal
        }
        return intervals.length - kept;                 // minimum removals = everything we couldn't keep
    }

    public static void main(String[] args) {
        assert eraseOverlapIntervals(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 3}}) == 1;
        assert eraseOverlapIntervals(new int[][]{{1, 2}, {1, 2}, {1, 2}}) == 2;
        assert eraseOverlapIntervals(new int[][]{{1, 2}, {2, 3}}) == 0; // touching is fine here
        System.out.println("NonOverlappingIntervals: OK");
    }
}
