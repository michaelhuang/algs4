
/**
 * 
 * @author Huangzf
 *
 */
public class Board {
    
    private int[][] tiles;
    private int N;
    private int cachedManhattan = -1;
    
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
    	this.N = blocks.length;
        this.tiles = copy(blocks);
    }
    
    // board dimension N
    public int dimension() {
        return N;
    }
    
    // number of blocks out of place
    public int hamming() {
        int index = 0, count = 0;
        for (int i=0; i < N; i++)
            for (int j=0; j < N; j++)
                if (++index != tiles[i][j])
                    count++;
        
        // do not count the blank
        return --count;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (cachedManhattan >= 0)
            return cachedManhattan;
        int sum = 0;
        int goalRow, goalCol;
        int value = 0;
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                value = tiles[row][col];
                if (value == 0)
                    continue;
                // Because 0 is not at (0, 0).
                goalRow = (value - 1) / N;
                goalCol = (value - 1) % N;
                sum += Math.abs(row - goalRow) + Math.abs(col - goalCol);
            }
        }
        cachedManhattan = sum;
        return sum;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
    	if (tiles[N - 1][N - 1] != 0)
            return false;
        int value = 1;
        for (int row = 0; row < N; row++)
            for (int col = 0; col < N; col++)
                if (tiles[row][col] != value++
                        && (row != N - 1 || col != N - 1))
                    return false;
        return true;
    }
    
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
    	int[][] copy = copy(tiles);
        if (N <= 1)
            return new Board(copy);
        // Find zero so that we don't exchange with the blank
        // This looks like a O(dim^2) algorithm, but on average it should finish
        // in O(1).
        int row = 0;
        int col = 0;
        int value = 0;
        int lastValue = tiles[0][0];
        zerosearch:
        for (row = 0; row < N; row++) {
            for (col = 0; col < N; col++) {
                value = tiles[row][col];
                // Check col>0 because swap must occur on same row
                if (value != 0 && lastValue != 0 && col > 0)
                    break zerosearch;
                lastValue = value;
            }
        }
        copy[row][col] = lastValue;
        copy[row][col - 1] = value;
        return new Board(copy);
    }
    
    // does this board equal y?
    public boolean equals(Object y) {
        if (null == y) return false;
        if (this == y) return true;
        if (this.getClass() != y.getClass()) return false;
        return this.toString().equals(y.toString());
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> queue = new Queue<Board>();
        for (int i=0; i < N; i++)
            for (int j=0; j < N; j++)
                if (0 == tiles[i][j]) {
                    if (i-1 >= 0)
                        queue.enqueue(getNeighbor(i, j, i-1, j));
                    if (i+1 < N)
                        queue.enqueue(getNeighbor(i, j, i+1, j));
                    if (j-1 >= 0)
                        queue.enqueue(getNeighbor(i, j, i, j-1));
                    if (j+1 < N)
                        queue.enqueue(getNeighbor(i, j, i, j+1));
                    
                    return queue;
                }
        
        return queue;
    }
    
    private Board getNeighbor(int i, int j, int m, int n) {
        int[][] neighbor = copy(tiles);
        exch(neighbor, i, j, m, n);
        return new Board(neighbor);
    }
    
    private void exch(int[][] blocks, int i, int j, int m, int n) {
        int temp = blocks[i][j];
        blocks[i][j] = blocks[m][n];
        blocks[m][n] = temp;
    }
    
    private int[][] copy(int[][] blocks) {
    	int[][] temp = new int[N][N];
        for (int i=0; i < N; i++)
            for (int j=0; j < N; j++)
                temp[i][j] = blocks[i][j];
        return temp;
    }
    
    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    // test
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        StdOut.print(initial);
        StdOut.println("manhattan: " + initial.manhattan());
        StdOut.println("hamming: " + initial.hamming());
        StdOut.println("twin: ");
        StdOut.print(initial.twin());
        
        StdOut.println("neighbor: ");
        for (Board board : initial.neighbors()) {
            StdOut.print(board);
            StdOut.println("manhattan: " + board.manhattan());
            StdOut.println("hamming: " + board.hamming());
            StdOut.println("twin: ");
            StdOut.print(board.twin());
        }
    }
}
