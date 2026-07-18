import java.util.*;

public class MedianFinder {

    // LeetCode 295. Support addNum(int) and findMedian() on a growing stream.
    //
    // TWO-HEAP IDEA: split the numbers into halves around the median.
    //   lo = a MAX-heap of the smaller half (its root = biggest small number)
    //   hi = a MIN-heap of the larger half  (its root = smallest big number)
    // The median only ever involves the two roots — both O(1) peeks.
    private final PriorityQueue<Integer> lo =
            new PriorityQueue<>(Comparator.reverseOrder()); // max-heap
    private final PriorityQueue<Integer> hi =
            new PriorityQueue<>();                          // min-heap (default)

    public void addNum(int num) {
        // Route: anything <= biggest-of-the-small-half belongs in lo.
        if (lo.isEmpty() || num <= lo.peek()) lo.offer(num);
        else hi.offer(num);
        // Rebalance so sizes differ by at most 1 (lo may hold the extra).
        // Moving one root across the boundary keeps both halves valid.
        if (lo.size() > hi.size() + 1) hi.offer(lo.poll());
        else if (hi.size() > lo.size()) lo.offer(hi.poll());
    }

    public double findMedian() {
        // Odd count: the extra element sits on top of lo.
        if (lo.size() > hi.size()) return lo.peek();
        // Even count: median = average of the two middle values (the roots).
        return (lo.peek() + hi.peek()) / 2.0;
    }

    public static void main(String[] args) {
        MedianFinder mf = new MedianFinder();
        mf.addNum(1);
        mf.addNum(2);
        assert mf.findMedian() == 1.5;    // [1,2] -> 1.5
        mf.addNum(3);
        assert mf.findMedian() == 2.0;    // [1,2,3] -> 2
        // Descending inserts still balance correctly
        MedianFinder mf2 = new MedianFinder();
        for (int x : new int[]{5, 4, 3, 2, 1}) mf2.addNum(x);
        assert mf2.findMedian() == 3.0;
        mf2.addNum(0);
        assert mf2.findMedian() == 2.5;   // [0,1,2,3,4,5] -> (2+3)/2
        System.out.println("MedianFinder: OK");
    }
}
