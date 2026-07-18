import java.util.Arrays;
import java.util.PriorityQueue;

// LeetCode 253 — Meeting Rooms II: minimum rooms so no two meetings share one.
// Two classic solutions: a min-heap of room end times, and a +1/-1 sweep line.
public class MeetingRoomsII {

    // Solution A — min-heap of end times: the heap IS the set of occupied rooms.
    public static int minMeetingRoomsHeap(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0])); // process meetings in start order
        PriorityQueue<Integer> endTimes = new PriorityQueue<>();       // min-heap: earliest-freeing room on top
        for (int[] m : intervals) {
            // If the room that frees earliest is free before we start, reuse it
            if (!endTimes.isEmpty() && endTimes.peek() <= m[0]) {
                endTimes.poll();                       // that meeting ended -> room released
            }
            endTimes.offer(m[1]);                      // our meeting now occupies a room until m[1]
        }
        return endTimes.size();                        // rooms still tracked = peak simultaneous meetings
    }

    // Solution B — sweep line: +1 at each start, -1 at each end, track the peak.
    public static int minMeetingRoomsSweep(int[][] intervals) {
        int n = intervals.length;
        int[] starts = new int[n], ends = new int[n];
        for (int i = 0; i < n; i++) {
            starts[i] = intervals[i][0];
            ends[i] = intervals[i][1];
        }
        Arrays.sort(starts);                           // walk both timelines in time order
        Arrays.sort(ends);
        int rooms = 0, best = 0, e = 0;                // e = index of next meeting to finish
        for (int s = 0; s < n; s++) {
            // release every meeting that finished before (or exactly when) this one starts
            while (e < n && ends[e] <= starts[s]) {
                rooms--;
                e++;
            }
            rooms++;                                   // this meeting starts -> needs a room
            best = Math.max(best, rooms);              // record the peak concurrency
        }
        return best;                                   // peak = minimum rooms required
    }

    public static void main(String[] args) {
        int[][] a = {{0, 30}, {5, 10}, {15, 20}};
        assert minMeetingRoomsHeap(a) == 2 && minMeetingRoomsSweep(a) == 2;
        int[][] b = {{7, 10}, {2, 4}};
        assert minMeetingRoomsHeap(b) == 1 && minMeetingRoomsSweep(b) == 1;
        int[][] c = {{1, 5}, {2, 6}, {3, 7}};          // all three overlap at t=4
        assert minMeetingRoomsHeap(c) == 3 && minMeetingRoomsSweep(c) == 3;
        System.out.println("MeetingRoomsII: OK");
    }
}
