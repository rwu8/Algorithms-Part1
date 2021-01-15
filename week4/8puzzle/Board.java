import java.util.ArrayList;

public class Board {
    private final int[][] board;
    private final int length;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.length = tiles.length;
        this.board = new int[length][length];

        for (int row=0; row<length; row++) {
            for (int col=0; col<length; col++) {
                this.board[row][col] = tiles[row][col];
            }
        }
    }

    // string representation of this board
    // The toString() method returns a string composed of n + 1 lines.
    // The first line contains the board size n; the remaining n lines contains
    // the n-by-n grid of tiles in row-major order, using 0 to designate the
    // blank square.
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.length + "\n");
        for (int i=0; i<length; i++) {
            for (int j=0; j<length; j++) {
                sb.append(String.format("%2d", board[i][j]));
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return this.length;
    }

    // number of tiles out of place
    public int hamming() {
        int numOutOfPlace = 0;
        int inPlace = 1;
        for (int row=0; row<this.length; row++) {
            for (int col=0; col<this.length; col++) {
                if (this.board[row][col] != inPlace++) {
                    numOutOfPlace++;
                }
                if (inPlace == length * length) {
                    break;
                }
            }
        }
        return numOutOfPlace;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;
        for (int row=0; row<length; row++) {
            for (int col = 0; col < length; col++) {
                int value = board[row][col];
                if (value != 0) {
                    int targetRow = (value-1) / length;
                    int targetCol = (value-1) % length;
                    distance += Math.abs(row - targetRow) + Math.abs(col - targetCol);
                }
            }
        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this.board == y) return true;
        if (!(y instanceof Board)) return false;
        Board yBoard = (Board) y;
        if (yBoard.length != this.length) return false;
        for (int row=0; row<length; row++) {
            for (int col = 0; col < length; col++) {
                if (yBoard.board[row][col] != this.board[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighboringBoards = new ArrayList<>();
        // find location of the blank square

        for (int row=0; row<length; row++) {
            for (int col = 0; col < length; col++) {
                if (board[row][col] == 0) {
                    // Depending on the location of the blank square,
                    // a board can have 2, 3, or 4 neighbors.
                    if (col - 1 >= 0) {
                        Board twin = new Board(board);
                        twin.exch(twin, row, col, row, col - 1);
                        neighboringBoards.add(twin);
                    }
                    if (col + 1 < length) {
                        Board twin = new Board(board);
                        twin.exch(twin, row, col, row, col + 1);
                        neighboringBoards.add(twin);
                    }
                    if (row - 1 >= 0) {
                        Board twin = new Board(board);
                        twin.exch(twin, row, col, row - 1, col);
                        neighboringBoards.add(twin);
                    }
                    if (row + 1 < length) {
                        Board twin = new Board(board);
                        twin.exch(twin, row, col, row + 1, col);
                        neighboringBoards.add(twin);
                    }

                    return neighboringBoards;
                }
            }
        }

        return neighboringBoards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twinBoard = new Board(board);

        int row = 0;
        int col = 0;
        if (board[row][col] == 0) {
            row++;
        }

        // find any row & col to exchange to create a twinBoard
        for (int i=0; i<length; i++) {
            for (int j=0; j<length; j++) {
                if (board[i][j] != board[row][col] && board[i][j] != 0) {
                    twinBoard.exch(twinBoard, i, j, row, col);
                    return twinBoard;
                }
            }
        }
        return twinBoard;
    }

    private void exch(Board board, int row1, int col1, int row2, int col2) {
        int temp = board.board[row1][col1];
        board.board[row1][col1] = board.board[row2][col2];
        board.board[row2][col2] = temp;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] initial = {{0, 2, 1}, {3, 4, 5}, {7, 8, 6}};
        Board board = new Board(initial);
        System.out.println(board);
        System.out.println("hamming: " + board.hamming());
        System.out.println("manhattan: " + board.manhattan());
        System.out.println("neighbors:\n" + board.neighbors());
    }


}