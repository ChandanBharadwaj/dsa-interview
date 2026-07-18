/**
 * LeetCode 1071 — Greatest Common Divisor of Strings. Concatenation
 * commutes iff a common unit exists; then the answer has length
 * gcd(|s1|, |s2|). Mirrors pages/23-math-geometry.html.
 */
public class GcdOfStrings {

    public static String gcdOfStrings(String str1, String str2) {
        // Divisibility test in string-land: if a common repeating unit exists,
        // then concatenation commutes — str1+str2 must equal str2+str1.
        if (!(str1 + str2).equals(str2 + str1)) return "";
        // When the test passes, the answer's length is exactly gcd(|s1|, |s2|).
        int g = gcd(str1.length(), str2.length());
        return str1.substring(0, g);
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);   // when b hits 0, a holds the answer
    }

    public static void main(String[] args) {
        assert gcdOfStrings("ABCABC", "ABC").equals("ABC");
        assert gcdOfStrings("ABABAB", "ABAB").equals("AB");
        assert gcdOfStrings("LEET", "CODE").isEmpty();
        assert gcdOfStrings("AAAAAA", "AAA").equals("AAA");
        System.out.println("GcdOfStrings: OK");
    }
}
