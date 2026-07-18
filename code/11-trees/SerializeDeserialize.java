import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * LeetCode 297 — Serialize and Deserialize Binary Tree. Preorder with
 * explicit '#' null markers makes the encoding unambiguous.
 * Mirrors pages/11-trees.html.
 */
public class SerializeDeserialize {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    // Serialize: preorder, writing '#' for every null. The null markers are
    // what make the string unambiguous — shape is fully recorded.
    public static String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        ser(root, sb);
        return sb.toString();
    }

    private static void ser(TreeNode n, StringBuilder sb) {
        if (n == null) { sb.append("#,"); return; }
        sb.append(n.val).append(',');            // node BEFORE children = preorder
        ser(n.left, sb);
        ser(n.right, sb);
    }

    // Deserialize: consume tokens in the same preorder. The recursion's
    // structure IS the tree's structure — each call eats exactly one subtree.
    public static TreeNode deserialize(String data) {
        Deque<String> tokens = new ArrayDeque<>(Arrays.asList(data.split(",")));
        return des(tokens);
    }

    private static TreeNode des(Deque<String> tokens) {
        String t = tokens.poll();
        if (t.equals("#")) return null;          // a recorded null: subtree ends here
        TreeNode node = new TreeNode(Integer.parseInt(t));
        node.left = des(tokens);                 // tokens are consumed left-to-right,
        node.right = des(tokens);                // exactly as serialize emitted them
        return node;
    }

    public static void main(String[] args) {
        TreeNode r = new TreeNode(1);
        r.left = new TreeNode(2); r.right = new TreeNode(3);
        r.right.left = new TreeNode(4); r.right.right = new TreeNode(5);
        String s = serialize(r);
        TreeNode back = deserialize(s);
        assert serialize(back).equals(s);        // round-trip is exact
        assert back.right.left.val == 4;
        assert serialize(deserialize(serialize(null))).equals("#,");
        assert deserialize("-7,#,#,").val == -7; // negatives survive parsing
        System.out.println("SerializeDeserialize: OK");
    }
}
