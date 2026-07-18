import java.util.List;

// LeetCode 648 — Replace Words
// Given dictionary "roots" and a sentence, replace every word with its
// SHORTEST root prefix. A trie of roots finds that shortest root in
// O(length of word): walk the word and stop at the FIRST word-end hit.
public class ReplaceWords {

    private static class Node {
        Node[] children = new Node[26];
        boolean isWord;
    }

    public static String replaceWords(List<String> dictionary, String sentence) {
        // Step 1: build a trie of all roots.
        Node root = new Node();
        for (String w : dictionary) {
            Node cur = root;
            for (int i = 0; i < w.length(); i++) {
                int c = w.charAt(i) - 'a';
                if (cur.children[c] == null) cur.children[c] = new Node();
                cur = cur.children[c];
            }
            cur.isWord = true;
        }

        // Step 2: rewrite each word of the sentence independently.
        StringBuilder out = new StringBuilder();
        for (String word : sentence.split(" ")) {
            if (out.length() > 0) out.append(' ');
            out.append(shortestRoot(root, word));
        }
        return out.toString();
    }

    // Walk the trie along `word`; the FIRST word-end we meet is the
    // shortest root (that's why we check isWord before going deeper).
    private static String shortestRoot(Node root, String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';
            if (cur.children[c] == null) return word; // no root prefixes this word
            cur = cur.children[c];
            if (cur.isWord) return word.substring(0, i + 1); // shortest root found
        }
        return word; // word ran out before any root ended
    }

    public static void main(String[] args) {
        assert replaceWords(List.of("cat", "bat", "rat"),
                "the cattle was rattled by the battery")
                .equals("the cat was rat by the bat");
        assert replaceWords(List.of("a", "b", "c"),
                "aadsfasf absbs bbab cadsfafs")
                .equals("a a b c");
        System.out.println("ReplaceWords: OK");
    }
}
