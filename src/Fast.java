import java.util.Arrays;

/**
 * Write a program Fast.java that implements this algorithm. The order of growth 
 * of the running time of your program should be N2 log N in the worst case and 
 * it should use space proportional to N.
 */

/**
 * @author Huangzf
 *
 */
public class Fast {
    
    // per line has 4 points at least
    private int LEAST_POINTS_PER_LINE = 4;
    // avoid print subsegments of a line segment containing 5 or more points
    private Point FINISH = new Point(-1, -1);
    
    // print the line and draw it
    private void drawLine(Point point, Point[] list, int start, int end) {
        if (null == list || list.length == 0) return;
        if (end - start <= LEAST_POINTS_PER_LINE -2) return;
        if (list[end-1].compareTo(FINISH) == 0) return;
        
        StdOut.print(point);
        StdOut.print(" -> ");
        for (int i=start; i < end; i++) {
            StdOut.print(list[i]);
            if (i != end-1) StdOut.print(" -> ");
        }
        StdOut.println();
        
        point.drawTo(list[end-1]);
        
        FINISH = list[end-1];
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
       
        if (args.length != 1) throw new IllegalArgumentException("bad args.");
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        
        // store all points in points[] and draw all
        String fileName = args[0];
        In in = new In(fileName);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i=0; i < N; i++) {
            Point p = new Point(in.readInt(), in.readInt());
            points[i] = p;
            p.draw();
        }
        StdDraw.show(0);
        
        Arrays.sort(points);
        
        Fast fast = new Fast();
        // test
        /*for (int m=0; m < N; m++)
            StdOut.println(points[m].toString());*/
        
        Point[] points2 = new Point[N];
        for (int i=0; i < N-fast.LEAST_POINTS_PER_LINE+1; i++) {
            // pick origin
            Point point = points[i];
            int low = 0;
            // size of remaining points
            int sizeOfRemain = N-i-1;
            System.arraycopy(points, i+1, points2, 0, sizeOfRemain);
            Arrays.sort(points2, 0, sizeOfRemain, point.SLOPE_ORDER);
            
            // test
            /*StdOut.println("Point: " + point.toString());
            for (int k=0; k < sortSize; k++)
                StdOut.println(points2[k].toString() + point.slopeTo(points2[k]));
            StdOut.println();*/
            
            double lastSlope = point.slopeTo(points2[0]);
            for (int j=1; j < sizeOfRemain; j++) {
                double currSlope = point.slopeTo(points2[j]);
                if (currSlope != lastSlope) {
                    fast.drawLine(point, points2, low, j);
                    low = j;
                    lastSlope = point.slopeTo(points2[low]);
                }
            }
            
            // last few points are in a line
            fast.drawLine(point, points2, low, sizeOfRemain);
        }
        StdDraw.show(0);
    }

}
