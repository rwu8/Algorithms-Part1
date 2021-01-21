import edu.princeton.cs.algs4.StdDraw;

public class Point2D implements Comparable<Point2D> {
    private double x, y;

    public Point2D(double x, double y)              // construct the point (x, y)
    {
        if (x == Double.NaN || x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY
                ||  y == Double.NaN || y == Double.POSITIVE_INFINITY || y == Double.NEGATIVE_INFINITY)
            throw new IllegalArgumentException();

        this.x = x;
        this.y = y;
    }

    public  double x()                              // x-coordinate
    {
        return this.x;
    }
    public  double y()                              // y-coordinate
    {
        return this.y;
    }

    public  double distanceTo(Point2D that)         // Euclidean distance between two points
    {
        return Math.sqrt(distanceSquaredTo(that));
    }

    public  double distanceSquaredTo(Point2D that)  // square of Euclidean distance between two points
    {
        return Math.pow((that.x - this.x), 2) + Math.pow((that.y - this.y), 2);
    }

    // Compares two points by y-coordinate, breaking ties by x-coordinate. Formally, the invoking
    // point (x0, y0) is less than the argument point (x1, y1) if and only if
    // either y0 < y1 or if y0 == y1 and x0 < x1.
    public     int compareTo(Point2D that)          // for use in an ordered symbol table
    {
        if          (this.y < that.y) return -1;
        else if     (this.y > that.y) return 1;
        else if     (this.x < that.x) return -1;
        else if     (this.x > that.x) return 1;
        return 0;
    }

    public boolean equals(Object that)              // does this point equal that object?
    {
        if (this == that) return true;
        if (that == null) return false;
        if (!(that instanceof Point2D)) return false;
        Point2D tempPoint = (Point2D) that;
        return tempPoint.y == this.y && tempPoint.x == this.x;
    }

    public    void draw()                           // draw to standard draw
    {
        StdDraw.point(x, y);
    }

    public void drawTo(Point2D that)
    {
        double dist = distanceTo(that);
        StdDraw.setPenRadius(dist);
    }

    public  String toString()                       // string representation
    {
        return "X: " + x + "; Y: " + y;
    }
}