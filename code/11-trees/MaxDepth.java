public class MaxDepth {

    // Minimal binary tree node — same shape LeetCode gives you.
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val; this.left = left; this.right = right;
        }
    }

    // LeetCode 104. Depth = number of nodes on the longest root-to-leaf path.
    public static int maxDepth(TreeNode root) {
        // Base case: the empty tree has depth 0. This one line also handles
        // leaves cleanly (both their children are null subtrees of depth 0).
        if (root == null) return 0;
        // Leap of faith: trust the recursion to measure each subtree...
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        // ...then combine: my depth = the deeper subtree + 1 for myself.
        // "Combine after recursing" makes this a POST-ORDER (bottom-up) pattern.
        return Math.max(left, right) + 1;
    }

    public static void main(String[] args) {
        // Tree:      3
        //           / \
        //          9  20
        //             / \
        //            15  7        -> depth 3
        TreeNode root = new TreeNode(3,
                new TreeNode(9),
                new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        assert maxDepth(root) == 3;
        assert maxDepth(null) == 0;
        assert maxDepth(new TreeNode(1)) == 1;
        // Degenerate "linked list" tree: 1 -> 2 -> 3 -> 4  -> depth 4
        TreeNode chain = new TreeNode(1, null,
                new TreeNode(2, null, new TreeNode(3, null, new TreeNode(4))));
        assert maxDepth(chain) == 4;
        System.out.println("MaxDepth: OK");
    }
}
