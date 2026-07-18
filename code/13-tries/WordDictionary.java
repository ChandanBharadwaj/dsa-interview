// LeetCode 211 — Design Add and Search Words Data Structure
// A trie where search supports '.' matching ANY single letter.
// A '.' forces us to try all 26 children — a small DFS branching out.
public class WordDictionary {

    private static class Node {
        Node[] children = new Node[26];
        boolean isWord;
    }

    private final Node root = new Node();

    /** Standard trie insert — identical to Implement Trie. */
    public void addWord(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';
            if (cur.children[c] == null) cur.children[c] = new Node();
            cur = cur.children[c];
        }
        cur.isWord = true;
    }

    /** Search where '.' is a wildcard for exactly one letter. */
    public boolean search(String word) {
        return dfs(word, 0, root);
    }

    // Can word[i..] be matched starting from `node`?
    private boolean dfs(String word, int i, Node node) {
        if (node == null) return false;             // walked off the trie -> fail
        if (i == word.length()) return node.isWord; // all chars consumed: need a word-end HERE
        char ch = word.charAt(i);
        if (ch == '.') {
            // Wildcard: try every existing child. Any success is a match.
            for (Node child : node.children)
                if (dfs(word, i + 1, child)) return true;
            return false; // no branch worked
        }
        // Normal letter: follow exactly one branch (null is caught above).
        return dfs(word, i + 1, node.children[ch - 'a']);
    }

    public static void main(String[] args) {
        WordDictionary d = new WordDictionary();
        d.addWord("bad");
        d.addWord("dad");
        d.addWord("mad");
        assert !d.search("pad"); // no such word
        assert d.search("bad");  // exact match
        assert d.search(".ad");  // '.' matches b, d, or m
        assert d.search("b.."); // b + any two letters -> "bad"
        assert !d.search("b.");  // too short: no word ends after 2 chars
        System.out.println("WordDictionary: OK");
    }
}
