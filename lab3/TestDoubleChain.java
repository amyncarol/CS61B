import static org.junit.Assert.*;

import org.junit.Test;

/** Perform tests of the DoubleChain class
 */

public class TestDoubleChain {

    /** Tests the constructor of DoubleChain */
    @Test
    public void testConstructor() {
        DoubleChain d = new DoubleChain(5);
        assertEquals(5,d.getFront().val, 1e-6);
        assertEquals(null, d.getFront().prev);
        assertEquals(null, d.getFront().next);
    }

    /** Tests some basic DoubleChain operations. */
    @Test
    public void testBasicOperations() {
        DoubleChain d = new DoubleChain(5);
        assertEquals(5, d.getFront().val, 1e-11);
        assertEquals(5, d.getBack().val, 1e-11);

        d.insertFront(2);
        d.insertFront(1);
        d.insertBack(7);
        d.insertBack(8);
        assertEquals(1, d.getFront().val, 1e-11);
        assertEquals(8, d.getBack().val, 1e-11);
    }

    @Test 
    public void testDeleteBack() {
    	DoubleChain d = new DoubleChain(5);
        d.insertBack(7);
        d.insertBack(8);
        assertEquals(8,d.getBack().val,1e-11);
        assertEquals(8,d.deleteBack().val,1e-11);
        assertEquals(7,d.getBack().val,1e-11);
    }

    @Test 
    public void testDeleteBack2() {
    	DoubleChain d = new DoubleChain(5);
    	assertEquals(1,d.getSize());
    	assertEquals(5,d.getBack().val,1e-11);
        assertEquals(5,d.deleteBack().val,1e-11);
        assertEquals(0,d.getSize());
        assertEquals(null, d.getFront());
        assertEquals(null, d.getBack());
        d.deleteBack();
        assertEquals(0,d.getSize());
        assertEquals(null, d.getFront());
        assertEquals(null, d.getBack());
    }

    @Test
    public void testToString(){
    	DoubleChain d = new DoubleChain(1.2);
    	d.insertFront(2.1);
        d.insertFront(1.77);
        d.insertBack(-7.34);
        d.insertBack(8);
        assertEquals("<[1.77, 2.1, 1.2, -7.34, 8.0]>",d.toString());
    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestDoubleChain.class);
    }
}
