/*
 * JUnit tests for the Triangle class
 */
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author melaniecebula
 */
public class TriangleTest {
  /**  We've already created a testScalene method.  Please fill in testEquilateral, and additionally
   *   create tests for Isosceles, Negative Sides, and Invalid sides
   **/
   //test scalene 
    @Test
    public void testScalene() {
        Triangle t = new Triangle(30, 40, 50);
        String result = t.triangleType();
        assertEquals("Scalene", result);
    }
    //test equilateral
    @Test
    public void testEquilateral() {
        Triangle t = new Triangle(30, 30, 30);
        String result = t.triangleType();
        assertEquals("Equilateral", result);
    }
    //test isosceles
    @Test
    public void testIsosceles() {
        Triangle t = new Triangle(30, 30, 40);
        String result = t.triangleType();
        assertEquals("Isosceles", result);
    }
    //test if negative sides works
    @Test
    public void testNegativeSides(){
        Triangle t = new Triangle(30, -40, 30);
        String result = t.triangleType();
        assertEquals("At least one length is less than 0!", result);
    }
    //test if invalid sides works
    @Test
    public void testInvalidSides(){
        Triangle t = new Triangle(40, 5, 25);
        String result = t.triangleType();
        assertEquals("The lengths of the triangles do not form a valid triangle!", result);
    }
    //run all the above tests
    public static void main(String[] args) {
      System.exit(jh61b.junit.textui.runClasses(TriangleTest.class));
    }
}
