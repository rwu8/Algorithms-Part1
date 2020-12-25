import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first, last;

    // inner class
    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("This item cannot be added to the Deque!");
        }
        Node newNode = new Node();
        newNode.item = item;

        if (isEmpty()) {
            this.first = newNode;
            this.last = newNode;
        } else {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("This item cannot be added to the Deque!");
        }
        Node newNode = new Node();
        newNode.item = item;
        if (isEmpty()) {
            this.first = newNode;
            this.last = newNode;
        } else {
            this.last.next = newNode;
            newNode.prev = this.last;
            this.last = newNode;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The deque is already empty!");
        }
        size--;
        Node oldFirst = this.first;

        if (isEmpty()) {
            this.first = null;
            this.last = null;
        } else {
            this.first = this.first.next;
            this.first.prev = null;
        }

        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The deque is already empty!");
        }
        size--;
        Node oldLast = this.last;

        if (isEmpty()) {
            this.first = null;
            this.last = null;
        } else {
            this.last = this.last.prev;
            this.last.next = null;
        }

        return oldLast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() { return current != null; }
        public void remove() {
            //    not supported!
            throw new UnsupportedOperationException("Remove is not supported!");
        }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("There are no more items to return.");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        System.out.println(deque.isEmpty());
        deque.addFirst("first");
        System.out.println(deque.first.item);
        deque.addFirst("second");
        System.out.println(deque.first.item);
        deque.addLast("third");
        System.out.println(deque.last.item);
        System.out.println(deque.size());
        System.out.println("----------");
        System.out.println("testing iterator:");
        for (String item : deque) {
            System.out.println(item);
        }
        System.out.println("----------");
        deque.removeFirst();
        System.out.println(deque.first.item);
        deque.removeLast();
        System.out.println(deque.last.item);
    }

}