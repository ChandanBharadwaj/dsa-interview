import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * LeetCode 460 — LFU Cache. Frequency buckets of LinkedHashSets (LRU
 * order inside each) + a minFreq floor pointer: all ops O(1).
 * Mirrors pages/24-advanced-structures.html.
 */
public class LFUCache {

    private final int capacity;
    private final Map<Integer, Integer> values = new HashMap<>();
    private final Map<Integer, Integer> freq = new HashMap<>();   // key -> use count
    // freq -> keys with that count, in LRU order (LinkedHashSet keeps
    // insertion order, giving us the recency tie-break for free).
    private final Map<Integer, LinkedHashSet<Integer>> buckets = new HashMap<>();
    private int minFreq = 0;              // smallest frequency present in the cache

    public LFUCache(int capacity) { this.capacity = capacity; }

    public int get(int key) {
        if (!values.containsKey(key)) return -1;
        touch(key);                       // bump its frequency
        return values.get(key);
    }

    public void put(int key, int value) {
        if (capacity == 0) return;
        if (values.containsKey(key)) {    // update existing: new value + bump
            values.put(key, value);
            touch(key);
            return;
        }
        if (values.size() == capacity) {
            // Evict: the LRU member of the minimum-frequency bucket.
            int evict = buckets.get(minFreq).iterator().next();  // oldest inserted
            buckets.get(minFreq).remove(evict);
            values.remove(evict);
            freq.remove(evict);
        }
        values.put(key, value);
        freq.put(key, 1);
        buckets.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
        minFreq = 1;                      // a brand-new key resets the floor
    }

    // Move key from bucket f to bucket f+1, maintaining minFreq.
    private void touch(int key) {
        int f = freq.get(key);
        buckets.get(f).remove(key);
        // If that emptied the minimum bucket, the floor rises by exactly 1
        if (f == minFreq && buckets.get(f).isEmpty()) minFreq++;
        freq.put(key, f + 1);
        buckets.computeIfAbsent(f + 1, k -> new LinkedHashSet<>()).add(key);
    }

    public static void main(String[] args) {
        LFUCache c = new LFUCache(2);
        c.put(1, 1); c.put(2, 2);
        assert c.get(1) == 1;             // key 1 now freq 2
        c.put(3, 3);                      // evicts key 2 (freq 1)
        assert c.get(2) == -1;
        assert c.get(3) == 3;             // key 3 now freq 2
        c.put(4, 4);                      // tie at freq 2: evict LRU = key 1
        assert c.get(1) == -1;
        assert c.get(3) == 3 && c.get(4) == 4;
        System.out.println("LFUCache: OK");
    }
}
