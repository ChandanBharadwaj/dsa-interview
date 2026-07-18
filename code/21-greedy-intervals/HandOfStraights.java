import java.util.*;

public class HandOfStraights {

    // Greedy from the smallest remaining card: it MUST start a group (nothing
    // smaller can precede it), so its next groupSize-1 successors must exist.
    public static boolean isNStraightHand(int[] hand, int groupSize) {
        if (hand.length % groupSize != 0) return false;
        TreeMap<Integer, Integer> counts = new TreeMap<>();
        for (int card : hand) counts.merge(card, 1, Integer::sum);

        while (!counts.isEmpty()) {
            int start = counts.firstKey();          // forced group leader
            for (int v = start; v < start + groupSize; v++) {
                Integer c = counts.get(v);
                if (c == null) return false;        // gap — no straight possible
                if (c == 1) counts.remove(v);
                else counts.put(v, c - 1);
            }
        }
        return true;
    }

    public static void main(String[] args) {
        assert isNStraightHand(new int[]{1, 2, 3, 6, 2, 3, 4, 7, 8}, 3);
        assert !isNStraightHand(new int[]{1, 2, 3, 4, 5}, 4);   // 5 % 4 != 0
        assert isNStraightHand(new int[]{8, 10, 12}, 1);        // size 1: always
        assert !isNStraightHand(new int[]{1, 1, 2, 2, 3, 3}, 2);// pairs need gaps
        assert isNStraightHand(new int[]{1, 1, 2, 2, 3, 3}, 3);
        System.out.println("HandOfStraights: OK");
    }
}
