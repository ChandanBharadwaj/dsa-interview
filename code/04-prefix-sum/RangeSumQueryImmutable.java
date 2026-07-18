// LeetCode 303. Range Sum Query - Immutable (LeetCode names this class NumArray)
public class RangeSumQueryImmutable {

    // prefix[i] = sum of the first i elements: nums[0] + ... + nums[i-1].
    // One extra slot (prefix[0] = 0) means no special case for ranges starting at 0.
    private final long[] prefix;

    public RangeSumQueryImmutable(int[] nums) {
        prefix = new long[nums.length + 1];          // prefix[0] stays 0: "sum of nothing"
        for (int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];     // extend the running total by one element
        }
    }

    // Sum of nums[left..right] inclusive, in O(1):
    // everything up to right, minus everything strictly before left.
    public int sumRange(int left, int right) {
        return (int) (prefix[right + 1] - prefix[left]);
    }

    public static void main(String[] args) {
        RangeSumQueryImmutable na = new RangeSumQueryImmutable(new int[]{-2, 0, 3, -5, 2, -1});
        assert na.sumRange(0, 2) == 1;    // -2 + 0 + 3
        assert na.sumRange(2, 5) == -1;   // 3 - 5 + 2 - 1
        assert na.sumRange(0, 5) == -3;   // the whole array
        System.out.println("RangeSumQueryImmutable: OK");
    }
}
