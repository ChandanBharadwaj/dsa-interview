public class Candy {

    // Two independent constraints -> two sweeps. Left pass fixes "higher than
    // left neighbor"; right pass fixes "higher than right neighbor"; taking
    // the max satisfies both simultaneously.
    public static int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        java.util.Arrays.fill(candies, 1);          // everyone gets at least one

        for (int i = 1; i < n; i++)                 // left-to-right climbs
            if (ratings[i] > ratings[i - 1])
                candies[i] = candies[i - 1] + 1;

        for (int i = n - 2; i >= 0; i--)            // right-to-left climbs
            if (ratings[i] > ratings[i + 1])
                candies[i] = Math.max(candies[i],   // max keeps the left rule
                        candies[i + 1] + 1);

        int total = 0;
        for (int c : candies) total += c;
        return total;
    }

    public static void main(String[] args) {
        assert candy(new int[]{1, 0, 2}) == 5;      // 2,1,2
        assert candy(new int[]{1, 2, 2}) == 4;      // 1,2,1 (equal needs no more)
        assert candy(new int[]{1, 3, 2, 2, 1}) == 7;
        assert candy(new int[]{5}) == 1;
        assert candy(new int[]{1, 2, 3, 4, 5}) == 15;
        System.out.println("Candy: OK");
    }
}
