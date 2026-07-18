import java.util.*;

public class RangeModule {

    // TreeMap of disjoint tracked intervals: key = start, value = end.
    // Every operation touches only the (few) neighbors that can overlap.
    private final TreeMap<Integer, Integer> map = new TreeMap<>();

    public void addRange(int left, int right) {
        // Absorb every interval overlapping [left, right).
        Map.Entry<Integer, Integer> e = map.floorEntry(left);
        if (e != null && e.getValue() >= left) {        // extends into us
            left = Math.min(left, e.getKey());
            right = Math.max(right, e.getValue());
            map.remove(e.getKey());
        }
        while ((e = map.ceilingEntry(left)) != null && e.getKey() <= right) {
            right = Math.max(right, e.getValue());      // swallow it
            map.remove(e.getKey());
        }
        map.put(left, right);
    }

    public boolean queryRange(int left, int right) {
        Map.Entry<Integer, Integer> e = map.floorEntry(left);
        return e != null && e.getValue() >= right;      // one interval covers it
    }

    public void removeRange(int left, int right) {
        // Trim the neighbor that starts before us, keeping its left stub
        Map.Entry<Integer, Integer> e = map.floorEntry(left);
        if (e != null && e.getValue() > left) {
            int end = e.getValue();
            map.put(e.getKey(), left);                  // left stub survives
            if (end > right) map.put(right, end);       // right stub too
        }
        // Delete/trim intervals starting inside [left, right)
        while ((e = map.ceilingEntry(left)) != null && e.getKey() < right) {
            if (e.getValue() > right)
                map.put(right, e.getValue());           // right stub survives
            map.remove(e.getKey());
        }
    }

    public static void main(String[] args) {
        RangeModule rm = new RangeModule();
        rm.addRange(10, 20);
        rm.removeRange(14, 16);
        assert rm.queryRange(10, 14);
        assert !rm.queryRange(13, 15);
        assert rm.queryRange(16, 17);
        rm.addRange(14, 16);                    // heal the gap
        assert rm.queryRange(10, 20);
        rm.addRange(5, 8);
        rm.addRange(8, 10);                     // touching intervals merge
        assert rm.queryRange(5, 20);
        rm.removeRange(0, 30);
        assert !rm.queryRange(5, 6);
        // randomized cross-check against a boolean array oracle
        Random r = new Random(31);
        boolean[] oracle = new boolean[100];
        RangeModule rm2 = new RangeModule();
        for (int t = 0; t < 500; t++) {
            int a = r.nextInt(99), b = a + 1 + r.nextInt(99 - a);
            int op = r.nextInt(3);
            if (op == 0) {
                rm2.addRange(a, b);
                for (int i = a; i < b; i++) oracle[i] = true;
            } else if (op == 1) {
                rm2.removeRange(a, b);
                for (int i = a; i < b; i++) oracle[i] = false;
            } else {
                boolean expect = true;
                for (int i = a; i < b; i++) expect &= oracle[i];
                assert rm2.queryRange(a, b) == expect;
            }
        }
        System.out.println("RangeModule: OK");
    }
}
