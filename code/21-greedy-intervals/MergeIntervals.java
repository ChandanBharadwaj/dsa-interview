import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// LeetCode 56 — Merge Intervals. Sort by START, then sweep once merging overlaps.
public class MergeIntervals {

    public static int[][] merge(int[][] intervals) {
        // Sort by start. Integer.compare avoids the subtraction-overflow bug of (a[0] - b[0]).
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> out = new ArrayList<>();
        int[] cur = intervals[0];                       // the interval we are currently growing
        for (int i = 1; i < intervals.length; i++) {
            int[] next = intervals[i];
            if (next[0] <= cur[1]) {                    // starts before cur ends -> overlap (touching counts)
                cur[1] = Math.max(cur[1], next[1]);     // extend; max() because next may be nested inside cur
            } else {                                    // gap -> cur can never grow again (starts only increase)
                out.add(cur);                           // commit the finished merged interval
                cur = next;                             // start growing a fresh one
            }
        }
        out.add(cur);                                   // don't forget the last open interval
        return out.toArray(new int[0][]);
    }

    public static void main(String[] args) {
        assert Arrays.deepEquals(
                merge(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}}),
                new int[][]{{1, 6}, {8, 10}, {15, 18}});
        assert Arrays.deepEquals(                        // touching intervals merge
                merge(new int[][]{{1, 4}, {4, 5}}),
                new int[][]{{1, 5}});
        assert Arrays.deepEquals(                        // nested interval is swallowed
                merge(new int[][]{{1, 10}, {2, 3}}),
                new int[][]{{1, 10}});
        System.out.println("MergeIntervals: OK");
    }
}
