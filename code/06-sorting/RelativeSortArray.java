import java.util.*;

public class RelativeSortArray {

    // Values are bounded (0..1000) -> counting sort, emitting arr2's order
    // first and the leftovers in ascending order.
    public static int[] relativeSortArray(int[] arr1, int[] arr2) {
        int[] count = new int[1001];
        for (int x : arr1) count[x]++;

        int[] out = new int[arr1.length];
        int w = 0;
        for (int v : arr2)                  // arr2's order, with multiplicity
            while (count[v]-- > 0)
                out[w++] = v;
        for (int v = 0; v <= 1000; v++)     // leftovers: ascending for free,
            while (count[v]-- > 0)          // because we scan values in order
                out[w++] = v;
        return out;
    }

    public static void main(String[] args) {
        assert Arrays.equals(
                relativeSortArray(new int[]{2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19},
                                  new int[]{2, 1, 4, 3, 9, 6}),
                new int[]{2, 2, 2, 1, 4, 3, 3, 9, 6, 7, 19});
        assert Arrays.equals(
                relativeSortArray(new int[]{28, 6, 22, 8, 44, 17}, new int[]{22, 28, 8, 6}),
                new int[]{22, 28, 8, 6, 17, 44});
        System.out.println("RelativeSortArray: OK");
    }
}
