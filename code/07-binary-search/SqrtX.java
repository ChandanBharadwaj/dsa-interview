public class SqrtX {

    // Binary search on the answer space [0, x]: find the LAST k with k*k <= x.
    public static int mySqrt(int x) {
        int lo = 0, hi = x, ans = 0;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if ((long) mid * mid <= x) {    // long: mid*mid overflows int
                ans = mid;                  // candidate; try bigger
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        assert mySqrt(4) == 2;
        assert mySqrt(8) == 2;              // floor, not round
        assert mySqrt(0) == 0 && mySqrt(1) == 1;
        assert mySqrt(2147395599) == 46339; // near int max — overflow probe
        for (int i = 0; i <= 1000; i++)
            assert mySqrt(i) == (int) Math.sqrt(i);
        System.out.println("SqrtX: OK");
    }
}
