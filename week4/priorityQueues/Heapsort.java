// Heapsort uses 2NlgN compares and exchanges
// in-place sorting algorithm with NlgN compares
// makes poor use of cache memory
// not stable (long distance exchanges)

public class Heap {
    public static void sort(Comparable[] pq) {
        int N = pq.length;
        for (int k = N/2; k>=1; k--) {
            sink(pq, k, N);
        }
        while (N > 1) {
            exch(pq, 1, N);
            sink(pq, 1, --N);
        }
    }

    private static void sink(Comparable[] pq, int k, int N) {
        while (2*k <= N) {
            int j = 2*k;
            if (less(pq,j,j+1)) j++;
            if (!less(pq, k, j)) break;
            exch(pq, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable[] pq, int i, int j) {
        return pq[i-1].compareTo(pq[j-1]) < 0;
    }

    private static void exch(Comparable[] pq, int i, int j) {
        Key item = pq[i-1];
        pq[i-1] = pq[j-1];
        pq[j-1] = item;
    }
}