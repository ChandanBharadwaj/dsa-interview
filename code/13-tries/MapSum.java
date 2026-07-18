import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 677 — Map Sum Pairs. Trie with per-node subtree sums; the
 * overwrite delta keeps aggregates honest. Mirrors pages/13-tries.html.
 */
public class MapSum {

    private static class Node {
        Node[] children = new Node[26];
        int subtreeSum;                     // sum of values of ALL keys below here
    }

    private final Node root = new Node();
    private final Map<String, Integer> values = new HashMap<>(); // current value per key

    public void insert(String key, int val) {
        // Overwrite semantics: only the DIFFERENCE propagates down the path.
        int delta = val - values.getOrDefault(key, 0);
        values.put(key, val);
        Node cur = root;
        cur.subtreeSum += delta;
        for (int i = 0; i < key.length(); i++) {
            int c = key.charAt(i) - 'a';
            if (cur.children[c] == null) cur.children[c] = new Node();
            cur = cur.children[c];
            cur.subtreeSum += delta;        // every prefix node absorbs the delta
        }
    }

    public int sum(String prefix) {
        Node cur = root;
        for (int i = 0; i < prefix.length(); i++) {
            cur = cur.children[prefix.charAt(i) - 'a'];
            if (cur == null) return 0;      // no key has this prefix
        }
        return cur.subtreeSum;              // precomputed — no subtree walk needed
    }

    public static void main(String[] args) {
        MapSum m = new MapSum();
        m.insert("apple", 3);
        assert m.sum("ap") == 3;
        m.insert("app", 2);
        assert m.sum("ap") == 5;
        m.insert("apple", 5);               // overwrite
        assert m.sum("ap") == 7;
        assert m.sum("apple") == 5;
        assert m.sum("zz") == 0;
        System.out.println("MapSum: OK");
    }
}
