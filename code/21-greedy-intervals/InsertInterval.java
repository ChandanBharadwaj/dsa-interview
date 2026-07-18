import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 57 — Insert Interval. Three phases over the already-sorted
 * disjoint list: before / absorb overlaps / after.
 * Mirrors pages/21-greedy-intervals.html.
 */
public class InsertInterval {

    public static int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> res = new ArrayList<>();
        int i = 0, n = intervals.length;

        // Phase 1: intervals entirely BEFORE the new one (end < newStart)
        while (i < n && intervals[i][1] < newInterval[0])
            res.add(intervals[i++]);

        // Phase 2: everything that overlaps the new interval gets absorbed.
        // Overlap test: interval starts before (or at) the new one's end.
        while (i < n && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);  // stretch left
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);  // stretch right
            i++;
        }
        res.add(newInterval);                    // the merged blob goes in once

        // Phase 3: the untouched tail
        while (i < n)
            res.add(intervals[i++]);

        return res.toArray(new int[0][]);
    }

    public static void main(String[] args) {
        assert Arrays.deepEquals(
            insert(new int[][]{{1, 3}, {6, 9}}, new int[]{2, 5}),
            new int[][]{{1, 5}, {6, 9}});
        assert Arrays.deepEquals(
            insert(new int[][]{{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}}, new int[]{4, 8}),
            new int[][]{{1, 2}, {3, 10}, {12, 16}});
        assert Arrays.deepEquals(
            insert(new int[][]{}, new int[]{5, 7}),
            new int[][]{{5, 7}});
        System.out.println("InsertInterval: OK");
    }
}
