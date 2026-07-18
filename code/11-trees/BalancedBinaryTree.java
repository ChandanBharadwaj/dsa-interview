public class BalancedBinaryTree {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val; this.left = left; this.right = right;
        }
    }

    // LeetCode 110. Height-balanced: every node's subtree heights differ ≤ 1.
    // One post-order pass returns height, using -1 as an "unbalanced" flag
    // that short-circuits straight up — O(n), not the naive O(n log n).
    public static boolean isBalanced(TreeNode root) {
        return height(root) != -1;
    }

    private static int height(TreeNode n) {
        if (n == null) return 0;
        int left = height(n.left);
        if (left == -1) return -1;              // already broken below — bail
        int right = height(n.right);
        if (right == -1) return -1;
        if (Math.abs(left - right) > 1) return -1;   // broken HERE
        return 1 + Math.max(left, right);       // healthy: report real height
    }

    public static void main(String[] args) {
        TreeNode ok = new TreeNode(3,
                new TreeNode(9),
                new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        assert isBalanced(ok);
        TreeNode bad = new TreeNode(1,
                new TreeNode(2, new TreeNode(3, new TreeNode(4), null), null),
                new TreeNode(2));
        assert !isBalanced(bad);
        assert isBalanced(null);
        System.out.println("BalancedBinaryTree: OK");
    }
}
