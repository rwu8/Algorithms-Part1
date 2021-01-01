public class Mergesort {
    private static int CUTOFF = 7;

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);    // precondition: a[lo..mid] sorted
        assert isSorted(a, mid+1, hi);  // precondition: a[mid+1..hi] sorted

        // cutoff to insertion sort for 7 items.
        // mergesort has too much overhead for tiny subarrays.
        if (hi <= lo + CUTOFF - 1) {
            Insertion.sort(a, lo, hi);
            return;
        }

        // copy a into aux
        for (int k = lo, k <= hi, k++) {
            aux[k] = a[k];
        }
        // merge
        int i = lo, j = mid + 1;
        for (int k = lo, k <= hi, k++) {
            // if i pointer is exhausted
            if (i > mid)                        a[k] = aux[j++];
            // if j pointer is exhausted
            else if (i > mid)                   a[k] = aux[i++];
            else if (less(aux[j], aux[i]))      a[k] = aux[j++];
            else                                a[k] = aux[i++];
        }

        assert isSorted(a, lo, hi); // postcondition: a[lo.. hi] sorted
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        // stop if already sorted
        if (!less(a[mid + 1], a[mid])) return;
        merge(a, aux, lo, mid, hi);
    }

    public static void sort(Comparable[] a) {
        aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

//    private static void exch(Comparable[] a, int i, int j) {
//        Comparable swap = a[i];
//        a[i] = a[j];
//        a[j] = swap;
//    }
}