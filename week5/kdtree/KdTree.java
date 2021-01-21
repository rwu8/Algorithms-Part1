import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;

public class KdTree {
    private Node root;
    private int N;
    private RectHV rectHV;

    private class Node {
        private Point2D value;
        private Node right, left;
        private boolean isVertical;

        public Node(Point2D value, Node left, Node right, boolean isVertical) {
            this.value = value;
            this.left = left;
            this.right = right;
            this.isVertical = isVertical;
        }
    }

    public KdTree()                               // construct an empty set of points
    {
        this.root = null;
        this.N = 0;
        rectHV = new RectHV(0, 0, 1, 1);
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return N == 0;
    }

    public int size()                         // number of points in the set
    {
        return N;
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null) throw new IllegalArgumentException();;
        root = insert(root, p, true);
    }

    private Node insert(Node h, Point2D p, boolean isVertical) {
        if (h == null) {
            N++;
            return new Node(p, null, null, isVertical);
        }
        // update current node
        if (h.value.x() == p.x() && h.value.y() == p.y()) h.value = p;
        // insert left if p less than h
        else if (h.isVertical && p.x() < h.value.x() || !h.isVertical && p.y() < h.value.y()) {
            // flip vertical flag at the next level
            h.left = insert(h.left, p, !isVertical);
        // else insert right if p greater than h
        } else {
            // flip vertical flag at the next level
            h.right = insert(h.right, p, !isVertical);
        }
        return h;
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return contains(root, p);
    }

    private boolean contains(Node h, Point2D p) {
        // hit null node during search
        if (h == null) return false;

        if (h.value.x() == p.x() && h.value.y() == p.y()) return true;
        else if (h.isVertical && p.x() < h.value.x() || !h.isVertical && p.y() < h.value.y()) {
            return contains(h.left, p);
        } else {
            return contains(h.right, p);
        }
    }

    public void draw()                         // draw all points to standard draw
    {
        StdDraw.setScale(0, 1);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.1);
        rectHV.draw();
        draw(root, rectHV);
    }

    private void draw(Node h, RectHV rect) {
        if (h == null) return;

        // draw all of the points to standard draw in black
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.1);
        new Point2D(h.value.x(), h.value.y()).draw();

        // Find the min and max division lines
        Point2D min, max;
        if (h.isVertical) {
            // draw all of the subdivisions in red (for vertical splits)
            StdDraw.setPenColor(StdDraw.RED);
            min = new Point2D(h.value.x(), rect.ymin());
            max = new Point2D(h.value.x(), rect.ymax());
        } else {
            // draw all of the subdivisions in blue (for horizontal splits)
            StdDraw.setPenColor(StdDraw.BLUE);
            min = new Point2D(rect.xmin(), h.value.y());
            max = new Point2D(rect.xmax(), h.value.y());
        }

        // Draw our division line
        StdDraw.setPenRadius();
        min.drawTo(max);

        // Recursively draw left and right children nodes
        // update the rectangle size accordingly
        draw(h.left, leftRect(rect, h));
        draw(h.right, rightRect(rect, h));
    }

    private RectHV leftRect(RectHV rect, Node h) {
        // if vertical, update the rectangle with x as the xmax
        if (h.isVertical) {
            return new RectHV(rect.xmin(), rect.ymin(), h.value.x(), rect.ymax());
        } else {
            // if horizontal, update the rectangle with y as the ymax
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), h.value.y());
        }
    }

    private RectHV rightRect(RectHV rect, Node h) {
        // if vertical, update the rectangle with x as the xmin
        if (h.isVertical) {
            return new RectHV(h.value.x(), rect.ymin(), rect.xmax(), rect.ymax());
        } else {
            // if vertical, update the rectangle with y as the ymax
            return new RectHV(rect.xmin(), h.value.y(), rect.xmax(), rect.ymax());
        }
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        ArrayList<Point2D> list = new ArrayList<>();
        range(root, rectHV, rect, list);
        return list;
    }

    private void range(Node h, RectHV initial, RectHV other, ArrayList<Point2D> l) {
        if (h == null) return;

        if (other.intersects(initial)) {
            Point2D p = new Point2D(h.value.x(), h.value.y());
            if (other.contains(p)) {
                l.add(p);
            }
            // Recursively check the child nodes
            range(h.left, leftRect(initial, h), other, l);
            range(h.right, rightRect(initial, h), other, l);
        }
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null) throw new IllegalArgumentException();
        return nearest(root, rectHV, p, null);
    }

    private Point2D nearest(Node h, RectHV rect, Point2D query, Point2D point) {
        if (h == null) return point;
        double queryDistanceToMin = 0.0;
        double rectDistanceToQuery = 0.0;
        Point2D min = point;

        if (min != null) {
            queryDistanceToMin = query.distanceSquaredTo(min);
            rectDistanceToQuery = rect.distanceSquaredTo(query);
        }
        // only evaluate if query distance is greater than the the rectangle to the query point
        if (min == null || queryDistanceToMin > rectDistanceToQuery) {
            Point2D p = new Point2D(h.value.x(), h.value.y());
            if (min == null  || queryDistanceToMin > query.distanceSquaredTo(p)) {
                min = p;
            }

            // Recursively check the nearest points for the children nodes
            // Recalculate rectangles based on left and right children
            if (h.isVertical) {
                RectHV left = new RectHV(rect.xmin(), rect.ymin(), h.value.x(), rect.ymax());
                RectHV right = new RectHV(h.value.x(), rect.ymin(), rect.xmax(), rect.ymax());

                if (query.x() < h.value.x()) {
                    // go left first, then check right if needed
                    min = nearest(h.left, left, query, min);
                    min = nearest(h.right, right, query, min);
                } else {
                    // otherwise query x coord is larger, go right first, then check left if needed
                    min = nearest(h.right, right, query, min);
                    min = nearest(h.left, left, query, min);
                }
            } else {
                RectHV left = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), h.value.y());
                RectHV right = new RectHV(rect.xmin(), h.value.y(), rect.xmax(), rect.ymax());

                if (query.y() < h.value.y()) {
                    min = nearest(h.left, left, query, min);
                    min = nearest(h.right, right, query, min);
                } else {
                    min = nearest(h.right, right, query, min);
                    min = nearest(h.left, left, query, min);
                }
            }
        }
        return min;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {

    }
}
