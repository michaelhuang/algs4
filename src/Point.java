/*************************************************************************
 * Name:  Huangzf
 * Email: michaelhuang12@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlope();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;
        if (this.x == that.x) return Double.POSITIVE_INFINITY;
        if (this.y == that.y) return 0.0f; // positive zero
        return Double.valueOf(that.y - this.y) / Double.valueOf(that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if      (this.y < that.y) return -1;
        else if (this.y > that.y) return  1;
        else if (this.x < that.x) return -1;
        else if (this.x > that.x) return 1;
        else    return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    private class BySlope implements Comparator<Point> {

        public int compare(Point o1, Point o2) {
            double slopeO1 = Point.this.slopeTo(o1);
            double slopeO2 = Point.this.slopeTo(o2);
            if      (slopeO1 < slopeO2) return -1;
            else if (slopeO1 > slopeO2) return 1;
            else    return 0;
        }
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point point = new Point(4, 4);
        Point point2 = new Point(7, 8);
        StdOut.println(point.slopeTo(point2));
       /* StdOut.println(Double.POSITIVE_INFINITY > Double.NEGATIVE_INFINITY);
        StdOut.println(Double.POSITIVE_INFINITY == Double.POSITIVE_INFINITY);
        StdOut.println(Double.NEGATIVE_INFINITY == Double.NEGATIVE_INFINITY);
        StdOut.println(Double.NaN == Double.NaN);*/
    }
}
