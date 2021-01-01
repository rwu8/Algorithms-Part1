/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;
    private Point[] pointList;
    private int N;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        // check for null points
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException(
                        "The array contains one or more null elements.");
            }
        }

        pointList = points.clone();
        Arrays.sort(pointList);

        // check for duplicate points
        for (int i = 0; i < N; i++) {
            if (pointList[i].equals(pointList[i + 1])) {
                throw new IllegalArgumentException("Duplicate point found.");
            }
        }

        N = points.length;
        lineSegments = new LineSegment[N];
        ArrayList<LineSegment> segments = new ArrayList<>();

        // Note: we will iterate through points and pointList
        for (int i = 0; i < N; i++) {
            // Think of point p as the origin.
            Point point = points[i];
            // For each other point q, determine the slope it makes with p.
            // Sort the points according to the slopes they makes with p.
            Arrays.sort(pointList, point.slopeOrder());
            int startIndex = 1;
            for (int j = startIndex; j < N - 2; ) {
                // reset count with each iteration
                int count = 0;
                final double currentSlope = point.slopeTo(pointList[j]);

                // continue counting as long as the slope matches our current index
                while (j < N && point.slopeTo(pointList[j]) == currentSlope) {
                    count++;
                    j++;
                }

                // ensure max line segment
                // Check if any 3 (or more) adjacent points in the sorted order have equal slopes
                // with respect to p. If so, these points, together with p, are collinear.
                if (count >= 3) {
                    // sort subarray portion of the list.
                    // note we need the remainder of the list to remain unmodified for future iterations
                    Arrays.sort(pointList, startIndex, j);
                    if (point.compareTo(pointList[startIndex]) < 0) {
                        segments.add(new LineSegment(point, pointList[startIndex + count - 1]));
                    }
                }
                startIndex = j;
            }
        }
        lineSegments = segments.toArray(new LineSegment[segments.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments;
    }

    /**
     * Client provided by Princeton University.
     */
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
