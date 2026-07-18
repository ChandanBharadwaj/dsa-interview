/**
 * LeetCode 235 — Lowest Common Ancestor of a BST: descend until the two
 * targets split. O(h) time, O(1) space. Mirrors pages/11-trees.html.
 */
public class LcaBST {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode cur = root;
        while (cur != null) {
            if (p.val < cur.val && q.val < cur.val) {
                cur = cur.left;              // both targets are smaller — LCA is left
            } else if (p.val > cur.val && q.val > cur.val) {
                cur = cur.right;             // both larger — LCA is right
            } else {
                // The targets SPLIT here (or one equals cur):
                // this is the lowest node with one on each side.
                return cur;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // BST [6,2,8,0,4,7,9] with 4's children 3 and 5
        TreeNode n6 = new TreeNode(6), n2 = new TreeNode(2), n8 = new TreeNode(8);
        TreeNode n0 = new TreeNode(0), n4 = new TreeNode(4), n7 = new TreeNode(7), n9 = new TreeNode(9);
        TreeNode n3 = new TreeNode(3), n5 = new TreeNode(5);
        n6.left = n2; n6.right = n8; n2.left = n0; n2.right = n4;
        n8.left = n7; n8.right = n9; n4.left = n3; n4.right = n5;
        assert lowestCommonAncestor(n6, n2, n8) == n6;
        assert lowestCommonAncestor(n6, n2, n4) == n2;   // ancestor of itself
        assert lowestCommonAncestor(n6, n3, n5) == n4;
        System.out.println("LcaBST: OK");
    }
}
