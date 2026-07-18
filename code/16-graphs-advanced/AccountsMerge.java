import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * LeetCode 721 — Accounts Merge. Union accounts through shared emails,
 * then group emails under each root (identity resolution).
 * Mirrors pages/16-graphs-advanced.html.
 */
public class AccountsMerge {

    static class UnionFind {
        int[] parent, size;
        int count;
        UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            count = n;
            for (int i = 0; i < n; i++) { parent[i] = i; size[i] = 1; }
        }
        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);  // path compression
            return parent[x];
        }
        boolean union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return false;
            if (size[ra] < size[rb]) { int t = ra; ra = rb; rb = t; }
            parent[rb] = ra;                    // smaller tree under larger
            size[ra] += size[rb];
            count--;
            return true;
        }
    }

    public static List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();
        UnionFind uf = new UnionFind(n);            // one set per ACCOUNT index
        Map<String, Integer> emailOwner = new HashMap<>();  // email -> first account seen

        // Pass 1: an email seen in two accounts unions those accounts.
        for (int i = 0; i < n; i++)
            for (int j = 1; j < accounts.get(i).size(); j++) {
                String email = accounts.get(i).get(j);
                Integer owner = emailOwner.putIfAbsent(email, i);
                if (owner != null) uf.union(owner, i);   // same email, same person
            }

        // Pass 2: bucket every email under its account's ROOT.
        Map<Integer, TreeSet<String>> groups = new HashMap<>();  // TreeSet: sorted + deduped
        for (int i = 0; i < n; i++) {
            int root = uf.find(i);
            groups.computeIfAbsent(root, k -> new TreeSet<>())
                  .addAll(accounts.get(i).subList(1, accounts.get(i).size()));
        }

        // Pass 3: emit [name, emails...] per group (name from any member account).
        List<List<String>> res = new ArrayList<>();
        for (Map.Entry<Integer, TreeSet<String>> e : groups.entrySet()) {
            List<String> row = new ArrayList<>();
            row.add(accounts.get(e.getKey()).get(0));   // the person's name
            row.addAll(e.getValue());
            res.add(row);
        }
        return res;
    }

    public static void main(String[] args) {
        List<List<String>> accounts = List.of(
            List.of("John", "j1@m.co", "j2@m.co"),
            List.of("John", "j3@m.co"),
            List.of("John", "j1@m.co", "j4@m.co"),
            List.of("Mary", "mary@m.co"));
        List<List<String>> res = accountsMerge(accounts);
        assert res.size() == 3;
        boolean foundMerged = false;
        for (List<String> row : res)
            if (row.size() == 4 && row.get(0).equals("John")
                    && row.contains("j1@m.co") && row.contains("j2@m.co") && row.contains("j4@m.co"))
                foundMerged = true;
        assert foundMerged;
        System.out.println("AccountsMerge: OK");
    }
}
