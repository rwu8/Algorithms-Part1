public class MergeBU {
    public static Comparable[] aux;

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);    // precondition: a[lo..mid] sorted
        assert isSorted(a, mid+1, hi);  // precondition: a[mid+1..hi] sorted

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

    public static void sort(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];
        // first for loop executes lgN times
        for (int sz = 1; sz < N; sz = sz+sz) {
            for (int lo = 0; lo < N-sz; lo += sz+sz) {
                merge(a, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
            }
        }
    }
}