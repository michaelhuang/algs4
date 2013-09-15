/**
 * 
 */

/**
 * @author Huangzf
 * 
 */
public class PercolationStats {
	private int[] x_t;
	private int size;

	public PercolationStats(int N, int T) throws Exception {
		if ((N <= 0) || (T <= 0))
			throw new java.lang.IllegalArgumentException(
					"Either N or T has illegal values");
		x_t = new int[T];
		size = N;
		for (int i = 0; i < T; i++) {
			Percolation perc = new Percolation(N);
			x_t[i] = 0;
			while (!perc.percolates()) {
				int row = StdRandom.uniform(1, N+1);
				int column = StdRandom.uniform(1, N+1);
				if (perc.isOpen(row, column)) 
					continue;
				perc.open(row, column);
				x_t[i] += 1;
			}
			// perc.show(); // print the matrix
			// StdOut.println(i);
			perc = null;
		}
	}

	public double mean() {
		return StdStats.mean(x_t) / (size*size);
	}

	public double stddev() {
		if (x_t.length == 1) return Double.NaN;
		return StdStats.stddev(x_t) / (size*size);
	}
	
	public double confidenceLo() {
		return mean() - 1.96 * stddev() / Math.sqrt(x_t.length);
	}
	
	public double confidenceHi() {
		return mean() + 1.96 * stddev() / Math.sqrt(x_t.length);
	}

	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		StdOut.printf("N and T                 = %d, %d\n", N, T);
		PercolationStats stat = new PercolationStats(N, T);
		double mean = stat.mean();
		double stddev = stat.stddev();
		double confidenceLo = stat.confidenceLo();
		double confidenceHi = stat.confidenceHi();
		StdOut.printf("mean                    = %f\n", mean);
		StdOut.printf("stddev                  = %f\n", stddev);
		StdOut.printf("confidence interval = %f, %f\n", confidenceLo, confidenceHi);
	}
}
