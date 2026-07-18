import java.util.*;

public class ReorganizeString {

    // Greedy: always place the most plentiful letter that isn't the one just
    // placed. A max-heap by remaining count makes "most plentiful" O(log 26).
    public static String reorganizeString(String s) {
        int[] counts = new int[26];
        for (char c : s.toCharArray()) counts[c - 'a']++;

        // Feasibility: the majority letter must fit into every other slot.
        int max = 0;
        for (int c : counts) max = Math.max(max, c);
        if (max > (s.length() + 1) / 2) return "";

        PriorityQueue<int[]> heap =                       // {letter, count}
                new PriorityQueue<>((a, b) -> b[1] - a[1]);
        for (int i = 0; i < 26; i++)
            if (counts[i] > 0) heap.offer(new int[]{i, counts[i]});

        StringBuilder sb = new StringBuilder();
        int[] hold = null;                                // cooling off: last placed
        while (!heap.isEmpty()) {
            int[] top = heap.poll();
            sb.append((char) ('a' + top[0]));
            top[1]--;
            if (hold != null) heap.offer(hold);           // last letter usable again
            hold = top[1] > 0 ? top : null;               // cool the one just used
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String r = reorganizeString("aab");
        assert r.length() == 3 && noAdjacent(r);
        assert reorganizeString("aaab").isEmpty();        // majority can't fit
        assert noAdjacent(reorganizeString("vvvlo"));
        assert reorganizeString("a").equals("a");
        System.out.println("ReorganizeString: OK");
    }

    private static boolean noAdjacent(String s) {
        for (int i = 1; i < s.length(); i++)
            if (s.charAt(i) == s.charAt(i - 1)) return false;
        return true;
    }
}
