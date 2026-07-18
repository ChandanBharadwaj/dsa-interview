// LeetCode 208 — Implement Trie (Prefix Tree)
// Supports insert, exact-word search, and prefix search, all in O(L)
// where L is the length of the word/prefix — independent of how many
// words are stored.
public class Trie {

    // One node per character position. children[c - 'a'] is the branch
    // for letter c; null means "no stored word goes that way".
    private static class Node {
        Node[] children = new Node[26]; // lowercase a-z only
        boolean isWord;                 // does a stored word END here?
    }

    private final Node root = new Node(); // root = the empty prefix ""

    /** Insert a word: walk letter by letter, creating nodes as needed. */
    public void insert(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';      // map 'a'..'z' -> 0..25
            if (cur.children[c] == null)       // no branch yet for this letter
                cur.children[c] = new Node();  // create it (the only allocation)
            cur = cur.children[c];             // descend one level
        }
        cur.isWord = true; // mark the node where the word ends
    }

    /** True only if the exact word was inserted (not just a prefix of one). */
    public boolean search(String word) {
        Node end = walk(word);
        return end != null && end.isWord; // must exist AND be a word-end
    }

    /** True if ANY inserted word starts with this prefix. */
    public boolean startsWith(String prefix) {
        return walk(prefix) != null; // reaching a node is enough — no flag check
    }

    // Shared walk: follow the letters of s down from the root.
    // Returns the node where s ends, or null if the path breaks.
    private Node walk(String s) {
        Node cur = root;
        for (int i = 0; i < s.length(); i++) {
            cur = cur.children[s.charAt(i) - 'a'];
            if (cur == null) return null; // path does not exist
        }
        return cur;
    }

    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("apple");
        assert t.search("apple");    // exact word -> true
        assert !t.search("app");     // "app" is only a prefix so far
        assert t.startsWith("app");  // but it IS a valid prefix
        t.insert("app");
        assert t.search("app");      // now it's a stored word too
        assert !t.startsWith("apx"); // no word goes a-p-x
        System.out.println("Trie: OK");
    }
}
