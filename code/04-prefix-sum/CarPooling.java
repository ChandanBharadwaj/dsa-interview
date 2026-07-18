// LeetCode 1094. Car Pooling — difference array over the road positions
public class CarPooling {

    public static boolean carPooling(int[][] trips, int capacity) {
        // diff[p] = net change in passengers at kilometer p.
        // Positions are bounded (0..1000 in the constraints), so a plain
        // array beats a TreeMap of events here.
        int[] diff = new int[1001];
        for (int[] t : trips) {
            int passengers = t[0], from = t[1], to = t[2];
            diff[from] += passengers;   // they board at 'from'
            diff[to]   -= passengers;   // and are gone by 'to' (drop-off is exclusive)
        }
        // Prefix-sum the markers: onboard = passengers in the car at position p.
        int onboard = 0;
        for (int p = 0; p < diff.length; p++) {
            onboard += diff[p];
            if (onboard > capacity) return false;   // overfull at this kilometer
        }
        return true;
    }

    public static void main(String[] args) {
        assert !carPooling(new int[][]{{2, 1, 5}, {3, 3, 7}}, 4);   // 5 aboard on km 3..4
        assert carPooling(new int[][]{{2, 1, 5}, {3, 3, 7}}, 5);
        assert carPooling(new int[][]{{2, 1, 5}, {3, 5, 7}}, 3);    // back-to-back trips share seats
        System.out.println("CarPooling: OK");
    }
}
