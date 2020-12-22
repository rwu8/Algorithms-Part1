public class FixedCapacityStackOfStrings {
    private String[] s;
    private int N = 0;

    public FixedCapacityStackOfStrings(int capacity) {
        s = new String[capacity];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public String pop() {
        // decrement N; then use to index into array
        // avoids "loitering". garbage collector can reclaim memory
        String item = s[--N];
        s[N] == null;
        return item;
    }

    public void push(String item) {
        // use to index into array; then increment N
        s[N++] = item;
    }

    public static void main(String[] args) {
        FixedCapacityStackOfStrings stack = new FixedCapacityStackOfStrings();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if(s.equals("-")) StdOut.print(stack.pop());
            else stack.push(s);
        }
    }
}