/**
 * LeetCode 125 — Valid Palindrome. Two variations from the page:
 * clean+reverse O(n) space vs converging pointers O(1) space.
 * Mirrors pages/02-two-pointers.html.
 */
public class ValidPalindrome {

    public static boolean isPalindromeReverse(String s) {
        StringBuilder clean = new StringBuilder();
        for (char c : s.toCharArray())
            if (Character.isLetterOrDigit(c))
                clean.append(Character.toLowerCase(c));   // keep only a-z0-9, lowercased
        return clean.toString().equals(clean.reverse().toString());
    }

    public static boolean isPalindrome(String s) {
        int l = 0, r = s.length() - 1;
        while (l < r) {
            // Skip characters that don't count, from either side
            if (!Character.isLetterOrDigit(s.charAt(l))) { l++; continue; }
            if (!Character.isLetterOrDigit(s.charAt(r))) { r--; continue; }
            // Compare the mirror pair, case-insensitively
            if (Character.toLowerCase(s.charAt(l)) !=
                Character.toLowerCase(s.charAt(r))) return false;
            l++; r--;                        // matched: shrink from both ends
        }
        return true;                         // pointers met -> every pair matched
    }

    public static void main(String[] args) {
        assert isPalindrome("A man, a plan, a canal: Panama");
        assert !isPalindrome("race a car");
        assert isPalindrome(" ");
        assert isPalindromeReverse("No 'x' in Nixon");
        assert !isPalindromeReverse("abca");
        System.out.println("ValidPalindrome: OK");
    }
}
