/**
 * LeetCode 100 — Same Tree: parallel recursion over both trees.
 * Mirrors pages/11-trees.html.
 */
public class SameTree {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;    // both empty: match
        if (p == null || q == null) return false;   // one empty, one not: shape differs
        if (p.val != q.val) return false;           // values differ right here
        // Both subtree pairs must match too
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    public static void main(String[] args) {
        TreeNode a = new TreeNode(1);
        a.left = new TreeNode(2); a.right = new TreeNode(3);
        TreeNode b = new TreeNode(1);
        b.left = new TreeNode(2); b.right = new TreeNode(3);
        assert isSameTree(a, b);
        b.right.val = 4;
        assert !isSameTree(a, b);
        TreeNode c = new TreeNode(1); c.left = new TreeNode(2);
        TreeNode d = new TreeNode(1); d.right = new TreeNode(2);
        assert !isSameTree(c, d);                   // same values, different shape
        assert isSameTree(null, null);
        System.out.println("SameTree: OK");
    }
}
