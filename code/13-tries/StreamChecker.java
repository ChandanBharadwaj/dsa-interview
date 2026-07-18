import java.util.*;

public class StreamChecker {

    // query(c) asks: does some dictionary word END at the current stream tip?
    // Reading the stream BACKWARDS turns "suffix of stream" into "prefix of
    // reversed word" — so build the trie over reversed words and walk the
    // recent stream tail newest-first.
    static class Node {
        Node[] next = new Node[26];
        boolean isWord;
    }

    private final Node root = new Node();
    private final StringBuilder stream = new StringBuilder();
    private int maxLen = 0;                       // longest word: walk cap

    public StreamChecker(String[] words) {
        for (String w : words) {
            maxLen = Math.max(maxLen, w.length());
            Node n = root;
            for (int i = w.length() - 1; i >= 0; i--) {   // insert REVERSED
                int c = w.charAt(i) - 'a';
                if (n.next[c] == null) n.next[c] = new Node();
                n = n.next[c];
            }
            n.isWord = true;
        }
    }

    public boolean query(char letter) {
        stream.append(letter);
        Node n = root;
        // Walk backwards from the tip, at most maxLen steps.
        for (int i = stream.length() - 1;
             i >= 0 && i >= stream.length() - maxLen; i--) {
            n = n.next[stream.charAt(i) - 'a'];
            if (n == null) return false;          // no word has this suffix path
            if (n.isWord) return true;            // a whole (reversed) word matched
        }
        return false;
    }

    public static void main(String[] args) {
        StreamChecker sc = new StreamChecker(new String[]{"cd", "f", "kl"});
        char[] q = "abcdefghijkl".toCharArray();
        boolean[] expect = {false, false, false, true,   // 'd' ends "cd"
                            false, true,                  // 'f' is a word
                            false, false, false, false, false, true};  // 'l' ends "kl"
        for (int i = 0; i < q.length; i++)
            assert sc.query(q[i]) == expect[i];
        System.out.println("StreamChecker: OK");
    }
}
