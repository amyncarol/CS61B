package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void TestQueue() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3.1);
        assertEquals(1, arb.peek(), 1e-10);
        assertEquals(1, arb.dequeue(), 1e-10);
        assertEquals(2, arb.peek(), 1e-10);  
        assertEquals(2, arb.dequeue(), 1e-10);
        arb.enqueue(5.1);
        assertEquals(3.1, arb.peek(), 1e-10);
        assertEquals(3.1, arb.dequeue(), 1e-10);
        assertEquals(5.1, arb.peek(), 1e-10);
        assertEquals(5.1, arb.dequeue(), 1e-10);
    }

    @Test
    public void TestExceptions() {
        ArrayRingBuffer arb = new ArrayRingBuffer(4);
        //arb.peek();
        //double x = arb.dequeue();       
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3.1);
        arb.enqueue(5.1);
        arb.enqueue(10);
    }

    // @Test
    // public void TestArray(){
    //     double[] array = new double[10];
    //     assertEquals(0, array[1], 1e-10);
    // }


    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 