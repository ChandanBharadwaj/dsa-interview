import java.util.*;

public class RightSideView {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val; this.left = left; this.right = right;
        }
    }

    // LeetCode 199. BFS level order, keeping each level's LAST node —
    // that's the one visible from the right.
    public static List<Integer> rightSideView(TreeNode root) {
        List<Integer> view = new ArrayList<>();
        if (root == null) return view;
        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();                 // snapshot = this level
            for (int i = 0; i < size; i++) {
                TreeNode n = q.poll();
                if (i == size - 1) view.add(n.val);   // rightmost of the level
                if (n.left != null) q.offer(n.left);
                if (n.right != null) q.offer(n.right);
            }
        }
        return view;
    }

    public static void main(String[] args) {
        TreeNode t = new TreeNode(1,
                new TreeNode(2, null, new TreeNode(5)),
                new TreeNode(3, null, new TreeNode(4)));
        assert rightSideView(t).equals(Arrays.asList(1, 3, 4));
        // deep left branch pokes out where the right side is shorter
        TreeNode t2 = new TreeNode(1,
                new TreeNode(2, new TreeNode(4), null),
                new TreeNode(3));
        assert rightSideView(t2).equals(Arrays.asList(1, 3, 4));
        assert rightSideView(null).isEmpty();
        System.out.println("RightSideView: OK");
    }
}
