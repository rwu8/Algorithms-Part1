public class LinkedStackOfStrings {
    private Node first = null;

    // inner class
    private class Node {
        String item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public Node pop() {
        // save item to return
        String item = first.item;
        // delete the first node
        first = first.next;
        return item;
    }

    public void push(String str) {
        // save a link to the list
        Node oldfirst = first;
        // create a new node for the beginning
        first = new Node;
        // set the instance variables in the new node
        first.item = str;
        first.next = oldfirst;
    }

    public static void main(String[] args) {
        LinkedStackOfStrings stack = new LinkedStackOfStrings();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if(s.equals("-")) StdOut.print(stack.pop());
            else stack.push(s);
        }
    }
}