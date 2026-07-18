public class DiameterOfBinaryTree {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val; this.left = left; this.right = right;
        }
    }

    // LeetCode 543. Diameter = length (in EDGES) of the longest path between
    // any two nodes. The path does not have to pass through the root.
    private static int best; // longest path seen anywhere in the tree

    public static int diameterOfBinaryTree(TreeNode root) {
        best = 0;
        height(root);        // we only run the traversal for its side effect
        return best;
    }

    // Returns the height (edges on the longest downward path) of `node`,
    // and as a SIDE EFFECT updates the best diameter seen so far.
    private static int height(TreeNode node) {
        if (node == null) return -1; // so a leaf gets height -1 + 1 = 0
        int left = height(node.left);    // bottom-up: children answered first
        int right = height(node.right);
        // The longest path THROUGH this node uses the deepest chain on each
        // side: left height + right height + the 2 edges connecting them.
        best = Math.max(best, left + right + 2);
        // But we RETURN only what the parent can extend: one side + 1 edge.
        // (The parent's path can't bend through both of my subtrees.)
        return Math.max(left, right) + 1;
    }

    public static void main(String[] args) {
        // Tree:      1
        //           / \
        //          2   3
        //         / \
        //        4   5    longest path 4-2-5 or 4-2-1-3 -> 3 edges
        TreeNode root = new TreeNode(1,
                new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                new TreeNode(3));
        assert diameterOfBinaryTree(root) == 3;
        assert diameterOfBinaryTree(new TreeNode(1)) == 0;
        // Path that does NOT pass through the root: 6-4-2-5 (3 edges), found
        // entirely inside the left subtree while `best` watches every node.
        TreeNode deep = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(4, new TreeNode(6), null),
                        new TreeNode(5)),
                null);
        assert diameterOfBinaryTree(deep) == 3;
        System.out.println("DiameterOfBinaryTree: OK");
    }
}
