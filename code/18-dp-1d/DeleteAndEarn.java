import java.util.*;

public class DeleteAndEarn {

    // Taking any copy of value v earns ALL copies of v but deletes every
    // v-1 and v+1 — so per distinct value it's all-or-nothing, and adjacent
    // values conflict. That IS House Robber over the value axis.
    public static int deleteAndEarn(int[] nums) {
        int maxVal = 0;
        for (int x : nums) maxVal = Math.max(maxVal, x);
        long[] gain = new long[maxVal + 1];      // gain[v] = v * count(v)
        for (int x : nums) gain[x] += x;

        long skip = 0, take = gain[0];           // rob/skip down the value line
        for (int v = 1; v <= maxVal; v++) {
            long newTake = skip + gain[v];       // take v -> v-1 must be skipped
            skip = Math.max(skip, take);         // skip v -> best of either at v-1
            take = newTake;
        }
        return (int) Math.max(skip, take);
    }

    public static void main(String[] args) {
        assert deleteAndEarn(new int[]{3, 4, 2}) == 6;          // take 4, then 2
        assert deleteAndEarn(new int[]{2, 2, 3, 3, 3, 4}) == 9; // all 3s
        assert deleteAndEarn(new int[]{5}) == 5;
        assert deleteAndEarn(new int[]{1, 1, 1, 8, 9}) == 12;   // 1s + 9
        System.out.println("DeleteAndEarn: OK");
    }
}
