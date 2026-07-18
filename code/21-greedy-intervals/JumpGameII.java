public class JumpGameII {

    // Implicit BFS layers: [left..right] is everything reachable in `jumps`
    // hops; the next layer extends to the furthest reach from this one.
    public static int jump(int[] nums) {
        int jumps = 0;
        int right = 0;              // end of the current BFS layer
        int furthest = 0;           // furthest reach from within the layer
        for (int i = 0; i < nums.length - 1; i++) {   // note: last index excluded
            furthest = Math.max(furthest, i + nums[i]);
            if (i == right) {       // layer exhausted — must jump again
                jumps++;
                right = furthest;
            }
        }
        return jumps;
    }

    public static void main(String[] args) {
        assert jump(new int[]{2, 3, 1, 1, 4}) == 2;   // 0 -> 1 -> 4
        assert jump(new int[]{2, 3, 0, 1, 4}) == 2;
        assert jump(new int[]{0}) == 0;
        assert jump(new int[]{1, 2}) == 1;
        assert jump(new int[]{1, 1, 1, 1}) == 3;
        System.out.println("JumpGameII: OK");
    }
}
