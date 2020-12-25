/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int N = Integer.valueOf(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            randomizedQueue.enqueue(s);
        }

        while (N > 0) {
            System.out.println(randomizedQueue.dequeue());
            N--;
        }
    }
}
