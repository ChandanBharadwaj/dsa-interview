import java.util.Arrays;

/**
 * LeetCode 443 — String Compression. In-place run-length encoding with
 * read/write pointers. Mirrors pages/14-strings-advanced.html.
 */
public class StringCompression {

    public static int compress(char[] chars) {
        int write = 0;                            // where the next output char goes
        int read = 0;                             // start of the current run
        while (read < chars.length) {
            char c = chars[read];
            int runEnd = read;
            while (runEnd < chars.length && chars[runEnd] == c)
                runEnd++;                         // find the end of this run
            int runLen = runEnd - read;

            chars[write++] = c;                   // every run writes its character
            if (runLen > 1) {                     // runs of 1 write NO count
                // Counts >= 10 are written digit by digit, in order
                for (char d : String.valueOf(runLen).toCharArray())
                    chars[write++] = d;
            }
            read = runEnd;                        // jump to the next run
        }
        return write;                             // compressed length
    }

    public static void main(String[] args) {
        char[] a = {'a', 'a', 'b', 'b', 'c', 'c', 'c'};
        assert compress(a) == 6;
        assert Arrays.equals(Arrays.copyOf(a, 6), new char[]{'a', '2', 'b', '2', 'c', '3'});
        char[] b = {'a'};
        assert compress(b) == 1 && b[0] == 'a';
        char[] c = new char[13];
        c[0] = 'a';
        for (int i = 1; i < 13; i++) c[i] = 'b';
        assert compress(c) == 4;                  // a b 1 2
        assert Arrays.equals(Arrays.copyOf(c, 4), new char[]{'a', 'b', '1', '2'});
        System.out.println("StringCompression: OK");
    }
}
