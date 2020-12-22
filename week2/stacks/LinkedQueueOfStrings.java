public class LinkedQueueOfStrings {
    private Node first, last;

    // inner class
    private class Node {
        String item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public Node dequeue() {
        // save item to return
        String item = first.item;
        // delete the first node
        first = first.next;
        if (isEmpty()) last = null;
        return item;
    }

    public void enqueue(String str) {
        // save a link to the list
        Node oldlast = last;
        // create a new node for the end
        Node last = new Node;
        // set the instance variables in the new node
        last.item = str;
        last.next = null;
        if (isEmpty()) first = last;
        else oldlast.next = last;
    }

    public static void main(String[] args) {
        LinkedQueueOfStrings queue = new LinkedQueueOfStrings();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if(s.equals("-")) StdOut.print(queue.dequeue());
            else queue.enqueue(s);
        }
    }
}