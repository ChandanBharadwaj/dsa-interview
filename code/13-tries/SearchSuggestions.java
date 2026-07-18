import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 1268 — Search Suggestions System. Two variations: sorted-array
 * shrinking window, and a trie with top-3 completions cached per node.
 * Mirrors pages/13-tries.html.
 */
public class SearchSuggestions {

    // Variation 1: sort once, then shrink a [lo..hi] window per keystroke.
    public static List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Arrays.sort(products);                     // lexicographic order once
        List<List<String>> res = new ArrayList<>();
        int lo = 0, hi = products.length - 1;      // window of products still matching
        for (int i = 0; i < searchWord.length(); i++) {
            char c = searchWord.charAt(i);
            // Shrink from both ends: survivors must have character c at position i
            while (lo <= hi && (products[lo].length() <= i || products[lo].charAt(i) != c)) lo++;
            while (lo <= hi && (products[hi].length() <= i || products[hi].charAt(i) != c)) hi--;
            List<String> top3 = new ArrayList<>();
            for (int k = lo; k <= Math.min(hi, lo + 2); k++) top3.add(products[k]);
            res.add(top3);
        }
        return res;
    }

    // Variation 2: trie whose nodes cache their 3 smallest completions.
    private static class Node {
        Node[] children = new Node[26];
        List<String> top3 = new ArrayList<>(3);   // filled during sorted insert
    }

    public static List<List<String>> suggestedProductsTrie(String[] products, String searchWord) {
        Arrays.sort(products);                    // sorted insert => each node's
        Node root = new Node();                   // top3 fills in lexicographic order
        for (String p : products) {
            Node cur = root;
            for (char ch : p.toCharArray()) {
                int c = ch - 'a';
                if (cur.children[c] == null) cur.children[c] = new Node();
                cur = cur.children[c];
                if (cur.top3.size() < 3) cur.top3.add(p);  // cap the cache at 3
            }
        }
        List<List<String>> res = new ArrayList<>();
        Node cur = root;
        for (int i = 0; i < searchWord.length(); i++) {
            cur = (cur == null) ? null : cur.children[searchWord.charAt(i) - 'a'];
            res.add(cur == null ? List.of() : List.copyOf(cur.top3));  // O(1) per keystroke
        }
        return res;
    }

    public static void main(String[] args) {
        String[] products = {"mobile", "mouse", "moneypot", "monitor", "mousepad"};
        List<List<String>> expect = List.of(
            List.of("mobile", "moneypot", "monitor"),
            List.of("mobile", "moneypot", "monitor"),
            List.of("mouse", "mousepad"),
            List.of("mouse", "mousepad"),
            List.of("mouse", "mousepad"));
        assert suggestedProducts(products.clone(), "mouse").equals(expect);
        assert suggestedProductsTrie(products.clone(), "mouse").equals(expect);
        System.out.println("SearchSuggestions: OK");
    }
}
