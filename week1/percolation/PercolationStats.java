/* *****************************************************************************
 *  Name:              Ray Wu
 *  Coursera User ID:  123456
 *  Last modified:     12/7/2020
 **************************************************************************** */
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int trials;
    private final double[] results;
    private final double mean;
    private final double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        this.results = new double[trials];

        // run Monte Carlo simulation
        for (int trial = 0; trial < trials; trial++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                perc.open(row, col);
            }

            // append result for the trial after the system percolates
            results[trial] = (double) perc.numberOfOpenSites() / (n * n);
        }

        // calculate our mean and std dev after running simulations
        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(trials));
    }

   // test client (see below)
   public static void main(String[] args) {
       int n = Integer.parseInt(args[0]);
       int trials = Integer.parseInt(args[1]);
       PercolationStats newPercStats = new PercolationStats(n, trials);
       StdOut.println("mean                    = " + newPercStats.mean());
       StdOut.println("stddev                  = " + newPercStats.stddev());
       StdOut.println("95% confidence interval = ["
           + newPercStats.confidenceLo() + ", "
           + newPercStats.confidenceHi() + "]");
   }

}