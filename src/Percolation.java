/**
 * 
 */

/**
 * @author Huangzf
 *
 */
public class Percolation {

    private WeightedQuickUnionUF quickUnionUF;
    private WeightedQuickUnionUF backWashUF;
    private int N;
    private boolean[][] openness;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int size) {
        N = size;
        openness = new boolean[N][N];
        quickUnionUF = new WeightedQuickUnionUF(N*N + 2); // for two extra virtual sites
        backWashUF = new WeightedQuickUnionUF(N*N + 1);   // for isFull()
    }
    
    // mapping 2D coordinates to 1D coordinates
    private int xyTo1D(int i, int j) {
        return (i-1)*N + j;
    }
    
    // to check whether indices i,j are valid coordinates for the NxN matrix of sites
    private boolean isValid(int i, int j) {
        if (i <= 0 || i > N)
            return false;
        if (j <= 0 || j > N)
            return false;
        return true;
    }
    
    // open site (row i, column j) if it is not already
    public void open(int i, int j) throws Exception {
        if (isValid(i, j)) {
            if (!isOpen(i, j)) {
                openness[i-1][j-1] = true;
                if (isValid(i, j+1) && isOpen(i, j+1)) {
                	quickUnionUF.union(xyTo1D(i, j), xyTo1D(i, j+1));
                	backWashUF.union(xyTo1D(i, j), xyTo1D(i, j+1));
                }
                if (isValid(i+1, j) && isOpen(i+1, j)) {
                	quickUnionUF.union(xyTo1D(i, j), xyTo1D(i+1, j));
                	backWashUF.union(xyTo1D(i, j), xyTo1D(i+1, j));
                }
                if (isValid(i, j-1) && isOpen(i, j-1)) {
                	quickUnionUF.union(xyTo1D(i, j), xyTo1D(i, j-1));
                	backWashUF.union(xyTo1D(i, j), xyTo1D(i, j-1));
                }
                if (isValid(i-1, j) && isOpen(i-1, j)) {
                	quickUnionUF.union(xyTo1D(i, j), xyTo1D(i-1, j));
                	backWashUF.union(xyTo1D(i, j), xyTo1D(i-1, j));
                }
                
                if (i == 1) {
                	quickUnionUF.union(xyTo1D(i, j), 0);
                	backWashUF.union(xyTo1D(i, j), 0);
                }
                if ((i==N))
                    quickUnionUF.union(xyTo1D(i, j), N*N+1); 
            }
        } else {
            throw new IndexOutOfBoundsException("wrong indices");
        }
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) throws Exception {
        if (isValid(i, j))
            return openness[i-1][j-1];
        else
            throw new IndexOutOfBoundsException("wrong indices");
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) throws Exception {
        if (isValid(i, j))
            return backWashUF.connected(0, xyTo1D(i, j));
        else
            throw new IndexOutOfBoundsException("wrong indices");
    }
    
    // does the system percolate?
    public boolean percolates() {
        return quickUnionUF.connected(0, N*N+1);
    }
    
    private void show() throws Exception {
        for (int i = 1; i <= N; i++)
        {
            for (int j = 1; j <= N; j++)
            {
                if (isOpen(i, j))
                    StdOut.print("1 ");
                else
                    StdOut.print("0 ");
            }
            StdOut.println();
        }
        for (int i = 0; i <= N*N+1; i++)
            StdOut.print(quickUnionUF.connected(0, i) ? "1 " : "0 ");
        StdOut.println();
    }
    
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        In in = new In(args[0]);      // input file
        int N = in.readInt();         // N-by-N percolation system
        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(N);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        perc.show();
    }

}
