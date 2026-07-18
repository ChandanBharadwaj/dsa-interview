public class DesignHashMap {

    // Separate chaining: an array of buckets, each a linked list of
    // key-value nodes. Exactly what java.util.HashMap does, miniaturized.
    private static final int BUCKETS = 1024;
    private final Node[] table = new Node[BUCKETS];

    private static class Node {
        final int key;
        int value;
        Node next;
        Node(int key, int value, Node next) {
            this.key = key; this.value = value; this.next = next;
        }
    }

    private int index(int key) {
        return Integer.hashCode(key) & (BUCKETS - 1);   // power-of-two mask
    }

    public void put(int key, int value) {
        int i = index(key);
        for (Node n = table[i]; n != null; n = n.next)
            if (n.key == key) {         // existing key: overwrite
                n.value = value;
                return;
            }
        table[i] = new Node(key, value, table[i]);      // prepend new node
    }

    public int get(int key) {
        for (Node n = table[index(key)]; n != null; n = n.next)
            if (n.key == key) return n.value;
        return -1;
    }

    public void remove(int key) {
        int i = index(key);
        Node prev = null;
        for (Node n = table[i]; n != null; prev = n, n = n.next)
            if (n.key == key) {
                if (prev == null) table[i] = n.next;    // head of the chain
                else prev.next = n.next;                // splice out mid-chain
                return;
            }
    }

    public static void main(String[] args) {
        DesignHashMap m = new DesignHashMap();
        m.put(1, 1);
        m.put(2, 2);
        assert m.get(1) == 1;
        assert m.get(3) == -1;
        m.put(2, 1);                    // overwrite
        assert m.get(2) == 1;
        m.remove(2);
        assert m.get(2) == -1;
        // collision exercise: keys 1024 apart share a bucket
        m.put(0, 10);
        m.put(1024, 20);
        m.put(2048, 30);
        assert m.get(0) == 10 && m.get(1024) == 20 && m.get(2048) == 30;
        m.remove(1024);
        assert m.get(1024) == -1 && m.get(0) == 10 && m.get(2048) == 30;
        System.out.println("DesignHashMap: OK");
    }
}
