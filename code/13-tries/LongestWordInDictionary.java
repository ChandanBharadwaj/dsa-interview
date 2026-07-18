import java.util.*;

public class LongestWordInDictionary {

    // The answer must be buildable one letter at a time, every prefix being
    // a word. Insert all words, then DFS the trie walking ONLY through
    // end-of-word nodes.
    static class Node {
        Node[] next = new Node[26];
        String word;                        // non-null marks end-of-word
    }

    public static String longestWord(String[] words) {
        Node root = new Node();
        for (String w : words) {
            Node n = root;
            for (char c : w.toCharArray()) {
                int i = c - 'a';
                if (n.next[i] == null) n.next[i] = new Node();
                n = n.next[i];
            }
            n.word = w;
        }
        return dfs(root, "");
    }

    private static String dfs(Node n, String best) {
        for (int i = 0; i < 26; i++) {
            Node child = n.next[i];
            if (child == null || child.word == null)
                continue;                   // not a word here -> chain breaks
            // longer wins; equal length -> lexicographically smaller wins,
            // which alphabetical child order (i ascending) gives us free —
            // but compare anyway for clarity
            if (child.word.length() > best.length())
                best = child.word;
            best = dfs(child, best);
        }
        return best;
    }

    public static void main(String[] args) {
        assert longestWord(new String[]{"w", "wo", "wor", "worl", "world"}).equals("world");
        assert longestWord(new String[]{"a", "banana", "app", "appl", "ap", "apply", "apple"})
                .equals("apple");           // apple beats apply lexicographically
        assert longestWord(new String[]{"banana"}).isEmpty();   // no 1-letter start
        System.out.println("LongestWordInDictionary: OK");
    }
}
