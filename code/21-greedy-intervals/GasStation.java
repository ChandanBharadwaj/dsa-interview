/**
 * LeetCode 134 — Gas Station. Greedy reset: a failure at i eliminates
 * every start up to i; total net >= 0 guarantees the survivor works.
 * Mirrors pages/21-greedy-intervals.html.
 */
public class GasStation {

    public static int canCompleteCircuitBrute(int[] gas, int[] cost) {
        int n = gas.length;
        for (int start = 0; start < n; start++) {
            int tank = 0;
            int i = 0;
            for (; i < n; i++) {                       // drive one full lap
                int station = (start + i) % n;
                tank += gas[station] - cost[station];
                if (tank < 0) break;                   // stranded — this start fails
            }
            if (i == n) return start;
        }
        return -1;
    }

    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int total = 0;      // net gas over the whole circle
        int tank = 0;       // gas since the current candidate start
        int start = 0;      // current candidate starting station
        for (int i = 0; i < gas.length; i++) {
            int net = gas[i] - cost[i];
            total += net;
            tank += net;
            if (tank < 0) {
                // If we ran dry reaching station i+1, no station between the
                // old start and i can work either — each would arrive here
                // with even LESS gas. Jump the candidate past i wholesale.
                start = i + 1;
                tank = 0;
            }
        }
        // Sufficiency: if total net >= 0, the final candidate completes the lap.
        return total >= 0 ? start : -1;
    }

    public static void main(String[] args) {
        assert canCompleteCircuit(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5, 1, 2}) == 3;
        assert canCompleteCircuit(new int[]{2, 3, 4}, new int[]{3, 4, 3}) == -1;
        assert canCompleteCircuitBrute(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5, 1, 2}) == 3;
        assert canCompleteCircuit(new int[]{5}, new int[]{4}) == 0;
        System.out.println("GasStation: OK");
    }
}
