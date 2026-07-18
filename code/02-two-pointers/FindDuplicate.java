import java.util.*;

public class FindDuplicate {

    // Variation 1: HashSet — O(n) space, disallowed by the follow-up but the
    // honest first answer.
    public static int findDuplicateSet(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int x : nums)
            if (!seen.add(x)) return x;
        throw new IllegalArgumentException("no duplicate");
    }

    // Variation 2: Floyd's cycle detection — O(1) space, array untouched.
    // View i -> nums[i] as a linked list: n+1 slots, values 1..n, so some
    // value repeats = two arrows into the same node = a cycle whose entry
    // IS the duplicated value.
    public static int findDuplicate(int[] nums) {
        int slow = nums[0], fast = nums[0];
        do {                                // phase 1: meet inside the cycle
            slow = nums[slow];              // one hop
            fast = nums[nums[fast]];        // two hops
        } while (slow != fast);

        slow = nums[0];                     // phase 2: restart one pointer;
        while (slow != fast) {              // equal-speed walk meets at the
            slow = nums[slow];              // cycle entrance = the duplicate
            fast = nums[fast];
        }
        return slow;
    }

    public static void main(String[] args) {
        assert findDuplicate(new int[]{1, 3, 4, 2, 2}) == 2;
        assert findDuplicate(new int[]{3, 1, 3, 4, 2}) == 3;
        assert findDuplicate(new int[]{3, 3, 3, 3, 3}) == 3;
        Random r = new Random(7);
        for (int t = 0; t < 200; t++) {     // cross-check the two variants
            int n = 2 + r.nextInt(30);
            int[] a = new int[n + 1];
            for (int i = 0; i <= n; i++) a[i] = 1 + r.nextInt(n);
            // ensure at least one duplicate exists by construction (pigeonhole: always)
            assert findDuplicate(a.clone()) == findDuplicateSet(a.clone())
                 || countOf(a, findDuplicate(a.clone())) >= 2;
        }
        System.out.println("FindDuplicate: OK");
    }

    private static int countOf(int[] a, int v) {
        int c = 0;
        for (int x : a) if (x == v) c++;
        return c;
    }
}
