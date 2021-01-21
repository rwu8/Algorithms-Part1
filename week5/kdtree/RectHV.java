import edu.princeton.cs.algs4.StdDraw;

public class RectHV {
    private double xmin, xmax, ymin, ymax;

    public    RectHV(double xmin, double ymin,      // construct the rectangle [xmin, xmax] x [ymin, ymax]
                     double xmax, double ymax)      // throw an IllegalArgumentException if (xmin > xmax) or (ymin > ymax)
    {
        if (xmin > xmax || ymin > ymax) throw new IllegalArgumentException();
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
    }

    public  double xmin()                           // minimum x-coordinate of rectangle
    {
        return this.xmin;
    }

    public  double ymin()                           // minimum y-coordinate of rectangle
    {
        return this.ymin;
    }

    public  double xmax()                           // maximum x-coordinate of rectangle
    {
        return this.xmax;
    }
    public  double ymax()                           // maximum y-coordinate of rectangle
    {
        return this.ymax;
    }

    public boolean contains(Point2D p)              // does this rectangle contain the point p (either inside or on boundary)?
    {
        return p.x() >= xmin && p.x() <= xmax && p.y() >= ymin && p.y() <= ymax;
    }

    public boolean intersects(RectHV that)          // does this rectangle intersect that rectangle (at one or more points)?
    {
        return this.xmax >= that.xmin && this.ymax >= that.ymin
                && that.xmax >= this.xmin && that.ymax >= this.ymin;
    }

    public  double distanceTo(Point2D p)            // Euclidean distance from point p to closest point in rectangle
    {
        double dx = 0.0, dy = 0.0;
        // return 0 if the point is contained in this rectangle
        if (contains(p)) return 0.0;

        if (p.x() > xmax) dx = p.x() - xmax;
        else if (p.x() < xmin) dx = p.x() - xmin;
        if (p.y() > ymax) dy = p.y() - ymax;
        else if (p.y() < ymin) dy = p.y() - ymin;

        return dx + dy;
    }

    public  double distanceSquaredTo(Point2D p)     // square of Euclidean distance from point p to closest point in rectangle
    {
        return Math.pow(distanceTo(p), 2);
    }

    public boolean equals(Object that)              // does this rectangle equal that object?
    {
        if (this == that) return true;
        if (that == null) return false;
        if (!(that instanceof RectHV)) return false;
        RectHV tempRect = (RectHV) that;
        return tempRect.xmin == this.xmin && tempRect.xmax == this.xmax &&
                tempRect.ymin == this.ymin && tempRect.ymax == this.ymax;
    }

    public void draw()                           // draw to standard draw
    {
        StdDraw.line(xmin, ymin, xmax, ymin);
        StdDraw.line(xmax, ymin, xmax, ymax);
        StdDraw.line(xmax, ymax, xmin, ymax);
        StdDraw.line(xmin, ymax, xmin, ymin);
    }

    public  String toString()                       // string representation
    {
        return "Xmin: " + xmin + "\nXmax: " + xmax + "\nYmin: " + ymin
                + "\nYmax: " + ymax;
    }
}