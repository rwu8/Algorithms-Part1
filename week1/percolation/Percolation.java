/* *****************************************************************************
 *  Name:              Ray Wu
 *  Coursera User ID:  123456
 *  Last modified:     12/7/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] sites;
    private final WeightedQuickUnionUF uf;
    private int openSites;
    private final int length_;
    private final int virtualTop;
    private final int virtualBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n cannot be less than or equal to 0!");
        this.length_ = n;
        this.openSites = 0;
        // n-by-n grid of sites with 2 spots for a virtual top and bottom site.
        this.sites = new boolean[n][n];
        this.uf = new WeightedQuickUnionUF((n * n) + 2);
        this.virtualTop = n * n;
        this.virtualBottom = (n * n) + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateSiteIndex(row, col);
        // site is already open
        if (isOpen(row, col)) {
            return;
        }
        // else open the site
        int rowIndex = row - 1;
        int colIndex = col - 1;
        sites[rowIndex][colIndex] = true;
        this.openSites++;
        int currentSiteIndex = getSiteIndex(row, col) - 1;
        if (rowIndex == 0) {
            // union virtual top
            this.uf.union(currentSiteIndex, virtualTop);
        } else if (rowIndex == length_ - 1) {
            // union virtual bottom
            this.uf.union(currentSiteIndex, virtualBottom);
        }

        // check the 4 sites surrounding the row, col
        // top
        if (rowIndex - 1 >= 0 && isOpen(row - 1, col)) {
            this.uf.union(currentSiteIndex, getSiteIndex(row - 1, col) - 1);
        }
        // bottom
        if (rowIndex + 1 < length_ && isOpen(row + 1, col)) {
            this.uf.union(currentSiteIndex, getSiteIndex(row + 1, col) - 1);
        }

        // left
        if (colIndex - 1 >= 0 && isOpen(row, col - 1)) {
            this.uf.union(currentSiteIndex, getSiteIndex(row, col - 1) - 1);
        }

        // right
        if (colIndex + 1 < length_ && isOpen(row, col + 1)) {
            this.uf.union(currentSiteIndex, getSiteIndex(row, col + 1) - 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateSiteIndex(row, col);
        return this.sites[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateSiteIndex(row, col);
        //  A full site is an open site that can be connected to an open site
        //  in the top row via a chain of neighboring (left, right, up, down) open sites.
        return isOpen(row, col) && this.uf.find(virtualTop) == this.uf.find(getSiteIndex(row, col) - 1);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        // the system percolates if there is a full site in the bottom row
        return this.uf.find(virtualTop) == this.uf.find(virtualBottom);
    }

    private int getSiteIndex(int row, int col) {
        return length_ * (row - 1) + col;
    }

    private void validateSiteIndex(int row, int col) {
        if (row < 1 || row > length_ || col < 1 || col > length_) {
            throw new IllegalArgumentException("You must enter a valid row and col!");
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);

        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(2, 1);
        System.out.println(percolation.numberOfOpenSites());
        System.out.println(percolation.isFull(1,1 ));
        // int n = StdIn.readInt();
        // StdOut.println(n);
        // Percolation perc = new Percolation(n);
        //
        // while (!StdIn.isEmpty()) {
        //     int p = StdIn.readInt();
        //     int q = StdIn.readInt();
        //
        //     if (perc.uf.find(q) != perc.uf.find(p)) {
        //         perc.uf.union(p, q);
        //         StdOut.println(p + " " + q);
        //     }
        // }
    }
}