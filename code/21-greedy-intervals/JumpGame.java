// LeetCode 55 — Jump Game. Greedy: track the furthest index reachable so far.
public class JumpGame {

    public static boolean canJump(int[] nums) {
        int furthest = 0;                                 // furthest index we can reach with jumps seen so far
        for (int i = 0; i < nums.length; i++) {
            if (i > furthest) return false;               // we can't even stand on i -> stuck before the end
            furthest = Math.max(furthest, i + nums[i]);   // jumping from i could extend our reach to i + nums[i]
            if (furthest >= nums.length - 1) return true; // last index is already within reach -> done early
        }
        return true;                                      // loop finished => every index (incl. last) was reachable
    }

    public static void main(String[] args) {
        assert canJump(new int[]{2, 3, 1, 1, 4});         // 0->1->4
        assert !canJump(new int[]{3, 2, 1, 0, 4});        // everything funnels into the 0 at index 3
        assert canJump(new int[]{0});                     // already standing on the last index
        System.out.println("JumpGame: OK");
    }
}
