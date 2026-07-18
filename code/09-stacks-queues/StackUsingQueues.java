import java.util.*;

public class StackUsingQueues {

    // One queue: after enqueueing x, rotate everything behind it to the
    // front — the queue's head is always the newest element (LIFO order).
    private final Queue<Integer> q = new ArrayDeque<>();

    public void push(int x) {
        q.offer(x);
        for (int i = 0; i < q.size() - 1; i++)   // rotate the older elements
            q.offer(q.poll());                   // around behind x
    }

    public int pop() { return q.poll(); }
    public int top() { return q.peek(); }
    public boolean empty() { return q.isEmpty(); }

    public static void main(String[] args) {
        StackUsingQueues s = new StackUsingQueues();
        s.push(1);
        s.push(2);
        assert s.top() == 2;
        assert s.pop() == 2;
        assert !s.empty();
        s.push(3);
        assert s.pop() == 3 && s.pop() == 1 && s.empty();
        System.out.println("StackUsingQueues: OK");
    }
}
