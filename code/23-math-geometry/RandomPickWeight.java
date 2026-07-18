import java.util.Random;

/**
 * LeetCode 528 — Random Pick with Weight. Prefix sums turn weights into
 * ranges; a random dart + lowerBound binary search picks the winner.
 * Mirrors pages/23-math-geometry.html.
 */
public class RandomPickWeight {

    private final int[] prefix;      // prefix[i] = w[0] + ... + w[i]
    private final int total;
    private final Random rand = new Random();

    public RandomPickWeight(int[] w) {
        prefix = new int[w.length];
        int sum = 0;
        for (int i = 0; i < w.length; i++) {
            sum += w[i];
            prefix[i] = sum;         // weights become adjacent ranges on a line
        }
        total = sum;
    }

    public int pickIndex() {
        // Throw a dart uniformly into [1..total]; whichever range it lands
        // in wins — bigger weights own proportionally bigger ranges.
        int dart = rand.nextInt(total) + 1;
        // First index whose prefix >= dart (lowerBound).
        int lo = 0, hi = prefix.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (prefix[mid] >= dart) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }

    public static void main(String[] args) {
        RandomPickWeight p = new RandomPickWeight(new int[]{1, 3});
        int trials = 40_000;
        int[] counts = new int[2];
        for (int i = 0; i < trials; i++) counts[p.pickIndex()]++;
        // Expect ~25% / ~75%, allow generous slack
        assert Math.abs(counts[0] - trials * 0.25) < trials * 0.03;
        assert Math.abs(counts[1] - trials * 0.75) < trials * 0.03;
        RandomPickWeight single = new RandomPickWeight(new int[]{7});
        assert single.pickIndex() == 0;
        System.out.println("RandomPickWeight: OK");
    }
}
