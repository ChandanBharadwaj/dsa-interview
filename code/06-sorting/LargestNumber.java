import java.util.Arrays;

/**
 * LeetCode 179 — Largest Number. The whole problem is one comparator:
 * put a before b exactly when a+b > b+a as strings. Mirrors pages/06-sorting.html.
 */
public class LargestNumber {

    public static String largestNumber(int[] nums) {
        // work with strings — concatenation order is what we're optimizing
        String[] s = new String[nums.length];
        for (int i = 0; i < nums.length; i++) s[i] = String.valueOf(nums[i]);

        // order pair (a, b) by which concatenation is bigger: "b+a vs a+b" descending
        Arrays.sort(s, (a, b) -> (b + a).compareTo(a + b));

        if (s[0].equals("0")) return "0";     // all zeros collapse to a single "0"

        StringBuilder sb = new StringBuilder(); // O(n) build — never += in a loop
        for (String part : s) sb.append(part);
        return sb.toString();
    }

    public static void main(String[] args) {
        assert largestNumber(new int[]{10, 2}).equals("210");
        assert largestNumber(new int[]{3, 30, 34, 5, 9}).equals("9534330");
        assert largestNumber(new int[]{0, 0}).equals("0");
        System.out.println("LargestNumber: OK");
    }
}
