public class MoveZeroes {

    // Same reader/writer pattern as RemoveElement, but instead of dropping
    // the unwanted values we let them settle at the end: compact non-zeros
    // forward, then fill the tail with zeros.
    public static void moveZeroes(int[] nums) {
        int write = 0;
        for (int read = 0; read < nums.length; read++)
            if (nums[read] != 0)
                nums[write++] = nums[read];   // compact non-zeros, order kept
        while (write < nums.length)
            nums[write++] = 0;                // zero out the leftover tail
    }

    public static void main(String[] args) {
        int[] a = {0, 1, 0, 3, 12};
        moveZeroes(a);
        assert java.util.Arrays.equals(a, new int[]{1, 3, 12, 0, 0});
        int[] b = {0};
        moveZeroes(b);
        assert b[0] == 0;
        System.out.println("MoveZeroes: OK");
    }
}
