import java.util.Iterator;
import java.util.Set;

//reference http://algs4.cs.princeton.edu/32bst/BST.java.html

/** A data structure that uses a binary search tree to store pairs of keys and values.
 *  Any key must appear at most once in the dictionary, but values may appear multiple
 *  times. Supports get(key), put(key, value), and contains(key) methods. The value
 *  associated to a key is the value in the last call to put with that key. */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;
    private int size = 0;
    /** Returns the value corresponding to KEY or null if no such value exists. */
    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node x, K key) {
        if (x == null) {
            return null;
        } else if (key.compareTo(x.key) < 0) {
            return get(x.left, key);
        } else if (key.compareTo(x.key) > 0) {
            return get(x.right, key);
        } else return x.val;
    }

    @Override
    public int size() {
        return size;
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    /** Inserts the key-value pair of KEY and VALUE into this dictionary,
     *  replacing the previous value associated to KEY, if any. */
    public void put(K key, V val) {
        put(root, key, val);
    }

    private void put(Node x, K key, V val) {
        if (x == null) {
            x = new Node(key, val);
            root = x;
            size++;
            return;
        }
        if (key.compareTo(x.key) < 0) {
            if (x.left != null) {
                put(x.left, key, val);
            } else {
                x.left = new Node(key, val); 
                size++; 
                return;              
            }
        } else if (key.compareTo(x.key) > 0) {
            if (x.right != null) {
                put(x.right, key, val);
            } else {
                x.right = new Node(key, val);
                size++;
                return;
            }
        } else {
            x.val = val;
            return;
        }
    }

    /** Returns true if and only if this dictionary contains KEY as the
     *  key of some key-value pair. */
    public boolean containsKey(K key) {
        return (get(key)!=null);
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node x) {
        if (x != null) {
            printInOrder(x.left);
            System.out.println(x.key + " : " + x.val);
            printInOrder(x.right);
        }
    }


    /** Represents one node in the binary search tree that stores the key-value pairs
     *  in the dictionary. */
    private class Node {
        
        /** Stores KEY as the key in this key-value pair, VAL as the value, and
         *  left as the left child node and right as the right child node. */
        public Node(K key, V val) {
            this.key = key;
            this.val = val;
  
        }

        /** Stores the key of the key-value pair of this node in the tree. */
        private K key;
        /** Stores the value of the key-value pair of this node in the tree. */
        private V val;
        /** Stores the left child node of this node. */
        private Node left;
        /** Stores the right child node of this node. */
        private Node right;
        
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

}