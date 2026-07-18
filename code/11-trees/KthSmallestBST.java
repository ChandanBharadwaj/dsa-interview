import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * LeetCode 230 — Kth Smallest in a BST. Flatten-then-index vs iterative
 * in-order stopping at k. Mirrors pages/11-trees.html.
 */
public class KthSmallestBST {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static int kthSmallestFlatten(TreeNode root, int k) {
        List<Integer> sorted = new ArrayList<>();
        flatten(root, sorted);                    // in-order = ascending values
        return sorted.get(k - 1);
    }

    private static void flatten(TreeNode n, List<Integer> out) {
        if (n == null) return;
        flatten(n.left, out);
        out.add(n.val);
        flatten(n.right, out);
    }

    public static int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {                 // dive left as far as possible,
                stack.push(cur);                  // remembering the path
                cur = cur.left;
            }
            cur = stack.pop();                    // next node in ASCENDING order
            if (--k == 0) return cur.val;         // the k-th visit is the answer
            cur = cur.right;                      // then explore its right subtree
        }
        throw new IllegalArgumentException("k out of range");
    }

    public static void main(String[] args) {
        // BST [5,3,6,2,4,null,null,1]
        TreeNode r = new TreeNode(5);
        r.left = new TreeNode(3); r.right = new TreeNode(6);
        r.left.left = new TreeNode(2); r.left.right = new TreeNode(4);
        r.left.left.left = new TreeNode(1);
        assert kthSmallest(r, 3) == 3;
        assert kthSmallest(r, 1) == 1;
        assert kthSmallest(r, 6) == 6;
        assert kthSmallestFlatten(r, 3) == 3;
        System.out.println("KthSmallestBST: OK");
    }
}
