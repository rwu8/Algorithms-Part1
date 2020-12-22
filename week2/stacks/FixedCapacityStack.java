public class FixedCapacityStack<Item> {
    private Item[] s;
    private int N = 0;

    public FixedCapacityStackOfStrings(int capacity) {
        // generic array creation now allowed in Java
        //        s = new Item[capacity]
        s = (Item[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public Item pop() {
        // decrement N; then use to index into array
        // avoids "loitering". garbage collector can reclaim memory
        Item item = s[--N];
        s[N] == null;
        return item;
    }

    public void push(Item item) {
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