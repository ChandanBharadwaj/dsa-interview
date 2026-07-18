import java.util.*;

public class IPO {

    // Two heaps, opposite orders: a min-heap of projects by capital needed
    // (locked until affordable) and a max-heap of profits (affordable now).
    // Each round: unlock everything you can afford, then take the best profit.
    public static int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        int[][] projects = new int[n][2];
        for (int i = 0; i < n; i++)
            projects[i] = new int[]{capital[i], profits[i]};
        Arrays.sort(projects, (a, b) -> a[0] - b[0]);   // by capital needed

        PriorityQueue<Integer> affordable =             // profits, max first
                new PriorityQueue<>(Collections.reverseOrder());
        int next = 0;
        for (int round = 0; round < k; round++) {
            while (next < n && projects[next][0] <= w)  // unlock newly affordable
                affordable.offer(projects[next++][1]);
            if (affordable.isEmpty()) break;            // nothing in reach — stop
            w += affordable.poll();                     // do the most profitable
        }
        return w;
    }

    public static void main(String[] args) {
        assert findMaximizedCapital(2, 0, new int[]{1, 2, 3}, new int[]{0, 1, 1}) == 4;
        assert findMaximizedCapital(3, 0, new int[]{1, 2, 3}, new int[]{0, 1, 2}) == 6;
        assert findMaximizedCapital(1, 2, new int[]{1, 2, 3}, new int[]{1, 1, 2}) == 5;
        assert findMaximizedCapital(2, 0, new int[]{1, 2, 3}, new int[]{1, 1, 2}) == 0;  // can't start
        System.out.println("IPO: OK");
    }
}
