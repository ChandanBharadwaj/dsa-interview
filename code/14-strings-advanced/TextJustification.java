import java.util.*;

public class TextJustification {

    // Two phases per line: greedily take words while they fit, then
    // distribute the leftover spaces. Extra spaces go LEFT-first; the last
    // line and single-word lines are left-justified instead.
    public static List<String> fullJustify(String[] words, int maxWidth) {
        List<String> lines = new ArrayList<>();
        int i = 0;
        while (i < words.length) {
            // Phase 1: how many words fit? (each extra word costs len + 1 space)
            int j = i, lineChars = 0;
            while (j < words.length
                    && lineChars + words[j].length() + (j - i) <= maxWidth) {
                lineChars += words[j].length();
                j++;
            }
            int wordCount = j - i;
            int spaces = maxWidth - lineChars;          // spaces to distribute
            StringBuilder line = new StringBuilder();

            if (j == words.length || wordCount == 1) {
                // Last line / lone word: left-justify, pad the right edge.
                for (int k = i; k < j; k++) {
                    line.append(words[k]);
                    if (k < j - 1) line.append(' ');
                }
                while (line.length() < maxWidth) line.append(' ');
            } else {
                // Full justify: gaps = wordCount - 1; left gaps get the
                // remainder, one extra space each.
                int gaps = wordCount - 1;
                int base = spaces / gaps, extra = spaces % gaps;
                for (int k = i; k < j; k++) {
                    line.append(words[k]);
                    if (k < j - 1) {
                        int pad = base + (k - i < extra ? 1 : 0);
                        for (int p = 0; p < pad; p++) line.append(' ');
                    }
                }
            }
            lines.add(line.toString());
            i = j;
        }
        return lines;
    }

    public static void main(String[] args) {
        assert fullJustify(new String[]{"This", "is", "an", "example", "of", "text",
                "justification."}, 16).equals(List.of(
                "This    is    an",
                "example  of text",
                "justification.  "));
        assert fullJustify(new String[]{"What", "must", "be", "acknowledgment",
                "shall", "be"}, 16).equals(List.of(
                "What   must   be",
                "acknowledgment  ",       // lone word: left-justified
                "shall be        "));     // last line: left-justified
        for (String line : fullJustify(new String[]{"a", "b", "c", "d"}, 5))
            assert line.length() == 5;
        System.out.println("TextJustification: OK");
    }
}
