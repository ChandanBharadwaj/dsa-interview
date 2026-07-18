import java.util.*;

public class WordLadder {

    // Words are nodes; an edge = one-letter difference. Shortest chain =
    // BFS. Generating the 25·L neighbors per word beats comparing all pairs.
    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) return 0;

        Queue<String> q = new ArrayDeque<>();
        q.offer(beginWord);
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        int steps = 1;                              // counts words in the chain
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                String word = q.poll();
                if (word.equals(endWord)) return steps;
                char[] chars = word.toCharArray();
                for (int pos = 0; pos < chars.length; pos++) {
                    char original = chars[pos];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue;
                        chars[pos] = c;             // mutate one letter
                        String next = new String(chars);
                        if (dict.contains(next) && visited.add(next))
                            q.offer(next);
                    }
                    chars[pos] = original;          // restore before next position
                }
            }
            steps++;
        }
        return 0;
    }

    public static void main(String[] args) {
        assert ladderLength("hit", "cog",
                Arrays.asList("hot", "dot", "dog", "lot", "log", "cog")) == 5;
        assert ladderLength("hit", "cog",
                Arrays.asList("hot", "dot", "dog", "lot", "log")) == 0;
        assert ladderLength("a", "c", Arrays.asList("a", "b", "c")) == 2;
        System.out.println("WordLadder: OK");
    }
}
