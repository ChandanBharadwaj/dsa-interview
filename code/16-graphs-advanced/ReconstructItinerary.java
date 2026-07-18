import java.util.*;

public class ReconstructItinerary {

    // Use every ticket exactly once, start at JFK, prefer lexicographically
    // smallest: an Eulerian path. Hierholzer's algorithm: DFS greedily
    // (smallest destination first), appending each airport on the way OUT —
    // dead-end airports sink to the end, and the reversed log is the path.
    public static List<String> findItinerary(List<List<String>> tickets) {
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
        for (List<String> t : tickets)
            graph.computeIfAbsent(t.get(0), k -> new PriorityQueue<>())
                 .add(t.get(1));               // heap = smallest destination first

        LinkedList<String> route = new LinkedList<>();
        dfs("JFK", graph, route);
        return route;
    }

    private static void dfs(String airport, Map<String, PriorityQueue<String>> graph,
                            LinkedList<String> route) {
        PriorityQueue<String> dests = graph.get(airport);
        while (dests != null && !dests.isEmpty())
            dfs(dests.poll(), graph, route);   // consume the ticket permanently
        route.addFirst(airport);               // post-order: add when stuck
    }

    public static void main(String[] args) {
        assert findItinerary(List.of(
                List.of("MUC", "LHR"), List.of("JFK", "MUC"),
                List.of("SFO", "SJC"), List.of("LHR", "SFO")))
                .equals(List.of("JFK", "MUC", "LHR", "SFO", "SJC"));
        // Greedy-smallest alone dead-ends here; Hierholzer recovers:
        assert findItinerary(List.of(
                List.of("JFK", "SFO"), List.of("JFK", "ATL"), List.of("SFO", "ATL"),
                List.of("ATL", "JFK"), List.of("ATL", "SFO")))
                .equals(List.of("JFK", "ATL", "JFK", "SFO", "ATL", "SFO"));
        assert findItinerary(List.of(List.of("JFK", "KUL"), List.of("JFK", "NRT"),
                List.of("NRT", "JFK")))
                .equals(List.of("JFK", "NRT", "JFK", "KUL"));
        System.out.println("ReconstructItinerary: OK");
    }
}
