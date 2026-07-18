import java.util.*;

public class SingleNumberIII {

    // TWO singles among pairs. XOR-all gives a^b; any set bit of a^b is a bit
    // where a and b DIFFER — partition all numbers by that bit and XOR each
    // group separately. Pairs stay together; a and b land apart.
    public static int[] singleNumber(int[] nums) {
        long xorAll = 0;                     // long guards the MIN_VALUE edge
        for (int x : nums) xorAll ^= x;

        long diff = xorAll & (-xorAll);      // lowest set bit of a^b
        int a = 0, b = 0;
        for (int x : nums) {
            if ((x & diff) != 0) a ^= x;     // group with the bit set
            else b ^= x;                     // group without it
        }
        return new int[]{a, b};
    }

    public static void main(String[] args) {
        int[] r = singleNumber(new int[]{1, 2, 1, 3, 2, 5});
        Arrays.sort(r);
        assert Arrays.equals(r, new int[]{3, 5});
        r = singleNumber(new int[]{-1, 0});
        Arrays.sort(r);
        assert Arrays.equals(r, new int[]{-1, 0});
        r = singleNumber(new int[]{1, 1, 0, -2147483648});
        Arrays.sort(r);
        assert Arrays.equals(r, new int[]{-2147483648, 0});
        System.out.println("SingleNumberIII: OK");
    }
}
