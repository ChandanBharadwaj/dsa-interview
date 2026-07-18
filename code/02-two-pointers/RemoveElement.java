public class RemoveElement {

    // Reader/writer pointers: reader scans everything, writer only advances
    // over elements worth keeping. Order of survivors is preserved.
    public static int removeElement(int[] nums, int val) {
        int write = 0;
        for (int read = 0; read < nums.length; read++)
            if (nums[read] != val)
                nums[write++] = nums[read];   // keep it; writer claims the slot
        return write;                          // new logical length
    }

    public static void main(String[] args) {
        int[] a = {3, 2, 2, 3};
        assert removeElement(a, 3) == 2 && a[0] == 2 && a[1] == 2;
        int[] b = {0, 1, 2, 2, 3, 0, 4, 2};
        assert removeElement(b, 2) == 5;
        System.out.println("RemoveElement: OK");
    }
}
