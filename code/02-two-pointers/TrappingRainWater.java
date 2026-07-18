/**
 * LeetCode 42 — Trapping Rain Water. Three variations from the page:
 * per-bar rescans O(n^2), prefix-max arrays O(n)/O(n), converging
 * pointers O(n)/O(1). Mirrors pages/02-two-pointers.html.
 */
public class TrappingRainWater {

    public static int trapBrute(int[] height) {
        int total = 0;
        for (int i = 0; i < height.length; i++) {
            int maxL = 0, maxR = 0;
            for (int j = 0; j <= i; j++) maxL = Math.max(maxL, height[j]);   // scan left
            for (int j = i; j < height.length; j++) maxR = Math.max(maxR, height[j]); // scan right
            total += Math.min(maxL, maxR) - height[i];   // water column above bar i
        }
        return total;
    }

    public static int trapPrefix(int[] height) {
        int n = height.length;
        if (n == 0) return 0;
        int[] maxL = new int[n], maxR = new int[n];
        maxL[0] = height[0];
        for (int i = 1; i < n; i++)             // tallest bar at or left of i
            maxL[i] = Math.max(maxL[i - 1], height[i]);
        maxR[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--)        // tallest bar at or right of i
            maxR[i] = Math.max(maxR[i + 1], height[i]);
        int total = 0;
        for (int i = 0; i < n; i++)
            total += Math.min(maxL[i], maxR[i]) - height[i];
        return total;
    }

    public static int trap(int[] height) {
        int l = 0, r = height.length - 1;
        int maxL = 0, maxR = 0;              // tallest bar seen so far from each end
        int total = 0;
        while (l < r) {
            if (height[l] < height[r]) {
                // The KEY step: the true right max for l is at least
                // height[r] > height[l], so min(maxL, trueMaxR) = maxL here.
                // The left side's water is fully decided — settle it.
                maxL = Math.max(maxL, height[l]);
                total += maxL - height[l];   // 0 if height[l] is a new max
                l++;
            } else {
                maxR = Math.max(maxR, height[r]);
                total += maxR - height[r];
                r--;
            }
        }
        return total;
    }

    public static void main(String[] args) {
        int[] a = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        assert trap(a) == 6 && trapPrefix(a) == 6 && trapBrute(a) == 6;
        int[] b = {4, 2, 0, 3, 2, 5};
        assert trap(b) == 9 && trapPrefix(b) == 9 && trapBrute(b) == 9;
        assert trap(new int[]{}) == 0;
        System.out.println("TrappingRainWater: OK");
    }
}
