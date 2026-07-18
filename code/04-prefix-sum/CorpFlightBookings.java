public class CorpFlightBookings {

    // Difference array: each booking touches only its two endpoints; one
    // prefix-sum pass at the end materializes every flight's total.
    public static int[] corpFlightBookings(int[][] bookings, int n) {
        int[] diff = new int[n + 1];             // +1 slot so `last+1` never overflows
        for (int[] b : bookings) {
            int first = b[0], last = b[1], seats = b[2];
            diff[first - 1] += seats;            // seats start here (1-indexed input)
            diff[last] -= seats;                 // and stop after `last`
        }
        int[] out = new int[n];
        int running = 0;
        for (int i = 0; i < n; i++) {
            running += diff[i];                  // prefix sum = actual seat count
            out[i] = running;
        }
        return out;
    }

    public static void main(String[] args) {
        assert java.util.Arrays.equals(
                corpFlightBookings(new int[][]{{1, 2, 10}, {2, 3, 20}, {2, 5, 25}}, 5),
                new int[]{10, 55, 45, 25, 25});
        assert java.util.Arrays.equals(
                corpFlightBookings(new int[][]{{1, 2, 10}, {2, 2, 15}}, 2),
                new int[]{10, 25});
        System.out.println("CorpFlightBookings: OK");
    }
}
