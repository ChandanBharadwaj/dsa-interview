public class SegmentTree {

    // Array-backed segment tree for range SUM. Node i covers a segment [lo..hi];
    // its children 2i+1 / 2i+2 cover the two halves (same trick as heaps, page 12).
    // 4n slots is always enough, even for non-power-of-two n.
    private final int[] tree;
    private final int n;

    public SegmentTree(int[] nums) {
        n = nums.length;
        tree = new int[4 * n];
        build(0, 0, n - 1, nums);
    }

    private void build(int node, int lo, int hi, int[] nums) {
        if (lo == hi) { tree[node] = nums[lo]; return; }        // leaf = one element
        int mid = lo + (hi - lo) / 2;
        build(2 * node + 1, lo, mid, nums);                     // left child = left half
        build(2 * node + 2, mid + 1, hi, nums);                 // right child = right half
        tree[node] = tree[2 * node + 1] + tree[2 * node + 2];   // parent = merge of children
    }

    public void update(int index, int val) { update(0, 0, n - 1, index, val); }

    private void update(int node, int lo, int hi, int index, int val) {
        if (lo == hi) { tree[node] = val; return; }             // reached the leaf
        int mid = lo + (hi - lo) / 2;
        if (index <= mid) update(2 * node + 1, lo, mid, index, val);
        else              update(2 * node + 2, mid + 1, hi, index, val);
        tree[node] = tree[2 * node + 1] + tree[2 * node + 2];   // re-merge on the way up
    }

    public int query(int left, int right) { return query(0, 0, n - 1, left, right); }

    private int query(int node, int lo, int hi, int left, int right) {
        if (right < lo || hi < left) return 0;                  // no overlap: sum identity
        if (left <= lo && hi <= right) return tree[node];       // fully covered: cached sum
        int mid = lo + (hi - lo) / 2;                           // partial overlap: recurse
        return query(2 * node + 1, lo, mid, left, right)
             + query(2 * node + 2, mid + 1, hi, left, right);
    }

    // ---- test harness ----
    public static void main(String[] args) {
        SegmentTree st = new SegmentTree(new int[]{2, 5, 1, 4, 9, 3});
        assert st.query(0, 5) == 24;   // whole array
        assert st.query(1, 3) == 10;   // 5 + 1 + 4
        st.update(2, 7);               // array is now [2, 5, 7, 4, 9, 3]
        assert st.query(1, 3) == 16;
        assert st.query(0, 5) == 30;
        System.out.println("SegmentTree: OK");
    }
}
