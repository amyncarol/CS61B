import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import list.EquationList;

public class CalculatorTest {
    /* Do not change this to be private. For silly testing reasons it is public. */
    public Calculator tester;

    /**
     * setUp() performs setup work for your Calculator.  In short, we 
     * initialize the appropriate Calculator for you to work with.
     * By default, we have set up the Staff Calculator for you to test your 
     * tests.  To use your unit tests for your own implementation, comment 
     * out the StaffCalculator() line and uncomment the Calculator() line.
     **/
    @Before
    public void setUp() {
        //tester = new StaffCalculator(); // Comment me out to test your Calculator
        tester = new Calculator();   // Un-comment me to test your Calculator
    }

    // TASK 1: WRITE JUNIT TESTS
    /* write 3 tests to test addition code
     */
    @Test
    public void addTest1(){
    	assertEquals(18824185,tester.add(18735426,88759));
        assertEquals(0,tester.add(10,-10));
        assertEquals(-108,tester.add(3,-111));
    }
   

    @Test
    public void multiplyTest1(){
    	assertEquals(64449,tester.multiply(189,341));
    	assertEquals(0,tester.multiply(0,-10));
    	assertEquals(3774,tester.multiply(-34,-111));
    	assertEquals(-3774,tester.multiply(34,-111));
    }


 	@Test
 	public void saveEquationTest(){
 		EquationList temp = tester.eqList;
 		tester.saveEquation("1 + 2",3);
 		assertEquals("1 + 2",tester.eqList.equation);
 		assertEquals(3,tester.eqList.result);
 		assertEquals(temp,tester.eqList.next);
 	}

 	@Test
 	public void printHistoryTest(){
 		tester.printHistory(3);
 	}
    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(CalculatorTest.class);
    }       
}