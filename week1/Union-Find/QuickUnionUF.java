// initialize: O(N)
// union: O(N)
// find: O(N)
// Quick-union defect: Trees can get tall
// Find too expensive (could be N array accesses)

public class QuickUnionUF {
    private int[] id;

    public QuickUnionUF(int N) {
        id = new int[N];
        // set id of each object to itself
        for (int i=0; i<N; i++) {
            id[i] = i;
        }
    }

    // chase parent pointers until reach root
    // depth of i array accesses
    private int root(int i) {
        while (i != id[i]) i = id[i];
        return i;
    }

    // check whether p and q are in the same component
    // depth of p and q array accesses
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    // change root of p to point to root of q
    // depth of p and q array accesses
    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        id[i] = j;
    }
}