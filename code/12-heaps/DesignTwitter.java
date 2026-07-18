import java.util.*;

public class DesignTwitter {

    // Feeds are per-user posted lists; a news feed merges the 10 newest
    // across the user + followees — K-way merge by timestamp with a heap.
    private int clock = 0;                                    // global timestamp
    private final Map<Integer, List<int[]>> tweets = new HashMap<>();   // {time, id}
    private final Map<Integer, Set<Integer>> follows = new HashMap<>();

    public void postTweet(int userId, int tweetId) {
        tweets.computeIfAbsent(userId, k -> new ArrayList<>())
              .add(new int[]{clock++, tweetId});
    }

    public List<Integer> getNewsFeed(int userId) {
        // Max-heap of candidate tweets (newest first) from self + followees.
        PriorityQueue<int[]> heap =            // {time, tweetId, ownerId, index}
                new PriorityQueue<>((a, b) -> b[0] - a[0]);
        Set<Integer> sources = new HashSet<>(follows.getOrDefault(userId, Set.of()));
        sources.add(userId);                   // your own tweets count
        for (int u : sources) {
            List<int[]> list = tweets.get(u);
            if (list != null && !list.isEmpty()) {
                int i = list.size() - 1;       // start at u's newest
                int[] t = list.get(i);
                heap.offer(new int[]{t[0], t[1], u, i});
            }
        }
        List<Integer> feed = new ArrayList<>();
        while (!heap.isEmpty() && feed.size() < 10) {
            int[] top = heap.poll();           // globally newest remaining
            feed.add(top[1]);
            if (top[3] > 0) {                  // that user's next-newest slides in
                int[] t = tweets.get(top[2]).get(top[3] - 1);
                heap.offer(new int[]{t[0], t[1], top[2], top[3] - 1});
            }
        }
        return feed;
    }

    public void follow(int followerId, int followeeId) {
        if (followerId != followeeId)
            follows.computeIfAbsent(followerId, k -> new HashSet<>()).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        Set<Integer> f = follows.get(followerId);
        if (f != null) f.remove(followeeId);
    }

    public static void main(String[] args) {
        DesignTwitter t = new DesignTwitter();
        t.postTweet(1, 5);
        assert t.getNewsFeed(1).equals(List.of(5));
        t.follow(1, 2);
        t.postTweet(2, 6);
        assert t.getNewsFeed(1).equals(List.of(6, 5));   // newest first
        t.unfollow(1, 2);
        assert t.getNewsFeed(1).equals(List.of(5));
        for (int i = 0; i < 12; i++) t.postTweet(3, 100 + i);
        assert t.getNewsFeed(3).size() == 10;            // capped at 10
        assert t.getNewsFeed(3).get(0) == 111;           // newest of user 3
        System.out.println("DesignTwitter: OK");
    }
}
