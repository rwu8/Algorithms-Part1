// initialize: O(N)
// union: O(lg(N))
// find: O(lg(N))
// Find: takes time proportional to the depth of p and q
// Union: takes constant time, given roots

public class WeightedQuickUnionUF {
    private int[] id;
    private int[] sz;

    public QuickUnionUF(int N) {
        id = new int[N];
        // set id of each object to itself
        // set size of each object to 1
        for (int i=0; i<N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    // chase parent pointers until reach root
    // also compress path of the tree while calculating the root
    // depth of i array accesses
    private int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    // check whether p and q are in the same component
    // depth of p and q array accesses
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    // change root of the smaller tree to the root of the larger tree
    // and update our sz array accordingly
    // depth of p and q array accesses
    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        if (sz[i] < sz[j]) {
            id[i] = id[j];
            sz[j] += sz[i];
        } else {
            id[j] = id[i];
            sz[i] += sz[j];
        }
    }
}