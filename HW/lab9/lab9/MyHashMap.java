package lab9;

import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Shinchan
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 4;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;//each bucket is a smaller ArrayMap; so buckets is ArrayMap list
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int bucketIndex = hash(key);
        return buckets[bucketIndex].get(key);
    }

    private int newhash(K key) {
        if (key == null) {
            return 0;
        }
        int numBuckets = buckets.length*2;
        return Math.floorMod(key.hashCode(), numBuckets);
    }
    private void resize(int capacity) {
        //class array/list can be instantiated while generic array cannot be
        ArrayMap<K, V>[] newBuckets = new ArrayMap[capacity];
        //initializing new buckets
        for (int i = 0; i < newBuckets.length; i++) {
            newBuckets[i] = new ArrayMap<>();
        }
        /**
        for (int i = 0; i < buckets.length; i++) {
            ArrayMap exterChain = buckets[i];
            Set<K> keys = exterChain.keySet();
            for (K k: keys) {
                int newBucketsIndex = newhash(k);
                V value = buckets[i].get(k);
                newBuckets[newBucketsIndex].put(k, value);
            }
        }*/
        for (int i = 0; i < buckets.length; i++) {
            //ArrayMap exterChain1 = buckets[i];
            //cannot do inhanced for loop to above exterChain. Type is object instead of <K, V>
            //ArrayMap exterChain2<K, V> = buckets[i]; this is Ok; replace buckets[i] with exterChain2
            for (K k : buckets[i]) {
                int newBucketsIndex = newhash(k);
                V value = buckets[i].get(k);
                newBuckets[newBucketsIndex].put(k, value);
            }
        }
        buckets = newBuckets;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Null values not allowed.");
        }
        int bucketIndex = hash(key);
        if (!buckets[bucketIndex].containsKey(key)) {
            size += 1;
        }
        buckets[bucketIndex].put(key, value);
        //resize(unnecessary)
        if (loadFactor() > MAX_LF) {
            resize(buckets.length * 2);
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {//getter
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
