import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    // Doubly-linked-list node. It stores the KEY as well as the value:
    // when we evict from the tail we must also delete the map entry,
    // and the key is the only way to find it.
    private static class Node {
        int key, value;
        Node prev, next;
        Node(int key, int value) { this.key = key; this.value = value; }
    }

    private final int capacity;
    private final Map<Integer, Node> map = new HashMap<>();  // key -> its node, O(1) locate
    // Sentinel head/tail: real nodes live strictly between them, so unlink/link
    // NEVER needs a null check. This one trick removes 90% of the edge-case bugs.
    private final Node head = new Node(0, 0);   // head.next  = most-recently-used
    private final Node tail = new Node(0, 0);   // tail.prev  = least-recently-used

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;                        // empty list: sentinels point at each other
        tail.prev = head;
    }

    public int get(int key) {
        Node n = map.get(key);
        if (n == null) return -1;                // miss
        moveToFront(n);                          // this access makes it most-recent
        return n.value;
    }

    public void put(int key, int value) {
        Node n = map.get(key);
        if (n != null) {                         // key exists: update value, refresh recency
            n.value = value;
            moveToFront(n);
            return;
        }
        if (map.size() == capacity) {            // full: evict the least-recently-used
            Node lru = tail.prev;                // the real node just before the tail sentinel
            unlink(lru);                         // O(1) removal from the list...
            map.remove(lru.key);                 // ...and from the map — why nodes store keys
        }
        Node fresh = new Node(key, value);
        map.put(key, fresh);
        linkFront(fresh);                        // new entries are most-recent by definition
    }

    // Splice a node out of the list. Sentinels guarantee prev/next are non-null.
    private void unlink(Node n) {
        n.prev.next = n.next;
        n.next.prev = n.prev;
    }

    // Insert right after the head sentinel — the most-recent position.
    private void linkFront(Node n) {
        n.next = head.next;
        n.prev = head;
        head.next.prev = n;
        head.next = n;
    }

    private void moveToFront(Node n) {
        unlink(n);
        linkFront(n);
    }

    // ---- test harness ----
    public static void main(String[] args) {
        LRUCache c = new LRUCache(2);
        c.put(1, 1);
        c.put(2, 2);
        assert c.get(1) == 1;      // 1 becomes most-recent; recency order: 1, 2
        c.put(3, 3);               // full -> evicts 2 (the LRU)
        assert c.get(2) == -1;
        c.put(4, 4);               // evicts 1
        assert c.get(1) == -1;
        assert c.get(3) == 3;
        assert c.get(4) == 4;
        System.out.println("LRUCache: OK");
    }
}
