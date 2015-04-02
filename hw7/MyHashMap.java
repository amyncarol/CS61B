import java.util.Set;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;

public class MyHashMap<K, V> implements Map61B<K, V> {
    
    private ArrayList<LinkedList<Entry>> hashmap;
    private float loadFactor;
    private int capacity;
    private int size;
    private Set<K> keyset;
    
    public MyHashMap() {
        this(16, (float) 0.75);
       
    }
    public MyHashMap(int initialSize) {
        this(initialSize, (float) 0.75);
       
    }
    public MyHashMap(int initialSize, float loadFactor) {
        this.hashmap = new ArrayList<LinkedList<Entry>>(initialSize);
        for (int i = 0; i < initialSize; i++) {
            hashmap.add(new LinkedList<Entry>());
        }
        this.loadFactor = loadFactor;
        this.capacity = initialSize;
        this.size = 0;
        this.keyset = new HashSet<K>();
    }
    
    
    @Override
    public void clear() {
        this.hashmap = new ArrayList<LinkedList<Entry>>(16);
        for (int i = 0; i < 16; i++) {
            hashmap.add(new LinkedList<Entry>());
        }
        this.loadFactor = (float) 0.75;
        this.capacity = 16;
        this.size = 0;
        keyset = new HashSet<K>();
    }

    @Override
    public boolean containsKey(K key) {
        LinkedList<Entry> ll = hashmap.get(getHash(key));
        if (ll.size() != 0) {
            for (Entry en : ll) {
                if (en.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        if (containsKey(key)) {
            LinkedList<Entry> ll = hashmap.get(getHash(key));
            for (Entry en : ll) {
                if (en.key.equals(key)) {
                    return en.value;
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        keyset.add(key);
        LinkedList<Entry> ll = hashmap.get(getHash(key));
        if (!containsKey(key)) {
           Entry en = new Entry(key, value);
           ll.add(en);
           size += 1;
           if ((float) size / (float) capacity > loadFactor) {
               rehash();
           }
        } else {

           for (Entry enn : ll) {
               if (enn.key.equals(key)) {
                   enn.value = value;
                   return;
               }
           }
       }      
    }
    
    private void rehash() {
        ArrayList<LinkedList<Entry>> oldHashmap = hashmap;
        capacity *= 2;
        hashmap = new ArrayList<LinkedList<Entry>>(capacity);
        for (int i = 0; i < capacity; i++) {
            hashmap.add(new LinkedList<Entry>());
        }
        for (int i = 0; i < oldHashmap.size(); i++) {
            LinkedList<Entry> ll = oldHashmap.get(i);
            for (Entry en : ll) {
                LinkedList<Entry> ll2 = hashmap.get(getHash(en.key));
                ll2.add(en);
            }
        }   
    }
    
    private int getHash(K key) {
        int index = key.hashCode() % capacity;
        if (index < 0) {
            index += capacity;
        }
        return index;
    }

    @Override
    public V remove(K key) {
        if (containsKey(key)) {
            V value = get(key);
            LinkedList<Entry> ll = hashmap.get(getHash(key));
            for (Entry en : ll) {
                if (en.key.equals(key)) {
                    ll.remove(en);
                    size = size - 1;
                }
            }
            return value;
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        if (containsKey(key)) {
            LinkedList<Entry> ll = hashmap.get(getHash(key));
            for (Entry en : ll) {
                if (en.key.equals(key) && en.value.equals(value)) {
                    ll.remove(en);
                    size = size - 1;
                    return value;
                }
            }
        }
        return null;
    }

    @Override
    public Set<K> keySet() {       
        return keyset;
    }
	
	private class Entry {
	    private K key;
	    private V value;
	    public Entry(K key, V value) {
	        this.key = key;
	        this.value = value;
	    }	   
	}
}
	