import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> pointSet;

    public         PointSET()                               // construct an empty set of points
    {
        this.pointSet = new SET<Point2D>();
    }

    public           boolean isEmpty()                      // is the set empty?
    {
        return pointSet.isEmpty();
    }

    public               int size()                         // number of points in the set
    {
        return pointSet.size();
    }

    public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null) throw new IllegalArgumentException();
        pointSet.add(p);
    }

    public           boolean contains(Point2D p)            // does the set contain point p?
    {
        if (p == null) throw new IllegalArgumentException();
        return pointSet.contains(p);
    }

    public              void draw()                         // draw all points to standard draw
    {
        for (Point2D p : pointSet) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> contained = new ArrayList<>();
        for (Point2D p: pointSet) {
            if (rect.contains(p)) {
                contained.add(p);
            }
        }
        return contained;
    }

    public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null) throw new IllegalArgumentException();
        if (pointSet.isEmpty()) return null;
        Point2D nearestPoint = null;
        for (Point2D point: pointSet) {
            if (nearestPoint == null) nearestPoint = point;
            else {
                if (point.distanceTo(p) < nearestPoint.distanceTo(p)) {
                    nearestPoint = point;
                }
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {

    }
}