public class SymmetricTree {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val; this.left = left; this.right = right;
        }
    }

    // LeetCode 101. A tree is symmetric iff its left and right subtrees are
    // MIRRORS: roots equal, and each one's left mirrors the other's right.
    public static boolean isSymmetric(TreeNode root) {
        return root == null || mirrors(root.left, root.right);
    }

    private static boolean mirrors(TreeNode a, TreeNode b) {
        if (a == null && b == null) return true;    // both absent: mirror
        if (a == null || b == null) return false;   // one absent: broken
        return a.val == b.val
            && mirrors(a.left, b.right)             // outer pair
            && mirrors(a.right, b.left);            // inner pair — the crossing
    }

    public static void main(String[] args) {
        TreeNode sym = new TreeNode(1,
                new TreeNode(2, new TreeNode(3), new TreeNode(4)),
                new TreeNode(2, new TreeNode(4), new TreeNode(3)));
        assert isSymmetric(sym);
        TreeNode asym = new TreeNode(1,
                new TreeNode(2, null, new TreeNode(3)),
                new TreeNode(2, null, new TreeNode(3)));
        assert !isSymmetric(asym);
        assert isSymmetric(null) && isSymmetric(new TreeNode(9));
        System.out.println("SymmetricTree: OK");
    }
}
