public class redblackBST<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        Key key;
        Value val;
        Node left, right;
        boolean color; // color of parent link
    }

    private boolean isRed(Node x) {
        if (x == null) return false; // null links are black
        return x.color == RED;
    }

    public Val get(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.val;
        }
        return null;
    }

    // orient a (temporarily) right-leaning red link to lean left.
    private Node rotateLeft(Node h) {
        assert isRed(h.right);
        // Node x will be the new parent after roration
        Node x = h.right;
        h.right = x.left;
        // make h a left child of Node x and adjust color
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private Node rotateRight(Node h) {
        assert isRed(h.left);
        // Node x will be the new parent after roration
        Node x = h.left;
        h.left = x.right;
        // make h a right child of Node x and adjust color
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(Node h) {
        assert !isRed(h);
        assert isRed(h.left);
        assert isRed(h.right);
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    private Node put(Node h, Key key, Value val) {
        if (h == null) return new Node(key, val, RED); // insert at bottom and color red
        int cmp = key.compareTo(h.key);
        if      (cmp < 0) h.left = put(h.left, key, val);
        else if (cmp > 0) h.right = put(h.right, key, val);
        else              h.val = val;

        // lean left
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        // balance 4-node
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        // split 4-node
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        return h;
    }
}