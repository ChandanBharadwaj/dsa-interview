import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * LeetCode 853 — Car Fleet. Sort by position (closest to target first);
 * a car joins the fleet ahead iff its solo arrival time <= that fleet's.
 * Mirrors pages/09-stacks-queues.html.
 */
public class CarFleet {

    public static int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        // Pair up and sort by starting position, CLOSEST to target first —
        // a car can only ever catch fleets that started ahead of it.
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> position[b] - position[a]);

        Deque<Double> fleets = new ArrayDeque<>();   // arrival time of each fleet, front-of-road first
        for (int k : idx) {
            double time = (double) (target - position[k]) / speed[k];  // solo arrival time
            // If this car would arrive AFTER the fleet directly ahead, it never
            // catches it -> it founds a NEW fleet. If it would arrive sooner or
            // equal, it slams into that fleet and dissolves into it (no push).
            if (fleets.isEmpty() || time > fleets.peek())
                fleets.push(time);
        }
        return fleets.size();
    }

    public static void main(String[] args) {
        assert carFleet(12, new int[]{10, 8, 0, 5, 3}, new int[]{2, 4, 1, 1, 3}) == 3;
        assert carFleet(10, new int[]{3}, new int[]{3}) == 1;
        assert carFleet(100, new int[]{0, 2, 4}, new int[]{4, 2, 1}) == 1;
        System.out.println("CarFleet: OK");
    }
}
