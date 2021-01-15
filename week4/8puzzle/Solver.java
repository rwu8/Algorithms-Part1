import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private SearchNode curr;
    private SearchNode twin;
    private Stack<Board> solution;

    // We define a search node of the game to be:
    // a board, the number of moves made to reach the board,
    // and the previous search node.
    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private SearchNode prev;
        private int priority;

        public SearchNode(Board board, SearchNode prev) {
            this.board = board;
            this.prev = prev;
            if (prev == null) {
                this.moves = 0;
            } else {
                this.moves = prev.moves + 1;
            }
            this.priority = moves + board.manhattan();
        }

        public int compareTo(SearchNode searchNode) {
            return Integer.compare(this.priority, searchNode.priority);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        curr = new SearchNode(initial, null);
        twin = new SearchNode(initial.twin(), null);
        MinPQ<SearchNode> minPQ = new MinPQ<>();
        MinPQ<SearchNode> twinPq = new MinPQ<>();
        minPQ.insert(curr);
        twinPq.insert(twin);

        while (true) {
            curr = minPQ.delMin();
            if (curr.board.isGoal()) {
                break;
            } else {
                findNeighbors(curr, minPQ);
            }

            twin = twinPq.delMin();
            if (twin.board.isGoal()) {
                break;
            } else {
                findNeighbors(twin, twinPq);
            }
        }
    }

    private void findNeighbors(SearchNode node, MinPQ<SearchNode> pq) {
        Iterable<Board> neighbors = node.board.neighbors();
        for (Board neighbor: neighbors) {
            // Critical optimization: To reduce unnecessary exploration of
            // useless search nodes, when considering the neighbors of a search
            // node, don’t enqueue a neighbor if its board is the same as the
            // board of the previous search node in the game tree.
            if (node.prev == null || !neighbor.equals(node.prev.board)) {
                pq.insert(new SearchNode(neighbor, node));
            }
        }
    }
    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return curr.board.isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) return curr.moves;
        else return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (curr.board.isGoal()) {
            solution = new Stack<>();
            SearchNode node = curr;

            while (node != null) {
                solution.push(node.board);
                node = node.prev;
            }
            return solution;
        } else {
            return null;
        }
    }

    /**
     * Test client provided by Princeton University.
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
