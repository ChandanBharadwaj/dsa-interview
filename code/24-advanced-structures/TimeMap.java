import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LeetCode 981 — Time Based Key-Value Store. Append-only version lists
 * per key + floor binary search. Mirrors pages/24-advanced-structures.html.
 */
public class TimeMap {

    // key -> its versions, appended in timestamp order (so each list is
    // ALREADY SORTED — that's what makes binary search legal).
    private final Map<String, List<int[]>> times = new HashMap<>();   // {timestamp}
    private final Map<String, List<String>> vals = new HashMap<>();

    public void set(String key, String value, int timestamp) {
        times.computeIfAbsent(key, k -> new ArrayList<>()).add(new int[]{timestamp});
        vals.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }

    public String get(String key, int timestamp) {
        List<int[]> ts = times.get(key);
        if (ts == null) return "";
        // Floor search: LAST index with ts[i] <= timestamp.
        int lo = 0, hi = ts.size() - 1, ans = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (ts.get(mid)[0] <= timestamp) {
                ans = mid;                   // candidate; maybe a later one fits too
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return ans == -1 ? "" : vals.get(key).get(ans);
    }

    public static void main(String[] args) {
        TimeMap tm = new TimeMap();
        tm.set("foo", "bar", 1);
        assert tm.get("foo", 1).equals("bar");
        assert tm.get("foo", 3).equals("bar");
        tm.set("foo", "bar2", 4);
        assert tm.get("foo", 4).equals("bar2");
        assert tm.get("foo", 3).equals("bar");
        assert tm.get("foo", 0).isEmpty();
        assert tm.get("nope", 5).isEmpty();
        System.out.println("TimeMap: OK");
    }
}
