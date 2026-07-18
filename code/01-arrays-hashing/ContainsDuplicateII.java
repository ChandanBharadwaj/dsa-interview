import java.util.*;

public class ContainsDuplicateII {

    // A HashSet holding only the last k elements — a sliding window of
    // membership. Seeing a value already in the set means a duplicate
    // within distance k.
    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> window = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (i > k)
                window.remove(nums[i - k - 1]);   // evict the element that fell out of range
            if (!window.add(nums[i]))             // add() is false if already present
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        assert containsNearbyDuplicate(new int[]{1, 2, 3, 1}, 3);
        assert containsNearbyDuplicate(new int[]{1, 0, 1, 1}, 1);
        assert !containsNearbyDuplicate(new int[]{1, 2, 3, 1, 2, 3}, 2);
        System.out.println("ContainsDuplicateII: OK");
    }
}
