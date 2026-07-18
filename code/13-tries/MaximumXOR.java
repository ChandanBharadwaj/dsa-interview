import java.util.*;

public class MaximumXOR {

    // A trie over BITS (children 0/1, most significant first). For each
    // number, walk the trie preferring the OPPOSITE bit at every level —
    // that greedy builds the best possible XOR partner.
    static class Node {
        Node[] next = new Node[2];
    }

    public static int findMaximumXOR(int[] nums) {
        Node root = new Node();
        for (int x : nums) {                       // insert all numbers
            Node n = root;
            for (int b = 31; b >= 0; b--) {
                int bit = (x >>> b) & 1;
                if (n.next[bit] == null) n.next[bit] = new Node();
                n = n.next[bit];
            }
        }
        int best = 0;
        for (int x : nums) {                       // query each number
            Node n = root;
            int xor = 0;
            for (int b = 31; b >= 0; b--) {
                int bit = (x >>> b) & 1;
                int want = 1 - bit;                // opposite bit maximizes XOR
                if (n.next[want] != null) {
                    xor |= 1 << b;                 // this bit of the XOR is 1
                    n = n.next[want];
                } else {
                    n = n.next[bit];               // forced to match
                }
            }
            best = Math.max(best, xor);
        }
        return best;
    }

    public static void main(String[] args) {
        assert findMaximumXOR(new int[]{3, 10, 5, 25, 2, 8}) == 28;   // 5 ^ 25
        assert findMaximumXOR(new int[]{14, 70, 53, 83, 49, 91, 36, 80, 92, 51, 66, 70}) == 127;
        assert findMaximumXOR(new int[]{0}) == 0;
        Random r = new Random(21);
        for (int t = 0; t < 100; t++) {            // brute-force cross-check
            int[] a = new int[2 + r.nextInt(20)];
            for (int i = 0; i < a.length; i++) a[i] = r.nextInt(1 << 16);
            int brute = 0;
            for (int i = 0; i < a.length; i++)
                for (int j = i; j < a.length; j++)
                    brute = Math.max(brute, a[i] ^ a[j]);
            assert findMaximumXOR(a) == brute;
        }
        System.out.println("MaximumXOR: OK");
    }
}
