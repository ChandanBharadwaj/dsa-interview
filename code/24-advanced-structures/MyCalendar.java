import java.util.Map;
import java.util.TreeMap;

public class MyCalendar {

    // start -> end of every booked interval [start, end), kept sorted by start.
    // The red-black tree behind TreeMap makes "nearest interval" an O(log n) question.
    private final TreeMap<Integer, Integer> booked = new TreeMap<>();

    public boolean book(int start, int end) {
        // Nearest booking starting AT OR BEFORE us: it must end before we begin.
        Map.Entry<Integer, Integer> before = booked.floorEntry(start);
        if (before != null && before.getValue() > start) return false;
        // Nearest booking starting AT OR AFTER us: it must start after we end.
        Map.Entry<Integer, Integer> after = booked.ceilingEntry(start);
        if (after != null && after.getKey() < end) return false;
        booked.put(start, end);        // no conflict on either side -> safe to book
        return true;
    }

    // ---- test harness ----
    public static void main(String[] args) {
        MyCalendar cal = new MyCalendar();
        assert cal.book(10, 20);       // empty calendar, always fits
        assert !cal.book(15, 25);      // overlaps [10, 20)
        assert cal.book(20, 30);       // [start, end) is half-open: touching is fine
        assert cal.book(5, 10);        // fits exactly before [10, 20)
        assert !cal.book(4, 6);        // overlaps [5, 10)
        System.out.println("MyCalendar: OK");
    }
}
