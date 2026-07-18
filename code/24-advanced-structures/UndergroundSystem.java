import java.util.*;

public class UndergroundSystem {

    // Two maps: open journeys keyed by traveler, and per-route aggregates
    // (total time, trip count) keyed by "start->end". Averages are O(1).
    private final Map<Integer, String> inStation = new HashMap<>();     // id -> start station
    private final Map<Integer, Integer> inTime = new HashMap<>();       // id -> check-in time
    private final Map<String, long[]> routes = new HashMap<>();         // "a->b" -> {totalTime, trips}

    public void checkIn(int id, String stationName, int t) {
        inStation.put(id, stationName);
        inTime.put(id, t);
    }

    public void checkOut(int id, String stationName, int t) {
        String route = inStation.remove(id) + "->" + stationName;
        int started = inTime.remove(id);
        long[] agg = routes.computeIfAbsent(route, k -> new long[2]);
        agg[0] += t - started;          // accumulate duration
        agg[1]++;                       // count the trip
    }

    public double getAverageTime(String startStation, String endStation) {
        long[] agg = routes.get(startStation + "->" + endStation);
        return (double) agg[0] / agg[1];
    }

    public static void main(String[] args) {
        UndergroundSystem u = new UndergroundSystem();
        u.checkIn(45, "Leyton", 3);
        u.checkIn(32, "Paradise", 8);
        u.checkIn(27, "Leyton", 10);
        u.checkOut(45, "Waterloo", 15);     // 12
        u.checkOut(27, "Waterloo", 20);     // 10
        u.checkOut(32, "Cambridge", 22);    // 14
        assert u.getAverageTime("Paradise", "Cambridge") == 14.0;
        assert u.getAverageTime("Leyton", "Waterloo") == 11.0;
        u.checkIn(10, "Leyton", 24);
        u.checkOut(10, "Waterloo", 38);     // 14 -> avg (12+10+14)/3 = 12
        assert u.getAverageTime("Leyton", "Waterloo") == 12.0;
        System.out.println("UndergroundSystem: OK");
    }
}
