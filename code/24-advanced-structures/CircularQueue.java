public class CircularQueue {

    // Fixed array + head index + size. Indices wrap with modulo — the same
    // ring ArrayDeque and Kafka partitions use under the hood.
    private final int[] data;
    private int head = 0;               // index of the front element
    private int size = 0;

    public CircularQueue(int k) {
        data = new int[k];
    }

    public boolean enQueue(int value) {
        if (size == data.length) return false;          // full
        data[(head + size) % data.length] = value;      // one past the tail
        size++;
        return true;
    }

    public boolean deQueue() {
        if (size == 0) return false;
        head = (head + 1) % data.length;                // front moves right
        size--;
        return true;
    }

    public int Front() { return size == 0 ? -1 : data[head]; }

    public int Rear() {
        return size == 0 ? -1 : data[(head + size - 1) % data.length];
    }

    public boolean isEmpty() { return size == 0; }
    public boolean isFull() { return size == data.length; }

    public static void main(String[] args) {
        CircularQueue q = new CircularQueue(3);
        assert q.enQueue(1) && q.enQueue(2) && q.enQueue(3);
        assert !q.enQueue(4);           // full
        assert q.Rear() == 3;
        assert q.isFull();
        assert q.deQueue();
        assert q.enQueue(4);            // wraps into the freed slot
        assert q.Rear() == 4 && q.Front() == 2;
        assert q.deQueue() && q.deQueue() && q.deQueue();
        assert q.isEmpty() && q.Front() == -1;
        System.out.println("CircularQueue: OK");
    }
}
