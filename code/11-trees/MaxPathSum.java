public class MaxPathSum {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val; this.left = left; this.right = right;
        }
    }

    // LeetCode 124. A path may start and end anywhere. Each node plays two
    // roles: ARCH of a path (left gain + val + right gain — candidate for the
    // global best) or SEGMENT handed to its parent (val + ONE side's gain).
    private static int best;

    public static int maxPathSum(TreeNode root) {
        best = Integer.MIN_VALUE;
        gain(root);
        return best;
    }

    private static int gain(TreeNode n) {
        if (n == null) return 0;
        int left = Math.max(gain(n.left), 0);    // negative branch? take nothing
        int right = Math.max(gain(n.right), 0);
        best = Math.max(best, left + n.val + right);  // arch through n
        return n.val + Math.max(left, right);    // segment: only one side extends
    }

    public static void main(String[] args) {
        assert maxPathSum(new TreeNode(1, new TreeNode(2), new TreeNode(3))) == 6;
        TreeNode t = new TreeNode(-10,
                new TreeNode(9),
                new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        assert maxPathSum(t) == 42;              // 15 + 20 + 7, skipping -10
        assert maxPathSum(new TreeNode(-3)) == -3;   // all-negative: best single node
        assert maxPathSum(new TreeNode(2, new TreeNode(-1), null)) == 2;
        System.out.println("MaxPathSum: OK");
    }
}
