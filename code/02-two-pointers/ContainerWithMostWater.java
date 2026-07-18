// LeetCode 11 — Container With Most Water
// Pattern: converging pointers with a greedy discard argument:
// always move the SHORTER wall inward.
public class ContainerWithMostWater {

    public static int maxArea(int[] height) {
        int l = 0, r = height.length - 1;    // widest possible container first
        int best = 0;
        while (l < r) {
            // Water level is capped by the SHORTER wall; width is the gap
            int area = Math.min(height[l], height[r]) * (r - l);
            best = Math.max(best, area);
            // Move the shorter wall inward. Why it's safe: any container
            // keeping the shorter wall is narrower (width shrinks) AND still
            // capped by that same short wall — it can never beat what we just
            // measured. So nothing is lost by discarding it.
            if (height[l] < height[r]) {
                l++;
            } else {
                r--;
            }
        }
        return best;
    }
    // Time O(n): each step retires one wall permanently.
    // Space O(1).

    public static void main(String[] args) {
        assert maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}) == 49;  // walls 8 and 7, width 7
        assert maxArea(new int[]{1, 1}) == 1;
        assert maxArea(new int[]{4, 3, 2, 1, 4}) == 16;
        System.out.println("ContainerWithMostWater: OK");
    }
}
