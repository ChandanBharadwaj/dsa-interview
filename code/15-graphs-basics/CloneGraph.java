import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// LeetCode 133 — Clone Graph
// Deep-copy an undirected graph. The trap: cycles. Blind recursion
// would clone the same node forever. Fix: a HashMap original -> clone
// that doubles as the visited set. BFS version shown here.
public class CloneGraph {

    // LeetCode's node type, as a static nested class so this file
    // compiles standalone.
    static class Node {
        int val;
        List<Node> neighbors = new ArrayList<>();
        Node(int val) { this.val = val; }
    }

    public static Node cloneGraph(Node node) {
        if (node == null) return null;
        // Maps each original node -> its clone. Also our "visited" set.
        Map<Node, Node> clones = new HashMap<>();
        clones.put(node, new Node(node.val)); // clone the start node up front
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(node);

        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (Node nb : cur.neighbors) {
                if (!clones.containsKey(nb)) {        // first time seeing nb
                    clones.put(nb, new Node(nb.val)); // clone BEFORE enqueue
                    queue.offer(nb);                  // explore it later
                }
                // Wire the CLONE edge: clone(cur) -> clone(nb).
                clones.get(cur).neighbors.add(clones.get(nb));
            }
        }
        return clones.get(node);
    }

    public static void main(String[] args) {
        // Build the classic square: 1-2, 2-3, 3-4, 4-1.
        Node n1 = new Node(1); Node n2 = new Node(2);
        Node n3 = new Node(3); Node n4 = new Node(4);
        n1.neighbors.addAll(List.of(n2, n4));
        n2.neighbors.addAll(List.of(n1, n3));
        n3.neighbors.addAll(List.of(n2, n4));
        n4.neighbors.addAll(List.of(n3, n1));

        Node c1 = cloneGraph(n1);
        assert c1 != n1;                  // a genuinely new object
        assert c1.val == 1 && c1.neighbors.size() == 2;
        Node c2 = c1.neighbors.get(0);    // clone of node 2
        assert c2.val == 2 && c2 != n2;   // deep copy, not a shared reference
        assert c2.neighbors.contains(c1); // undirected edge preserved both ways
        assert cloneGraph(null) == null;
        System.out.println("CloneGraph: OK");
    }
}
