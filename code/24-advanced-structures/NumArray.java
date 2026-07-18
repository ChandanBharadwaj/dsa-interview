public class NumArray {

    // Fenwick tree / Binary Indexed Tree, 1-based. tree[i] stores the sum of
    // the (i & -i) elements ending at position i — that lowest-set-bit trick
    // from page 22 is the whole data structure.
    private final int[] tree;
    private final int[] nums;   // current values, kept so update() can compute a delta
    private final int n;

    public NumArray(int[] nums) {
        this.n = nums.length;
        this.nums = new int[n];
        this.tree = new int[n + 1];               // index 0 unused: 0 has no lowest set bit
        for (int i = 0; i < n; i++) update(i, nums[i]);   // n * O(log n) build
    }

    public void update(int index, int val) {
        int delta = val - nums[index];            // BIT stores sums, so apply the CHANGE
        nums[index] = val;
        for (int i = index + 1; i <= n; i += i & (-i))    // hop to every covering range
            tree[i] += delta;
    }

    public int sumRange(int left, int right) {
        return prefix(right + 1) - prefix(left);  // classic prefix-sum subtraction (page 04)
    }

    // Sum of the first i elements: strip the lowest set bit until 0.
    private int prefix(int i) {
        int s = 0;
        for (; i > 0; i -= i & (-i))
            s += tree[i];
        return s;
    }

    // ---- test harness ----
    public static void main(String[] args) {
        NumArray a = new NumArray(new int[]{1, 3, 5});
        assert a.sumRange(0, 2) == 9;
        a.update(1, 2);                // array is now [1, 2, 5]
        assert a.sumRange(0, 2) == 8;
        assert a.sumRange(1, 2) == 7;
        assert a.sumRange(2, 2) == 5;
        System.out.println("NumArray: OK");
    }
}
