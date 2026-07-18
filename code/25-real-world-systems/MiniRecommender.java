import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class MiniRecommender {

    // Co-visitation matrix: item -> (other item -> number of users who watched both).
    // This IS the "users who watched X also watched Y" signal, stored as nested hashing (page 01).
    private final Map<String, Map<String, Integer>> coVis = new HashMap<>();
    // Watch history: user -> items already seen. Doubles as our "don't re-recommend"
    // filter — production uses a bloom filter (page 24) for exactly this role.
    private final Map<String, Set<String>> watched = new HashMap<>();

    public void recordWatch(String user, String item) {
        Set<String> items = watched.computeIfAbsent(user, u -> new LinkedHashSet<>());
        if (!items.add(item)) return;             // duplicate event, nothing new to learn
        for (String other : items) {              // pair the new item with the user's history:
            if (other.equals(item)) continue;     // one hop through the user-item bipartite
            bump(item, other);                    // graph (page 15), counted with a HashMap
            bump(other, item);                    // co-visitation is symmetric
        }
    }

    private void bump(String a, String b) {
        coVis.computeIfAbsent(a, k -> new HashMap<>()).merge(b, 1, Integer::sum);
    }

    public List<String> recommend(String user, int k) {
        Set<String> seen = watched.getOrDefault(user, Set.of());
        // Candidate generation + scoring: aggregate co-visitation counts over
        // everything the user has watched (frequency counting, page 01).
        Map<String, Integer> score = new HashMap<>();
        for (String item : seen)
            for (Map.Entry<String, Integer> e : coVis.getOrDefault(item, Map.of()).entrySet())
                if (!seen.contains(e.getKey()))   // filtering stage: drop already-watched
                    score.merge(e.getKey(), e.getValue(), Integer::sum);
        // Ranking stage: top-K with a size-K MIN-heap (page 12). The worst of the
        // current best K sits on top and is evicted when something better arrives:
        // O(m log k) over m candidates instead of sorting everything at O(m log m).
        PriorityQueue<Map.Entry<String, Integer>> heap = new PriorityQueue<>(
            (a, b) -> a.getValue().equals(b.getValue())
                ? b.getKey().compareTo(a.getKey())            // tie-break so results are stable
                : Integer.compare(a.getValue(), b.getValue()));
        for (Map.Entry<String, Integer> e : score.entrySet()) {
            heap.offer(e);
            if (heap.size() > k) heap.poll();     // drop the current worst candidate
        }
        LinkedList<String> out = new LinkedList<>();
        while (!heap.isEmpty()) out.addFirst(heap.poll().getKey());  // reverse: best first
        return out;
    }

    // ---- test harness ----
    public static void main(String[] args) {
        MiniRecommender r = new MiniRecommender();
        String[][] watchLog = {
            {"alice", "Inception"}, {"alice", "Interstellar"}, {"alice", "Tenet"},
            {"bob",   "Inception"}, {"bob",   "Interstellar"}, {"bob",   "Dunkirk"},
            {"carol", "Inception"}, {"carol", "Dunkirk"},
            {"dave",  "Interstellar"}, {"dave", "Tenet"}
        };
        for (String[] event : watchLog) r.recordWatch(event[0], event[1]);

        // carol watched Inception + Dunkirk. Interstellar is co-watched 3x
        // (alice, bob via Inception; bob via Dunkirk), Tenet 1x (alice).
        assert r.recommend("carol", 2).equals(List.of("Interstellar", "Tenet"));
        // dave watched Interstellar + Tenet -> Inception scores 3, Dunkirk 1.
        assert r.recommend("dave", 1).equals(List.of("Inception"));
        assert r.recommend("dave", 5).equals(List.of("Inception", "Dunkirk"));
        // Unknown user: no history, no candidates — cold start in miniature.
        assert r.recommend("erin", 3).isEmpty();
        System.out.println("MiniRecommender: OK");
    }
}
