import java.util.Arrays;

/**
 * LeetCode 88 — Merge Sorted Array. Backwards merge into the free tail:
 * O(m+n) time, O(1) space. Mirrors pages/06-sorting.html.
 */
public class MergeSortedArray {

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;          // last real element of nums1
        int j = n - 1;          // last element of nums2
        int w = m + n - 1;      // last slot of nums1 — write position

        // Merge from the BACK: the free space is at the end, so writing
        // backwards never overwrites an unread nums1 element.
        while (j >= 0) {                       // nums2 exhausted => rest is already in place
            if (i >= 0 && nums1[i] > nums2[j])
                nums1[w--] = nums1[i--];       // nums1's tail element is bigger — place it
            else
                nums1[w--] = nums2[j--];       // nums2's tail element is bigger (or nums1 done)
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 0, 0, 0};
        merge(a, 3, new int[]{2, 5, 6}, 3);
        assert Arrays.equals(a, new int[]{1, 2, 2, 3, 5, 6});
        int[] b = {0};
        merge(b, 0, new int[]{1}, 1);
        assert Arrays.equals(b, new int[]{1});
        int[] c = {2, 0};
        merge(c, 1, new int[]{1}, 1);
        assert Arrays.equals(c, new int[]{1, 2});
        System.out.println("MergeSortedArray: OK");
    }
}
