public class PalindromeNumber {

    // Reverse only HALF the digits and compare — no overflow possible,
    // no string conversion.
    public static boolean isPalindrome(int x) {
        // negatives never qualify; a trailing 0 needs a leading 0 (only 0 has one)
        if (x < 0 || (x % 10 == 0 && x != 0)) return false;
        int reversedHalf = 0;
        while (x > reversedHalf) {              // stop at the middle
            reversedHalf = reversedHalf * 10 + x % 10;
            x /= 10;
        }
        // even length: halves equal; odd: drop the reversed middle digit
        return x == reversedHalf || x == reversedHalf / 10;
    }

    public static void main(String[] args) {
        assert isPalindrome(121);
        assert !isPalindrome(-121);
        assert !isPalindrome(10);
        assert isPalindrome(0);
        assert isPalindrome(12321);
        assert isPalindrome(1221);
        assert !isPalindrome(123);
        System.out.println("PalindromeNumber: OK");
    }
}
