import org.junit.Test;
import static org.junit.Assert.*;

/** BSTMap test 
 */

public class BSTMapTest {
    @Test
    public void testBasic() {
        BSTMap<String, String> bm = new BSTMap<String, String>();
        bm.put("Gracias", "Dios Basado");
        assertEquals(bm.get("Gracias"), "Dios Basado");
        assertEquals(true, bm.containsKey("Gracias"));
        assertEquals(false, bm.containsKey("hey"));
        bm.printInOrder();
        bm.clear();
        System.out.println("-------");
        bm.printInOrder();
        System.out.println("-------");
    }

    @Test
    public void testSize(){
        BSTMap<Integer, String> bm = new BSTMap<Integer, String>();
        bm.put(19, "Amy");
        bm.put(12, "Jerry");
        bm.put(101, "thanks");
        bm.put(-22, "You");
        bm.put(12, "Hey");
        bm.put(1987, "thanks");
        bm.put(0, "What");
        bm.put(238, "something");
        assertEquals(7, bm.size());
        bm.printInOrder();
    }

    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(BSTMapTest.class);
    }
} 