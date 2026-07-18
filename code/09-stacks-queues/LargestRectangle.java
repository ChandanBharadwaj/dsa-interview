import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 84 — Largest Rectangle in Histogram. Brute expand-around vs
 * increasing monotonic stack with a height-0 sentinel flush.
 * Mirrors pages/09-stacks-queues.html.
 */
public class LargestRectangle {

    public static int largestRectangleBrute(int[] heights) {
        int best = 0;
        for (int i = 0; i < heights.length; i++) {
            // The rectangle of height heights[i] extends while bars stay >= it
            int l = i, r = i;
            while (l > 0 && heights[l - 1] >= heights[i]) l--;
            while (r < heights.length - 1 && heights[r + 1] >= heights[i]) r++;
            best = Math.max(best, heights[i] * (r - l + 1));
        }
        return best;
    }

    public static int largestRectangleArea(int[] heights) {
        int n = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();  // indices; heights INCREASE toward the top
        int best = 0;
        // Iterate one past the end with a virtual height-0 bar: it forces
        // every remaining bar to pop and be evaluated (the "sentinel flush").
        for (int i = 0; i <= n; i++) {
            int h = (i == n) ? 0 : heights[i];
            while (!stack.isEmpty() && h < heights[stack.peek()]) {
                // Popping bar j: its rectangle is now fully determined.
                int j = stack.pop();
                int height = heights[j];
                // Right boundary: i (first bar shorter on the right).
                // Left boundary: new stack top (first bar shorter on the left),
                // or the very start if the stack emptied.
                int left = stack.isEmpty() ? -1 : stack.peek();
                int width = i - left - 1;
                best = Math.max(best, height * width);
            }
            stack.push(i);
        }
        return best;
    }

    public static void main(String[] args) {
        assert largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}) == 10;
        assert largestRectangleArea(new int[]{2, 4}) == 4;
        assert largestRectangleArea(new int[]{5}) == 5;
        assert largestRectangleBrute(new int[]{2, 1, 5, 6, 2, 3}) == 10;
        java.util.Random r = new java.util.Random(7);
        for (int t = 0; t < 30; t++) {
            int[] h = new int[20];
            for (int i = 0; i < h.length; i++) h[i] = r.nextInt(10);
            assert largestRectangleArea(h) == largestRectangleBrute(h);
        }
        System.out.println("LargestRectangle: OK");
    }
}
