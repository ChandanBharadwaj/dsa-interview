import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomizedSet {

    // The trick: an ArrayList gives O(1) access at a random index, and a
    // HashMap (value -> its index in the list) gives O(1) location for delete.
    // Delete = swap-with-last + remove last, so nothing ever shifts.
    private final List<Integer> values = new ArrayList<>();
    private final Map<Integer, Integer> indexOf = new HashMap<>();
    private final Random rnd = new Random();

    public boolean insert(int val) {
        if (indexOf.containsKey(val)) return false;   // already present
        indexOf.put(val, values.size());              // it will sit at the end
        values.add(val);
        return true;
    }

    public boolean remove(int val) {
        Integer i = indexOf.get(val);
        if (i == null) return false;                  // not present
        int last = values.get(values.size() - 1);
        values.set(i, last);                          // fill the hole with the LAST element
        indexOf.put(last, i);                         // ...and record its new position
        values.remove(values.size() - 1);             // removing the tail is O(1)
        indexOf.remove(val);                          // (works even when val WAS the last)
        return true;
    }

    public int getRandom() {
        // Uniform because the list is dense: every slot holds a live value.
        return values.get(rnd.nextInt(values.size()));
    }

    // ---- test harness ----
    public static void main(String[] args) {
        RandomizedSet s = new RandomizedSet();
        assert s.insert(1);            // {1}
        assert !s.remove(2);           // 2 was never inserted
        assert s.insert(2);            // {1, 2}
        int r = s.getRandom();
        assert r == 1 || r == 2;
        assert s.remove(1);            // {2}
        assert !s.insert(2);           // duplicate insert rejected
        assert s.getRandom() == 2;     // only one element left
        System.out.println("RandomizedSet: OK");
    }
}
