public class LowestCommonAncestorBT {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val; this.left = left; this.right = right;
        }
    }

    // LeetCode 236. General binary tree (no BST ordering to exploit).
    // Post-order logic: a node that IS p or q reports itself; a node whose
    // two sides both report something is the split point = the LCA.
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q)
            return root;                          // found one (or dead end)
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null)
            return root;                          // p and q split here: LCA
        return left != null ? left : right;       // pass the single find upward
    }

    public static void main(String[] args) {
        TreeNode p = new TreeNode(5), q = new TreeNode(1);
        TreeNode four = new TreeNode(4);
        p.left = new TreeNode(6);
        p.right = new TreeNode(2, new TreeNode(7), four);
        TreeNode root = new TreeNode(3, p, q);
        q.left = new TreeNode(0);
        q.right = new TreeNode(8);
        assert lowestCommonAncestor(root, p, q) == root;   // split at the root
        assert lowestCommonAncestor(root, p, four) == p;   // ancestor of itself
        assert lowestCommonAncestor(root, four, p.left) == p;
        System.out.println("LowestCommonAncestorBT: OK");
    }
}
