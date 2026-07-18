import java.util.*;

public class LevelOrder {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val; this.left = left; this.right = right;
        }
    }

    // LeetCode 102. Return node values grouped level by level, left to right.
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;         // empty tree -> no levels
        Deque<TreeNode> queue = new ArrayDeque<>(); // FIFO frontier (never Stack!)
        queue.offer(root);
        while (!queue.isEmpty()) {
            // KEY TRICK: freeze the queue size NOW — that count is exactly one
            // level, because children we enqueue below belong to the NEXT level.
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>(levelSize);
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();    // take from the front (FIFO)
                level.add(node.val);
                // enqueue children left-to-right so the next level stays ordered
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            result.add(level);                   // one completed level per pass
        }
        return result;
    }

    public static void main(String[] args) {
        // Tree:      3
        //           / \
        //          9  20
        //             / \
        //            15  7
        TreeNode root = new TreeNode(3,
                new TreeNode(9),
                new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        assert levelOrder(root).equals(
                List.of(List.of(3), List.of(9, 20), List.of(15, 7)));
        assert levelOrder(null).isEmpty();
        assert levelOrder(new TreeNode(1)).equals(List.of(List.of(1)));
        System.out.println("LevelOrder: OK");
    }
}
