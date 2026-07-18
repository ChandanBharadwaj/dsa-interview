import java.util.*;

public class NextGreaterElementI {

    // Monotonic decreasing stack over nums2: when x arrives, everything
    // smaller on the stack just found its next greater element = x.
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> nextGreater = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();     // values, decreasing
        for (int x : nums2) {
            while (!stack.isEmpty() && stack.peek() < x)
                nextGreater.put(stack.pop(), x);       // x resolves them all
            stack.push(x);
        }
        int[] out = new int[nums1.length];             // unresolved -> -1
        for (int i = 0; i < nums1.length; i++)
            out[i] = nextGreater.getOrDefault(nums1[i], -1);
        return out;
    }

    public static void main(String[] args) {
        assert Arrays.equals(nextGreaterElement(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2}),
                new int[]{-1, 3, -1});
        assert Arrays.equals(nextGreaterElement(new int[]{2, 4}, new int[]{1, 2, 3, 4}),
                new int[]{3, -1});
        System.out.println("NextGreaterElementI: OK");
    }
}
