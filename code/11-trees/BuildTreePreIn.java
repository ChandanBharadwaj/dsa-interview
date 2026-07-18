import java.util.*;

public class BuildTreePreIn {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    // LeetCode 105. Preorder's head is the root; find it in inorder to learn
    // how many nodes the left subtree owns; recurse on the two spans.
    // A value->index map + a moving preorder cursor keep it O(n).
    private static int preIdx;
    private static Map<Integer, Integer> inPos;

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        preIdx = 0;
        inPos = new HashMap<>();
        for (int i = 0; i < inorder.length; i++)
            inPos.put(inorder[i], i);            // values are unique
        return build(preorder, 0, inorder.length - 1);
    }

    private static TreeNode build(int[] pre, int inLo, int inHi) {
        if (inLo > inHi) return null;            // empty span
        TreeNode root = new TreeNode(pre[preIdx++]);   // next preorder = root
        int split = inPos.get(root.val);         // root's slot in inorder
        root.left = build(pre, inLo, split - 1); // left span first — matches
        root.right = build(pre, split + 1, inHi);// preorder's left-then-right
        return root;
    }

    private static String render(TreeNode n) {   // preorder with nulls, to verify
        return n == null ? "#" : n.val + "(" + render(n.left) + "," + render(n.right) + ")";
    }

    public static void main(String[] args) {
        TreeNode t = buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7});
        assert render(t).equals("3(9(#,#),20(15(#,#),7(#,#)))");
        TreeNode s = buildTree(new int[]{1, 2}, new int[]{2, 1});   // left chain
        assert render(s).equals("1(2(#,#),#)");
        TreeNode r = buildTree(new int[]{1, 2}, new int[]{1, 2});   // right chain
        assert render(r).equals("1(#,2(#,#))");
        System.out.println("BuildTreePreIn: OK");
    }
}
