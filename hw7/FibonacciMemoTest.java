import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class FibonacciMemoTest {

	@Test
	public void basicTest(){
		System.out.println(FibonacciMemo.why47());
		assertEquals(0,FibonacciMemo.fibMemo(0));
		assertEquals(1,FibonacciMemo.fibMemo(1));
		assertEquals(1836311903,FibonacciMemo.fibMemo(46));
		System.out.println(FibonacciMemo.fibMemo(47));
		System.out.println(FibonacciMemo.fibMemo(300));
		System.out.println(FibonacciMemo.fibMemo(8000));

	
		
	
		// 46th Fibonacci = 1,836,311,903
        // 47th Fibonacci = 2,971,215,073
       
	}


/* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(FibonacciMemoTest.class);    
    }
}
	