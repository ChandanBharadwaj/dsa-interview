import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * LeetCode 269 — Alien Dictionary. Extract pairwise letter constraints
 * from adjacent words, then Kahn's over the letter graph.
 * Mirrors pages/16-graphs-advanced.html.
 */
public class AlienDictionary {

    public static String alienOrder(String[] words) {
        // Graph over the letters that actually appear.
        Map<Character, Set<Character>> adj = new HashMap<>();
        Map<Character, Integer> indegree = new HashMap<>();
        for (String w : words)
            for (char c : w.toCharArray()) {
                adj.putIfAbsent(c, new HashSet<>());
                indegree.putIfAbsent(c, 0);
            }

        // Each ADJACENT word pair contributes at most ONE edge: the first
        // position where they differ says thisChar < thatChar.
        for (int i = 0; i + 1 < words.length; i++) {
            String a = words[i], b = words[i + 1];
            // Contradiction: "abc" before "ab" can't happen in any alphabet.
            if (a.length() > b.length() && a.startsWith(b)) return "";
            for (int k = 0; k < Math.min(a.length(), b.length()); k++) {
                char x = a.charAt(k), y = b.charAt(k);
                if (x != y) {
                    if (adj.get(x).add(y))          // dedupe repeated constraints
                        indegree.merge(y, 1, Integer::sum);
                    break;                          // ONLY the first difference matters
                }
            }
        }

        // Standard Kahn's over the letter graph.
        Deque<Character> queue = new ArrayDeque<>();
        for (var e : indegree.entrySet())
            if (e.getValue() == 0) queue.offer(e.getKey());
        StringBuilder order = new StringBuilder();
        while (!queue.isEmpty()) {
            char c = queue.poll();
            order.append(c);
            for (char next : adj.get(c))
                if (indegree.merge(next, -1, Integer::sum) == 0)
                    queue.offer(next);
        }
        // Letters left un-taken sit on a cycle: contradictory input.
        return order.length() == indegree.size() ? order.toString() : "";
    }

    public static void main(String[] args) {
        String order = alienOrder(new String[]{"wrt", "wrf", "er", "ett", "rftt"});
        // must respect t<f, w<e, r<t, e<r
        assert order.indexOf('t') < order.indexOf('f');
        assert order.indexOf('w') < order.indexOf('e');
        assert order.indexOf('r') < order.indexOf('t');
        assert order.indexOf('e') < order.indexOf('r');
        assert order.length() == 5;
        assert alienOrder(new String[]{"abc", "ab"}).isEmpty();   // prefix contradiction
        assert alienOrder(new String[]{"z", "x", "z"}).isEmpty(); // cycle z<x<z
        System.out.println("AlienDictionary: OK");
    }
}
