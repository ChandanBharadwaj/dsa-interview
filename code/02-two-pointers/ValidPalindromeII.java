public class ValidPalindromeII {

    // Converge as usual; at the FIRST mismatch we get one free deletion —
    // try skipping either character and demand the rest be a clean palindrome.
    public static boolean validPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right))
                // one deletion allowed: drop left OR drop right
                return isPalindrome(s, left + 1, right)
                    || isPalindrome(s, left, right - 1);
            left++;
            right--;
        }
        return true;                    // no mismatch at all
    }

    private static boolean isPalindrome(String s, int i, int j) {
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }

    public static void main(String[] args) {
        assert validPalindrome("aba");
        assert validPalindrome("abca");     // delete 'c'
        assert !validPalindrome("abc");
        assert validPalindrome("deeee");    // delete 'd'
        System.out.println("ValidPalindromeII: OK");
    }
}
