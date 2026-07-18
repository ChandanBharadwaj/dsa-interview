/**
 * LeetCode 226 — Invert Binary Tree: swap children, recurse.
 * Mirrors pages/11-trees.html.
 */
public class InvertBinaryTree {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static TreeNode invertTree(TreeNode root) {
        if (root == null) return null;         // nothing to mirror
        // Swap my two children...
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        // ...then trust the recursion to mirror each (already swapped) subtree.
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    public static void main(String[] args) {
        TreeNode r = new TreeNode(4);
        r.left = new TreeNode(2); r.right = new TreeNode(7);
        r.left.left = new TreeNode(1); r.left.right = new TreeNode(3);
        r.right.left = new TreeNode(6); r.right.right = new TreeNode(9);
        invertTree(r);
        assert r.left.val == 7 && r.right.val == 2;
        assert r.left.left.val == 9 && r.left.right.val == 6;
        assert r.right.left.val == 3 && r.right.right.val == 1;
        assert invertTree(null) == null;
        System.out.println("InvertBinaryTree: OK");
    }
}
