public class SubtreeOfAnother {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val; this.left = left; this.right = right;
        }
    }

    // LeetCode 572. subRoot is a subtree of root iff some node of root
    // matches it EXACTLY (structure + values, including its leaves).
    public static boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null) return false;         // ran out of anchor points
        if (same(root, subRoot)) return true;   // anchored match here?
        return isSubtree(root.left, subRoot)    // else try anchoring lower
            || isSubtree(root.right, subRoot);
    }

    private static boolean same(TreeNode a, TreeNode b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.val == b.val && same(a.left, b.left) && same(a.right, b.right);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3,
                new TreeNode(4, new TreeNode(1), new TreeNode(2)),
                new TreeNode(5));
        TreeNode sub = new TreeNode(4, new TreeNode(1), new TreeNode(2));
        assert isSubtree(root, sub);
        // add a child under 2 -> no longer an exact subtree
        TreeNode root2 = new TreeNode(3,
                new TreeNode(4, new TreeNode(1),
                        new TreeNode(2, new TreeNode(0), null)),
                new TreeNode(5));
        assert !isSubtree(root2, sub);
        System.out.println("SubtreeOfAnother: OK");
    }
}
