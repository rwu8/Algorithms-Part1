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

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;
    private Point[] pointList;
    private int N;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        // check for null points
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException(
                        "The array contains one or more null elements.");
            }
        }

        // check for duplicate points
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].equals(points[j])) {
                    throw new IllegalArgumentException("Duplicate point found.");
                }
            }
        }

        N = points.length;
        lineSegments = new LineSegment[N];
        pointList = new Point[N];

        for (int i = 0; i < N; i++) {
            pointList[i] = points[i];
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        ArrayList<LineSegment> segments = new ArrayList<>();
        // sort our array
        Arrays.sort(pointList);
        for (int i = 0; i < N - 3; i++) {
            Point ptI = pointList[i];

            for (int j = i + 1; j < N - 2; j++) {
                Point ptJ = pointList[j];
                double slopeIJ = ptI.slopeTo(ptJ);

                for (int k = j + 1; k < N - 1; k++) {
                    Point ptK = pointList[k];
                    double slopeIK = ptI.slopeTo(ptK);

                    if (slopeIJ == slopeIK) {
                        for (int m = k + 1; m < N; m++) {
                            Point ptM = pointList[m];
                            double slopeIL = ptI.slopeTo(ptM);

                            if (slopeIJ == slopeIK && slopeIJ == slopeIL) {
                                segments.add(new LineSegment(pointList[i], pointList[m]));
                            }
                        }
                    }
                }
            }
        }
        lineSegments = segments.toArray(new LineSegment[segments.size()]);
        return lineSegments.clone();
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
