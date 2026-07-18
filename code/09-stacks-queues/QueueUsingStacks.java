import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 232 — Implement Queue using Stacks. Two stacks (in/out) with
 * lazy transfer: amortized O(1) per operation.
 * Mirrors pages/09-stacks-queues.html.
 */
public class QueueUsingStacks {

    // in  : newly pushed elements (newest on top)
    // out : reversed elements ready to pop in FIFO order (oldest on top)
    private final Deque<Integer> in = new ArrayDeque<>();
    private final Deque<Integer> out = new ArrayDeque<>();

    public void push(int x) { in.push(x); }

    public int pop() {
        shift();
        return out.pop();
    }

    public int peek() {
        shift();
        return out.peek();
    }

    public boolean empty() { return in.isEmpty() && out.isEmpty(); }

    // Only when out runs dry, dump in -> out. Two LIFO reversals = FIFO.
    // Each element makes this trip AT MOST ONCE in its lifetime, which is
    // why every operation is O(1) amortized despite the occasional O(n) dump.
    private void shift() {
        if (out.isEmpty())
            while (!in.isEmpty()) out.push(in.pop());
    }

    public static void main(String[] args) {
        QueueUsingStacks q = new QueueUsingStacks();
        q.push(1); q.push(2);
        assert q.peek() == 1;         // FIFO front
        assert q.pop() == 1;
        assert !q.empty();
        q.push(3);
        assert q.pop() == 2 && q.pop() == 3 && q.empty();
        System.out.println("QueueUsingStacks: OK");
    }
}
