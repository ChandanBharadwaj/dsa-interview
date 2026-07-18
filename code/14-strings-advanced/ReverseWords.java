public class ReverseWords {

    // Walk from the END, carving out each word and appending it — one pass,
    // no split(), handles all the whitespace mess by construction.
    public static String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        int i = s.length() - 1;
        while (i >= 0) {
            while (i >= 0 && s.charAt(i) == ' ') i--;   // skip trailing spaces
            if (i < 0) break;
            int end = i;                                // word ends here
            while (i >= 0 && s.charAt(i) != ' ') i--;   // find its start
            if (sb.length() > 0) sb.append(' ');        // single separator
            sb.append(s, i + 1, end + 1);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        assert reverseWords("the sky is blue").equals("blue is sky the");
        assert reverseWords("  hello world  ").equals("world hello");
        assert reverseWords("a good   example").equals("example good a");
        assert reverseWords("   ").isEmpty();
        System.out.println("ReverseWords: OK");
    }
}
