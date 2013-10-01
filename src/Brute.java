import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Brute force. Write a program Brute.java that examines 4 points at a time and checks whether 
 * they all lie on the same line segment, printing out any such line segments to standard output 
 * and drawing them using standard drawing. To check whether the 4 points p, q, r, and s are collinear, 
 * check whether the slopes between p and q, between p and r, and between p and s are all equal.
 * 
 * The order of growth of the running time of your program should be N4 in the worst case and 
 * it should use space proportional to N.
 */

/**
 * @author Huangzf
 *
 */
public class Brute {

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
        
        // iterating all 4-tuples and check if the 4 points are collinear
        Collections.sort(points);
        List<Point> collinears = new ArrayList<Point>();
        int size = points.size();
        for (int i=0; i < size; i++)
            for (int j=i+1; j < size; j++)
                for (int k=j+1; k < size; k++)
                    for (int m=k+1; m < size; m++) {
                        
                        if (points.get(i).slopeTo(points.get(j)) == points.get(i).slopeTo(points.get(k))
                                && points.get(i).slopeTo(points.get(j)) == points.get(i).slopeTo(points.get(m))) {
                            collinears.add(points.get(i));
                            collinears.add(points.get(j));
                            collinears.add(points.get(k));
                            collinears.add(points.get(m));
                        }
                    }
        
        // print each line segment as an ordered sequence of its constituent points
        for (int n=0; n < collinears.size();) {
            StdOut.print(collinears.get(n++));
            StdOut.print(" -> ");
            StdOut.print(collinears.get(n++));
            StdOut.print(" -> ");
            StdOut.print(collinears.get(n++));
            StdOut.print(" -> ");
            StdOut.print(collinears.get(n++));
            StdOut.println();
        }
        
        // draw the line segment
        for (int i=0; i < collinears.size();) {
            collinears.get(i).drawTo(collinears.get(i+3));
            i = i + 4;
        }
        StdDraw.show(0);
    }

}
