import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] randomQueue;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.randomQueue = (Item[]) new Object[10];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.size;
    }

    // add the item
    public void enqueue(Item item) {
        if(item == null) throw new NullPointerException();
        if (size() == randomQueue.length) resize(2 * size());

        this.randomQueue[size()] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        int random = StdRandom.uniform(size());
        // swap out last item in array with the item to dequeue
        Item item = randomQueue[random];
        randomQueue[random] = randomQueue[size() - 1];
        randomQueue[size() - 1] = null;
        // randomQueue[random] = null;
        size--;
        if (size() > 0 && size() == randomQueue.length / 4) resize (randomQueue.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int random = StdRandom.uniform(size());
        return randomQueue[random];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = randomQueue[i];
        }
        randomQueue = copy;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private Item[] copy;
        private int count;

        public RandomizedIterator() {
            this.copy = (Item[]) new Object[size];
            count = size;
            for (int i = 0; i < size; i++) {
                copy[i] = randomQueue[i];
            }
        }

        public boolean hasNext() { return count != 0; }
        public void remove() {
            //    not supported!
            throw new UnsupportedOperationException("Remove is not supported!");
        }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("There are no more items to return.");
            }
            // int random = StdRandom.uniform(size());
            // while (copy[random] == null) {
            //     random = StdRandom.uniform(size());
            // }
            int random = StdRandom.uniform(count);
            // swap item with the item at the end of the array
            Item item = copy[random];
            copy[random] = copy[count - 1];
            copy[count - 1] = item;
            count--;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        System.out.println(randomizedQueue.isEmpty());
        randomizedQueue.enqueue("first");
        randomizedQueue.enqueue("second");
        randomizedQueue.enqueue("third");
        randomizedQueue.enqueue("fourth");
        randomizedQueue.enqueue("fifth");
        System.out.println(randomizedQueue.size());
        System.out.println(randomizedQueue.sample());
        System.out.println(randomizedQueue.sample());
        System.out.println(randomizedQueue.size());
        System.out.println(randomizedQueue.isEmpty());
        System.out.println("----------");
        System.out.println("testing iterator:");
        for (String item : randomizedQueue) {
            System.out.println(item);
        }
        System.out.println("----------");
        System.out.println("testing iterator:");
        for (String item : randomizedQueue) {
            System.out.println(item);
        }
        System.out.println("----------");
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.dequeue());
        System.out.println("----------");
        System.out.println("testing iterator:");
        for (String item : randomizedQueue) {
            System.out.println(item);
        }
        System.out.println("----------");
    }
}