import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * LeetCode 210 — Course Schedule II. Kahn's algorithm recording the
 * take-order (which IS a topological order).
 * Mirrors pages/16-graphs-advanced.html.
 */
public class CourseScheduleII {

    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) adj.add(new ArrayList<>());
        int[] indegree = new int[numCourses];
        for (int[] p : prerequisites) {
            adj.get(p[1]).add(p[0]);
            indegree[p[0]]++;
        }

        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++)
            if (indegree[i] == 0) queue.offer(i);

        int[] order = new int[numCourses];
        int taken = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            order[taken++] = course;           // record the order courses are taken
            for (int next : adj.get(course))
                if (--indegree[next] == 0)
                    queue.offer(next);
        }
        return taken == numCourses ? order : new int[0];   // cycle -> no valid order
    }

    private static boolean validOrder(int[] order, int n, int[][] prereqs) {
        if (order.length != n) return false;
        int[] pos = new int[n];
        for (int i = 0; i < n; i++) pos[order[i]] = i;
        for (int[] p : prereqs)
            if (pos[p[1]] > pos[p[0]]) return false;   // b must come before a
        return true;
    }

    public static void main(String[] args) {
        int[][] pre = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};
        assert validOrder(findOrder(4, pre), 4, pre);
        assert Arrays.equals(findOrder(2, new int[][]{{1, 0}, {0, 1}}), new int[0]); // cycle
        assert validOrder(findOrder(1, new int[][]{}), 1, new int[][]{});
        System.out.println("CourseScheduleII: OK");
    }
}
