public class HouseRobberIII {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    // DP on a tree: each node returns TWO numbers (bottom-up, like page 11's diameter pattern):
    //   res[0] = best loot in this subtree if we ROB this node
    //   res[1] = best loot in this subtree if we SKIP this node
    public static int rob(TreeNode root) {
        int[] res = dfs(root);
        return Math.max(res[0], res[1]);                 // free to rob or skip the root
    }

    private static int[] dfs(TreeNode node) {
        if (node == null) return new int[]{0, 0};        // empty subtree: nothing either way
        int[] left = dfs(node.left);                     // solve children first (post-order)
        int[] right = dfs(node.right);
        // Rob this node -> both children MUST be skipped (adjacency constraint along edges).
        int robThis = node.val + left[1] + right[1];
        // Skip this node -> each child is independently free: take its better option.
        int skipThis = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        return new int[]{robThis, skipThis};
    }

    public static void main(String[] args) {
        // Tree [3,2,3,null,3,null,1]: rob 3 + 3 + 1 = 7
        TreeNode a = new TreeNode(3);
        a.left = new TreeNode(2); a.right = new TreeNode(3);
        a.left.right = new TreeNode(3); a.right.right = new TreeNode(1);
        assert rob(a) == 7;

        // Tree [3,4,5,1,3,null,1]: rob 4 + 5 = 9
        TreeNode b = new TreeNode(3);
        b.left = new TreeNode(4); b.right = new TreeNode(5);
        b.left.left = new TreeNode(1); b.left.right = new TreeNode(3);
        b.right.right = new TreeNode(1);
        assert rob(b) == 9;

        assert rob(null) == 0;
        System.out.println("HouseRobberIII: OK");
    }
}
