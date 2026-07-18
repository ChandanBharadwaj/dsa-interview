public class ReverseString {

    // The canonical converging pair: swap ends, walk inward, stop at the middle.
    public static void reverseString(char[] s) {
        int left = 0, right = s.length - 1;
        while (left < right) {
            char t = s[left];
            s[left] = s[right];
            s[right] = t;
            left++;             // both pointers move every step —
            right--;            // the loop runs exactly n/2 swaps
        }
    }

    public static void main(String[] args) {
        char[] a = {'h', 'e', 'l', 'l', 'o'};
        reverseString(a);
        assert new String(a).equals("olleh");
        char[] b = {'H'};
        reverseString(b);
        assert new String(b).equals("H");
        System.out.println("ReverseString: OK");
    }
}
