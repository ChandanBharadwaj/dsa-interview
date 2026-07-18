import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 212 — Word Search II. One grid DFS carrying a trie node:
 * all words searched simultaneously, dead prefixes pruned wholesale.
 * Mirrors pages/13-tries.html.
 */
public class WordSearchII {

    private static class Node {
        Node[] children = new Node[26];
        String word;                        // non-null = a complete word ends here
    }

    public static List<String> findWords(char[][] board, String[] words) {
        // Build ONE trie of all target words — the shared prefix structure
        // is what lets a single DFS search for every word simultaneously.
        Node root = new Node();
        for (String w : words) {
            Node cur = root;
            for (char ch : w.toCharArray()) {
                int c = ch - 'a';
                if (cur.children[c] == null) cur.children[c] = new Node();
                cur = cur.children[c];
            }
            cur.word = w;                   // store the word AT its end node
        }

        List<String> res = new ArrayList<>();
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[0].length; c++)
                dfs(board, r, c, root, res);
        return res;
    }

    private static void dfs(char[][] b, int r, int c, Node node, List<String> res) {
        if (r < 0 || r >= b.length || c < 0 || c >= b[0].length) return;
        char ch = b[r][c];
        if (ch == '#') return;              // visited on this path
        Node next = node.children[ch - 'a'];
        if (next == null) return;           // NO word continues this way — prune the
                                            // whole subtree of paths right here
        if (next.word != null) {
            res.add(next.word);             // found a complete word
            next.word = null;               // de-duplicate: report each word once
        }
        b[r][c] = '#';                      // mark visited
        dfs(b, r + 1, c, next, res);
        dfs(b, r - 1, c, next, res);
        dfs(b, r, c + 1, next, res);
        dfs(b, r, c - 1, next, res);
        b[r][c] = ch;                       // restore on backtrack
    }

    public static void main(String[] args) {
        char[][] board = {
            {'o', 'a', 'a', 'n'},
            {'e', 't', 'a', 'e'},
            {'i', 'h', 'k', 'r'},
            {'i', 'f', 'l', 'v'}
        };
        List<String> found = findWords(board, new String[]{"oath", "pea", "eat", "rain"});
        assert found.size() == 2 && found.contains("eat") && found.contains("oath");
        assert board[0][0] == 'o';           // board restored
        assert findWords(new char[][]{{'a'}}, new String[]{"b"}).isEmpty();
        System.out.println("WordSearchII: OK");
    }
}
