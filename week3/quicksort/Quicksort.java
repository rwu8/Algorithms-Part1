public class Quicksort {
    private final int CUTOFF = 10;
    public static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            // find item on left to swap
            while (less(a[++i], a[lo]) {
                if (i == hi) break;
            }
            // find item on right to swap
            while (less(a[lo], a[--j])) {
                if (j == lo) break;
            }

            // check if pointers cross
            if (i >= j) break;
            exch(a, i, j);
        }
        // swap with partitioning item
        exch(a, lo, j);
        return j; // return index of item now known to be in place
    }

    public static void sort(Comparable[] a) {
        // shuffle needed for performance guarantee
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        // practical improvement for small subarrays
        if (hi <= lo + CUTOFF - 1) {
            Insertion.sort(a, lo, hi);
            return;
        }
        // best choice of pivot item = median
        // estimate true median by taking median of sample
        int m = medianOf3(a, lo, hi + (hi - lo)/2, hi);
        swap(a, lo, m);
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}