import java.util.*;

public class EvaluateDivision {

    // a/b = 2.0 is a weighted edge a -> b (2.0) and b -> a (0.5). A query
    // x/y is the PRODUCT of weights along any path x ~> y — DFS finds it.
    public static double[] calcEquation(List<List<String>> equations, double[] values,
                                        List<List<String>> queries) {
        Map<String, Map<String, Double>> graph = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            String a = equations.get(i).get(0), b = equations.get(i).get(1);
            graph.computeIfAbsent(a, k -> new HashMap<>()).put(b, values[i]);
            graph.computeIfAbsent(b, k -> new HashMap<>()).put(a, 1.0 / values[i]);
        }
        double[] out = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String x = queries.get(i).get(0), y = queries.get(i).get(1);
            if (!graph.containsKey(x) || !graph.containsKey(y))
                out[i] = -1.0;                       // unknown variable
            else
                out[i] = dfs(graph, x, y, 1.0, new HashSet<>());
        }
        return out;
    }

    private static double dfs(Map<String, Map<String, Double>> g, String curr,
                              String target, double product, Set<String> visited) {
        if (curr.equals(target)) return product;     // x/x = 1 handled here too
        visited.add(curr);
        for (Map.Entry<String, Double> e : g.get(curr).entrySet()) {
            if (visited.contains(e.getKey())) continue;
            double r = dfs(g, e.getKey(), target, product * e.getValue(), visited);
            if (r != -1.0) return r;                 // found a path — done
        }
        return -1.0;
    }

    public static void main(String[] args) {
        double[] r = calcEquation(
                List.of(List.of("a", "b"), List.of("b", "c")),
                new double[]{2.0, 3.0},
                List.of(List.of("a", "c"), List.of("b", "a"),
                        List.of("a", "e"), List.of("a", "a"), List.of("x", "x")));
        assert r[0] == 6.0 && r[1] == 0.5 && r[2] == -1.0 && r[3] == 1.0 && r[4] == -1.0;
        System.out.println("EvaluateDivision: OK");
    }
}
