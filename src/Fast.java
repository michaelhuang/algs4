import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    
    private static int LEAST_POINTS_PER_LINE = 4;
    
    private static void drawLine(Point point, List<Point> list) {
        if (null == list || list.isEmpty()) return;
        Point max = point, min = point;
        for (Point i : list) {
            if (max.compareTo(i) < 0) max = i;
            if (min.compareTo(i) > 0) min = i;
        }
            
        min.drawTo(max);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
       
        if (args.length != 1) throw new IllegalArgumentException("bad args.");
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        
        String fileName = args[0];
        In in = new In(fileName);
        int N = in.readInt();
        List<Point> points = new ArrayList<Point>();
        for (int i=0; i < N; i++) {
            Point p = new Point(in.readInt(), in.readInt());
            points.add(p);
            p.draw();
        }
        StdDraw.show(0);
                
        int low = 0;
        for (int i=0; i < N-LEAST_POINTS_PER_LINE+1; i++) {
            Point point = points.get(i);
            List<Point> list = points.subList(i+1, N);
            Collections.sort(list, point.SLOPE_ORDER);
            
            StdOut.println("Point: " + point.toString());
            for (Point point2 : list)
                StdOut.println(point2.toString() + point.slopeTo(point2));
            StdOut.println();
            
            double lastSlope = point.slopeTo(list.get(0));
            for (int j=1; j < list.size(); j++) {
                double currSlope = point.slopeTo(list.get(j));
                if (currSlope != lastSlope) {
                    if (j - low > LEAST_POINTS_PER_LINE -2) {
                        drawLine(point, list.subList(low, j));
                        StdOut.print(point.toString());
                        StdOut.print(" -> ");
                        while (low < j) {
                            StdOut.print(list.get(low));
                            if (++low != j) StdOut.print(" -> ");
                        }
                        StdOut.println();
                    } else {
                        low = j;
                    }
                    lastSlope = point.slopeTo(list.get(low));
                }
            }
            
            if (list.size() - low > LEAST_POINTS_PER_LINE - 2) {
                drawLine(point, list.subList(low, list.size()));
                StdOut.print(point.toString());
                StdOut.print(" -> ");
                while (low < list.size()) {
                    StdOut.print(list.get(low));
                    if (++low != list.size()) StdOut.print(" -> ");
                }
                StdOut.println();
            }
        }
        StdDraw.show(0);
    }

}
