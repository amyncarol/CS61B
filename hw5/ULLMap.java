import java.util.HashSet;
import java.util.Set;
import java.lang.Iterable;
import java.util.Iterator;
import java.util.NoSuchElementException;

 /* java.util.Set needed only for challenge problem. */

/** A data structure that uses a linked list to store pairs of keys and values.
 *  Any key must appear at most once in the dictionary, but values may appear multiple
 *  times. Supports get(key), put(key, value), and contains(key) methods. The value
 *  associated to a key is the value in the last call to put with that key. 
 *
 *  For simplicity, you may assume that nobody ever inserts a null key or value
 *  into your map.
 */ 
public class ULLMap<K, V> implements Map61B<K, V>, Iterable<K> { //FIX ME
    /** Keys and values are stored in a linked list of Entry objects.
      * This variable stores the first pair in this linked list. You may
      * point this at a sentinel node, or use it as a the actual front item
      * of the linked list. 
      */
    private Entry front;
    private int size;

    public ULLMap() {
        front = null;
        size = 0;
    }

    public ULLMap(K key, V val) {
        front = new Entry(key, val, null);
        size = 1;
    }


    @Override
    public V get(K key) { //FIX ME
    //FILL ME IN
        if (front.get(key)!= null) {
            return front.get(key).val;
        } else {
            return null; //FIX ME
        }
    }

    @Override
    public void put(K key, V val) { //FIX ME
    //FILL ME IN
        if (containsKey(key)) {
            front.get(key).val = val;
        } else {
            Entry newFront = new Entry(key, val, front);
            front = newFront;
            size +=1;
        }
    }

    @Override
    public boolean containsKey(K key) { //FIX ME
    //FILL ME IN
        if (front == null){
            return false;
        }
        if (front.get(key)!=null){
            return true;
        } else {
            return false;
        } //FIX ME
    }

    @Override
    public int size() {
        return size; // FIX ME (you can add extra instance variables if you want)
    }

    @Override
    public void clear() {
        front = null;
        size = 0;
    //FILL ME IN
    }


    /** Represents one node in the linked list that stores the key-value pairs
     *  in the dictionary. */
    private class Entry{

        /** Stores KEY as the key in this key-value pair, VAL as the value, and
         *  NEXT as the next node in the linked list. */
        public Entry(K k, V v, Entry n) { //FIX ME
            key = k;
            val = v;
            next = n;
        }

        /** Returns the Entry in this linked list of key-value pairs whose key
         *  is equal to KEY, or null if no such Entry exists. */
        public Entry get(K k) { //FIX ME
            //FILL ME IN (using equals, not ==)
            Entry cur = this;
            while((cur!=null) && (!cur.key.equals(k))){
                cur = cur.next;
            }
            return cur;   //FIX ME
        }

        /** Stores the key of the key-value pair of this node in the list. */
        private K key; //FIX ME
        /** Stores the value of the key-value pair of this node in the list. */
        private V val; //FIX ME
        /** Stores the next Entry in the linked list. */
        private Entry next;
    
    }

    /* Methods below are all challenge problems. Will not be graded in any way. 
     * Autograder will not test these. */
    @Override
    public V remove(K key) { //FIX ME SO I COMPILE
        if(containsKey(key)){
            Entry cur = front.get(key);
            V returnVal = cur.val;
            cur.key = cur.next.key;
            cur.val = cur.next.val;
            cur.next = cur.next.next;
            size -=1;
            return returnVal;
        }
        return null;        
    }

    @Override
    public V remove(K key, V value) { //FIX ME SO I COMPILE
        if (containsKey(key)){
            Entry cur = front.get(key);
            if (cur.val.equals(value)){
                V returnVal = cur.val;
                cur.key = cur.next.key;
                cur.val = cur.next.val;
                cur.next = cur.next.next;
                size -=1;
                return returnVal;
            }
        }
        return null;
    }

    @Override
    public Set<K> keySet() { //FIX ME SO I COMPILE
        Entry cur = front;
        Set<K> set= new HashSet<K>();
        while(cur!=null){
            set.add(cur.key);
            cur = cur.next;
        }
        return set;
    }

    /* Iterator implementation */
    @Override
    public Iterator<K> iterator(){
        return new ULLMapIter();
    }

    public class ULLMapIter implements Iterator<K>{
        private Entry cur;

        public ULLMapIter(){
            cur = front;
        }

        public boolean hasNext(){
            if (cur==null){
                return false;
            } else {
                return true;
            }
        }

        public K next(){
            if(hasNext()){
                K returnKey = cur.key;
                cur = cur.next;
                return returnKey;
            } else{
                throw new NoSuchElementException();
            }

        }

        public void remove(){
            throw new UnsupportedOperationException();
        }

    }

    /* inverting this map, return a inverted map */
    public static <E1, E2> ULLMap<E1, E2> invert(ULLMap<E2, E1> um){
        ULLMap<E1, E2> umInverse = new ULLMap<E1, E2>();
        Iterator<E2> umIterator = um.iterator();
        while(umIterator.hasNext()){
            E2 e2 = umIterator.next();
            umInverse.put(um.get(e2), e2);
        }
        return umInverse;
    }

}