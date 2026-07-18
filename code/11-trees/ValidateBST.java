public class ValidateBST {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val; this.left = left; this.right = right;
        }
    }

    // LeetCode 98. Is this a valid binary search tree?
    public static boolean isValidBST(TreeNode root) {
        // Every node must live inside an OPEN interval (min, max) imposed by
        // ALL of its ancestors — not just its parent. Start unbounded, and use
        // Long so Integer.MIN_VALUE / MAX_VALUE node values still validate.
        return valid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean valid(TreeNode node, long min, long max) {
        if (node == null) return true;           // empty subtree breaks nothing
        // The node itself must respect the bounds inherited from its ancestors.
        // (Checking only node.left.val < node.val is the CLASSIC BUG — a
        // grandchild can violate a grandparent while satisfying its parent.)
        if (node.val <= min || node.val >= max) return false;
        // Going left: values must stay BELOW me -> my value becomes the ceiling.
        // Going right: values must stay ABOVE me -> my value becomes the floor.
        return valid(node.left, min, node.val)
            && valid(node.right, node.val, max);
    }

    public static void main(String[] args) {
        // Valid:    2         Invalid:   5
        //          / \                  / \
        //         1   3                4   6
        //                                 / \
        //                                3   7   (3 < 5 sits in 5's RIGHT subtree)
        TreeNode good = new TreeNode(2, new TreeNode(1), new TreeNode(3));
        assert isValidBST(good);
        TreeNode bad = new TreeNode(5,
                new TreeNode(4),
                new TreeNode(6, new TreeNode(3), new TreeNode(7)));
        assert !isValidBST(bad); // parent-only check would wrongly say true!
        // Equal values are NOT allowed (strict <) — [2,2,2] is invalid.
        TreeNode dup = new TreeNode(2, new TreeNode(2), new TreeNode(2));
        assert !isValidBST(dup);
        // Extreme single node must pass (hence long bounds, not int).
        assert isValidBST(new TreeNode(Integer.MAX_VALUE));
        System.out.println("ValidateBST: OK");
    }
}
