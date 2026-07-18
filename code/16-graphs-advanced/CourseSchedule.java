import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * LeetCode 207 — Course Schedule. Kahn's topological sort:
 * finishable iff the prerequisite graph is a DAG (processed == n).
 * Mirrors pages/16-graphs-advanced.html.
 */
public class CourseSchedule {

    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        // Step 1: adjacency list + indegree. Pair [a, b] means edge b -> a (b unlocks a).
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) adj.add(new ArrayList<>());
        int[] indegree = new int[numCourses];
        for (int[] p : prerequisites) {
            adj.get(p[1]).add(p[0]);       // b -> a
            indegree[p[0]]++;              // a has one more unmet prerequisite
        }

        // Step 2: every course with zero prerequisites is takeable right now.
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++)
            if (indegree[i] == 0) queue.offer(i);

        // Step 3: take a ready course; that satisfies one prereq of each dependent.
        int taken = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();     // safe: all its prerequisites are done
            taken++;
            for (int next : adj.get(course))
                if (--indegree[next] == 0) // that was next's LAST unmet prerequisite
                    queue.offer(next);     // now takeable
        }

        // Step 4: a never-taken course means its indegree never hit 0 -> it sits on
        // (or behind) a directed cycle, so no valid order exists.
        return taken == numCourses;
    }

    public static void main(String[] args) {
        assert canFinish(2, new int[][]{{1, 0}});                    // take 0 then 1
        assert !canFinish(2, new int[][]{{1, 0}, {0, 1}});           // mutual dependency = cycle
        assert canFinish(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}}); // diamond DAG is fine
        assert canFinish(3, new int[][]{});                          // no prereqs at all
        System.out.println("CourseSchedule: OK");
    }
}
