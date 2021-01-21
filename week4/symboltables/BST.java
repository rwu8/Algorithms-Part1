public class BST<Key extends Comparable<Key>, Value> {
    private Node root;

    private class Node {
        // Key and Value are generic types; Key is Comparable
        private Key key;
        private Value value;
        private Node left, right;
        private int count;
        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    // Cost. Number of compares is equal to 1 + depth of Node.
    private Node put(Node x, Key key, Value val) {
        // if empty tree
        if (x == null) return new Node(key, val);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) {
            x.left = put(x.left, key, val);
        }
        else if (cmp > 0) {
            x.right = put(x.right, key, val);
        } else {
            x.val = val;
        }
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    // Cost. Number of compares is equal to 1 + depth of Node.
    public Value get(Key key) {
        Node x = root;

        while (x != null) {
            int cmp = key.compareTo(x.key);
            if          (cmp < 0) x = x.left;
            else if     (cmp > 0) x = x.right;
            else                  return x.val;
        }
        return null;
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);

        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);

        Node t = floor(x.right, key);
        if (t != null)  return t;
        else            return x;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.count;
    }

    // how many keys < k?
    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node x) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if          (cmp < 0) return rank(key, x.left);
        // if greater than the Node x, we need to account for the root, left subtree and any rank of the right
        else if     (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else                 return size(x.left);
    }

    public Iterable<Key> keys() {
        Queue<Key> q = new Queue<Key>();
        inorder(root, q);
        return q;
    }

    private void inorder(Node x, Queue<Key> q) {
        if (x == null) return;
        inorder(x.left, q);
        q.enqueue(x.key);
        inorder(x.right, q);
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    // Hibbard deletion
    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        // search for the key
        if      (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left; // no right child
            if (x.left == null) return x.right; // no left child
            // replace with successor
            Node t = x; // Node to remove
            x = min(t.right); // keep track of successor Node
            x.right = deleteMin(t.right); // delete the min element in the right subtree
            x.left = t.left; // fix the links to the successor Node
        }
        // fix the count for the node
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        // fix the count for the node
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }
}