import java.util.Arrays;
import java.util.Random;

/**
 * LeetCode 384 — Shuffle an Array, via Fisher-Yates.
 * Uniform over all n! permutations because each i swaps with a uniform
 * index in [0..i]. Mirrors pages/23-math-geometry.html.
 */
public class ShuffleArray {

    private final int[] original;          // pristine copy for reset()
    private int[] current;
    private final Random rand = new Random();

    public ShuffleArray(int[] nums) {
        original = nums.clone();           // defensive copies — don't alias caller's array
        current = nums.clone();
    }

    public int[] reset() {
        current = original.clone();        // hand back (a copy of) the original order
        return current;
    }

    public int[] shuffle() {
        for (int i = current.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);   // uniform in [0..i] — the i+1 bound IS the algorithm
            int t = current[i]; current[i] = current[j]; current[j] = t;
        }
        return current;
    }

    public static void main(String[] args) {
        int[] base = {1, 2, 3, 4, 5};
        ShuffleArray s = new ShuffleArray(base);

        int[] shuffled = s.shuffle();
        int[] sorted = shuffled.clone();
        Arrays.sort(sorted);
        assert Arrays.equals(sorted, base);            // a permutation: same multiset

        assert Arrays.equals(s.reset(), base);         // reset restores original order

        // Rough uniformity check: over many shuffles of [0,1,2], each of the
        // 6 permutations should appear about 1/6 of the time.
        int trials = 60_000;
        int[] counts = new int[6];
        ShuffleArray t = new ShuffleArray(new int[]{0, 1, 2});
        for (int i = 0; i < trials; i++) {
            int[] p = t.shuffle();
            counts[p[0] * 2 + (p[1] > p[2] ? 1 : 0)]++;
            t.reset();
        }
        for (int c : counts)
            assert Math.abs(c - trials / 6.0) < trials * 0.02; // within 2% of fair
        System.out.println("ShuffleArray: OK");
    }
}
