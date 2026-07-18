import java.util.ArrayList;
import java.util.List;

// LeetCode 763 — Partition Labels. Greedy: a partition must extend to the
// LAST occurrence of every letter inside it.
public class PartitionLabels {

    public static List<Integer> partitionLabels(String s) {
        int[] last = new int[26];                       // last[c] = final index where letter c appears
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;                // later occurrences overwrite earlier ones
        }
        List<Integer> sizes = new ArrayList<>();
        int start = 0, end = 0;                         // current partition is s[start..end]
        for (int i = 0; i < s.length(); i++) {
            // every letter we meet drags the partition's end out to its last occurrence
            end = Math.max(end, last[s.charAt(i) - 'a']);
            if (i == end) {                             // nothing inside points past i -> safe to cut here
                sizes.add(end - start + 1);             // record this partition's length
                start = i + 1;                          // next partition begins after the cut
            }
        }
        return sizes;
    }

    public static void main(String[] args) {
        assert partitionLabels("ababcbacadefegdehijhklij").equals(List.of(9, 7, 8));
        assert partitionLabels("eccbbbbdec").equals(List.of(10));
        assert partitionLabels("a").equals(List.of(1));
        System.out.println("PartitionLabels: OK");
    }
}
