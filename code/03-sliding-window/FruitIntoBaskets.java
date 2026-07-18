import java.util.*;

public class FruitIntoBaskets {

    // "Longest window with at most 2 distinct values" wearing a story:
    // count map tracks the window's contents; shrink when a third type appears.
    public static int totalFruit(int[] fruits) {
        Map<Integer, Integer> count = new HashMap<>();
        int best = 0, left = 0;
        for (int right = 0; right < fruits.length; right++) {
            count.merge(fruits[right], 1, Integer::sum);       // take the fruit
            while (count.size() > 2) {                         // third type -> illegal
                int f = fruits[left++];
                if (count.merge(f, -1, Integer::sum) == 0)     // drop from the left
                    count.remove(f);                           // type fully gone
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    public static void main(String[] args) {
        assert totalFruit(new int[]{1, 2, 1}) == 3;
        assert totalFruit(new int[]{0, 1, 2, 2}) == 3;         // [1,2,2]
        assert totalFruit(new int[]{1, 2, 3, 2, 2}) == 4;      // [2,3,2,2]
        assert totalFruit(new int[]{3, 3, 3, 1, 2, 1, 1, 2, 3, 3, 4}) == 5;
        System.out.println("FruitIntoBaskets: OK");
    }
}
