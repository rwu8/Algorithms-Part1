public class FixedCapacityQueueOfStrings {
    private String[] q;
    private int N = 0;
    private int head, tail = 0;

    public FixedCapacityStackOfStrings(int capacity) {
        q = new String[capacity];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public String dequeue() {
        // decrement N; then use to index into array
        // avoids "loitering". garbage collector can reclaim memory
        String item = q[head];
        N--;
        q[head] == null;
        head++;
        return item;
    }

    public void enqueue(String item) {
        // use to index into array; then increment N
        if (isEmpty()) {
            q[head] = item;
            last++;
        } else q[++last] = item;
    }

    public static void main(String[] args) {
        FixedCapacityQueueOfStrings queue = new FixedCapacityQueueOfStrings();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if(s.equals("-")) StdOut.print(queue.dequeue());
            else stack.enqueue(s);
        }
    }
}