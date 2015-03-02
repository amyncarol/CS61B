import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/** ArrayList61BTest. You should write additional tests.
 *  @author Josh Hug
 */

public class ArrayList61BTest {
    @Test
    public void basicTest() {
        List<Integer> L = new ArrayList61B<Integer>();
        L.add(5);
        L.add(10);
        assertTrue(L.contains(5));        
        assertFalse(L.contains(0));
    }


    @Test
    public void testSize() {
        List<Integer> L = new ArrayList61B<Integer>(2);
        L.add(1);
        L.add(10);
        L.add(2);
        L.add(10);
        L.add(4);
        assertEquals(5, L.size());
        assertEquals((Integer) 10,L.get(3));
 
    }   

    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(ArrayList61BTest.class);
    }
}   