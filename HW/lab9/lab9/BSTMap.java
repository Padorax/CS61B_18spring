package lab9;

import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Shinchan
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        } else if (p.key.equals(key)) {
            return p.value;
        } else if (p.key.compareTo(key) > 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    //can be rewritten using getNode(getNodeHelper) instead of get
    @Override
    public V get(K key) {
        if (root == null) {
            return null;
        }
        if (root.key.equals(key)) {
            return root.value;
        } else if (root.key.compareTo(key) > 0) {
            //search left subtree
            //or we can have Node leftsubtree = root.left; leftsubtree.get(key);
            return getHelper(key, root.left);
        } else {
            return getHelper(key, root.right);
        }
    }


    private Node getNodeHelper (K key, Node p) {
        //null is the base case in recursion, cannot
        // be abandoned cuz the first entry from outside is ensured not null
        if (p == null) {
            return null;
        } else if (p.key.equals(key)) {
            return p;
        } else if (p.key.compareTo(key) > 0) {
            return getNodeHelper(key, p.left);
        } else {
            return getNodeHelper(key, p.right);
        }
    }
    /** Similar to get, return desired Node instead of value */
    private Node getNode (K key) {
        if (root == null) {
            return null;
        } else if (root.key.equals(key)) {
            return root;
        } else if (root.key.compareTo(key) > 0) {
            return getNodeHelper(key, root.left);
        } else {
            return getNodeHelper(key, root.right);
        }

    }

    /** parent of inserted Node, root p is not null */
    private Node insertNodeParent(K key, V value, Node p) {
        /**
        //iteration
        Node pChild = p;
        if (p.key.compareTo(key) > 0) {
            pChild = p.left;
        } else { pChild = p.right;}
        while (pChild != null) {
            p = pChild;
            if (p.key.compareTo(key) > 0) {
                pChild = p.left;
            } else {
                pChild = p.right;
            }
        }
        return p;
         */
        //recursion
        if (p.key.compareTo(key) > 0 && p.left == null) {
            return p;
        }
        if (p.key.compareTo(key) < 0 && p.right == null) {
            return p;
        }
        if (p.key.compareTo(key) > 0) {
            return insertNodeParent(key, value, p.left);
        } else {
            return insertNodeParent(key, value, p.right);
        }
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
     * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        Node addNode = new Node(key, value);
        if (p == null) {
            return addNode;
        }
        Node inNode = insertNodeParent(key, value, p);
        if (inNode.key.compareTo(key) > 0) {
            inNode.left = addNode;
        } else {
            inNode.right = addNode;
        }
        return root;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Null values not allowed.");
        }
        //if root == null?
        Node curNode = getNode(key);
        if (curNode == null) {
            //key doesn't exist
            size += 1;
            root = putHelper(key, value, root);
        } else {
            //update existing key value
            curNode.value = value;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////
    //Depth First traversal of BST
    private class dfBSTMapIter implements Iterator<K> {
        //this is iterating over key-- i.e, the next() method of Iterator returns K type.
        // we cannot iterate over nodes-- i.e, implements Iterator<Node>
        //since in Map61B, Map61B<K, V> nees to extends Iterable<Node>, Node is private in BSTMap
        /**
         * Create a new ULLMapIter by setting cur to the first node in the
         * linked list that stores the key-value pairs.
         */
        private dfBSTMapIter() {
            //cur = root;//root of BSTMap
            if (root != null) {
                fringe.push(root);
            }
        }

        @Override
        public boolean hasNext() {
            return !fringe.empty();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException(" Tree ran out of elements");
            }
            Node node = fringe.pop();
            K ret = node.key;
            if (node.right != null) {
                fringe.push(node.right);
            }
            if (node.left != null) {
                fringe.push(node.left);
            }
            return ret;

        }

        Node cur;
        Stack<Node> fringe = new Stack<> ();
    }

    //return the right most child of p
    private Node rightMost(Node p) {
        while (p.right != null) {
            p = p.right;
        }
        return p;
    }


    //Remove a node whose key matches given and return the new root
    //(k must be contained--no)
    private Node removeNode(K key) {
        Node sentinel = new Node(null, null);
        sentinel.left = root;
        sentinel.right = root;
        Node prev = sentinel;
        Node cur = root;
        while (cur != null) {
            if (cur.key.equals(key)) {
                break;
            } else if (cur.key.compareTo(key) > 0) {
                prev = cur;
                cur = cur.left;
            } else if (cur.key.compareTo(key) < 0) {
                prev = cur;
                cur = cur.right;
            }
        }
        //cur is the node we want to remove, prev is its parent; if cur == null, no such element
        if (cur == null) {
            return sentinel.left;
        }
        //node to be removed has 0 child
        if (cur.left == null && cur.right == null) {
            if (prev.left == cur) {
                prev.left = null;
            } else {
                prev.right = null;
            }//1 child
        } else if (prev.left == cur){
            if (cur.right == null) { prev.left = cur.left;}
            if (cur.left == null) { prev.left = cur.right;}
        } else if (prev.right == cur) {
            if (cur.right == null) { prev.right = cur.left;}
            if (cur.left == null) {prev.right = cur.right;}
        } else {//2 child
            if (prev.left == cur) {prev.left = rightMost(cur);}
            else {prev.right = rightMost(cur);}
        }

        return sentinel.left;
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new TreeSet<>();
        for (K keys : this) {//keys is what is returned by dfBSTMapIter.next()
            keySet.add(keys);
        }
        return keySet;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (get(key) == null) {
            //size = 0 or does not exist
            return null;
        }
        size -= 1;
        V retVal = get(key);
        /**Ugly boundary. we might need to delete root when size > 1
        if (size == 1) {
            //delete root
            clear();
            return retVal;
        }
         */
        root = removeNode(key);
        return retVal;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (get(key) == value) {
            return remove(key);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {// The whole BSTMap is iterable
        return new dfBSTMapIter();
    }
}
