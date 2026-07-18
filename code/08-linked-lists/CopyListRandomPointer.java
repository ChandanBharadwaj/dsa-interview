import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 138 — Copy List with Random Pointer. HashMap original->clone
 * (O(n) space) and the interleaved A->A'->B->B' O(1)-space variation.
 * Mirrors pages/08-linked-lists.html.
 */
public class CopyListRandomPointer {

    static class Node {
        int val;
        Node next, random;
        Node(int val) { this.val = val; }
    }

    public static Node copyRandomList(Node head) {
        // Pass 1: clone every node; map original -> clone
        Map<Node, Node> map = new HashMap<>();
        for (Node cur = head; cur != null; cur = cur.next)
            map.put(cur, new Node(cur.val));
        // Pass 2: wire the clones using the map as a lookup
        for (Node cur = head; cur != null; cur = cur.next) {
            map.get(cur).next   = map.get(cur.next);     // map.get(null) is null — handy
            map.get(cur).random = map.get(cur.random);
        }
        return map.get(head);
    }

    public static Node copyRandomListInterleaved(Node head) {
        if (head == null) return null;
        // Pass 1: insert each clone right AFTER its original: A -> A' -> B -> B' ...
        for (Node cur = head; cur != null; cur = cur.next.next) {
            Node clone = new Node(cur.val);
            clone.next = cur.next;
            cur.next = clone;
        }
        // Pass 2: set randoms — the clone of X.random is X.random.next (its neighbor!)
        for (Node cur = head; cur != null; cur = cur.next.next)
            if (cur.random != null)
                cur.next.random = cur.random.next;
        // Pass 3: unzip the two lists, restoring the original intact
        Node newHead = head.next;
        for (Node cur = head; cur != null; cur = cur.next) {
            Node clone = cur.next;
            cur.next = clone.next;                       // restore original's next
            clone.next = (clone.next != null) ? clone.next.next : null;
        }
        return newHead;
    }

    private static void check(Node copy, Node orig) {
        while (orig != null) {
            assert copy != null && copy != orig && copy.val == orig.val;
            if (orig.random == null) assert copy.random == null;
            else assert copy.random != orig.random && copy.random.val == orig.random.val;
            orig = orig.next; copy = copy.next;
        }
        assert copy == null;
    }

    public static void main(String[] args) {
        // 7 -> 13 -> 11 -> 10 -> 1 with randoms [null, 0, 4, 2, 0]
        Node[] n = new Node[5];
        int[] vals = {7, 13, 11, 10, 1};
        for (int i = 0; i < 5; i++) n[i] = new Node(vals[i]);
        for (int i = 0; i < 4; i++) n[i].next = n[i + 1];
        n[1].random = n[0]; n[2].random = n[4]; n[3].random = n[2]; n[4].random = n[0];

        check(copyRandomList(n[0]), n[0]);
        check(copyRandomListInterleaved(n[0]), n[0]);
        // interleaved version must leave the original intact
        for (int i = 0; i < 4; i++) assert n[i].next == n[i + 1];
        assert copyRandomList(null) == null && copyRandomListInterleaved(null) == null;
        System.out.println("CopyListRandomPointer: OK");
    }
}
