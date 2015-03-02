import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;
import java.lang.Iterable;

import java.util.HashSet;
import java.util.Set;

/** ULLMapTest. You should write additional tests.
 *  @author Josh Hug
 */

public class ULLMapTest {
    @Test
    public void testBasic() {
        ULLMap<String, String> um = new ULLMap<String, String>();
        um.put("Gracias", "Dios Basado");
        assertEquals(um.get("Gracias"), "Dios Basado");
    }

    @Test
    public void testSize(){
        ULLMap<Integer, String> um = new ULLMap<Integer, String>(1, "Amy");
        um.put(12, "Jerry");
        um.put(100, "thanks");
        assertEquals(3, um.size());

        String s1 = um.remove(12);
        assertEquals(2, um.size());
        assertEquals("Jerry", s1);

        String s2 = um.remove(100, "Jerry");
        assertEquals(null, s2);
        assertEquals(2, um.size());

        String s3 = um.remove(100, "thanks");
        assertEquals("thanks", s3);
        assertEquals(1, um.size());
    }

    @Test
    public void testSet(){
        ULLMap<Integer, String> um = new ULLMap<Integer, String>(1, "Amy");
        um.put(12, "Jerry");
        um.put(100, "thanks");
        Set<Integer> umKeySet = um.keySet();
        System.out.println(umKeySet.toString());

    }
    

    @Test
    public void testIterator() {
        ULLMap<Integer, String> um = new ULLMap<Integer, String>();
        um.put(-1, "zero");
        um.put(111, "one");
        um.put(222, "two");
        Iterator<Integer> umi = um.iterator();
        System.out.println(umi.next());
        System.out.println(umi.next());
        System.out.println(umi.next());
        //System.out.println(umi.next());

    }

    @Test
    public void testInverse(){
        ULLMap<Integer, String> um = new ULLMap<Integer, String>();
        um.put(0, "zero");
        um.put(1, "one");
        um.put(3, "two");
        um.put(2, "two");
        ULLMap<String, Integer> umInverse = ULLMap.invert(um);
        Iterator<String> umInverseIterator = umInverse.iterator();
        String s = umInverseIterator.next();
        System.out.println(s + umInverse.get(s));
        s = umInverseIterator.next();
        System.out.println(s + umInverse.get(s));
        s = umInverseIterator.next();
        System.out.println(s + umInverse.get(s));

    }
    

    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(ULLMapTest.class);
    }
} 