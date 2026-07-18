import java.util.*;

public class Combinations {

    // Choose k numbers from 1..n. The `start` parameter enforces ascending
    // picks, which is what prevents duplicate sets like {1,2} and {2,1}.
    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(1, n, k, new ArrayList<>(), res);
        return res;
    }

    private static void backtrack(int start, int n, int k,
                                  List<Integer> path, List<List<Integer>> res) {
        if (path.size() == k) {                  // picked enough — record
            res.add(new ArrayList<>(path));
            return;
        }
        // Prune: need (k - path.size()) more numbers; the highest useful
        // start leaves exactly that many in [i, n].
        int need = k - path.size();
        for (int i = start; i <= n - need + 1; i++) {
            path.add(i);                         // choose
            backtrack(i + 1, n, k, path, res);   // explore from above i
            path.remove(path.size() - 1);        // unchoose
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> r = combine(4, 2);
        assert r.size() == 6;                    // C(4,2)
        assert r.contains(Arrays.asList(1, 2)) && r.contains(Arrays.asList(3, 4));
        assert combine(1, 1).equals(List.of(List.of(1)));
        assert combine(5, 3).size() == 10;       // C(5,3)
        System.out.println("Combinations: OK");
    }
}
