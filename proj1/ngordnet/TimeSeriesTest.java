package ngordnet;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Collection;

/** Test the TimeSeries Class 
 */
public class TimeSeriesTest {

	@Test
	public void ConstructorTest(){
		TimeSeries<Double> ts = new TimeSeries<Double>();
        ts.put(1992, 3.6);
        ts.put(1993, 9.2);
        ts.put(1994, 15.2);
        ts.put(1995, 16.1);
        ts.put(1996, -15.7);
        
        TimeSeries<Double> tsCp = new TimeSeries<Double>(ts);
        TimeSeries<Double> tsSub = new TimeSeries<Double>(ts, 1992, 1995);
        System.out.println(ts);
        System.out.println(tsCp);
        System.out.println(tsSub);
        System.out.println();
   }

   @Test 
   public void YearsTest(){
   		TimeSeries<Double> ts = new TimeSeries<Double>();
        ts.put(1992, 3.6);
        ts.put(1993, 3.6);
        ts.put(1994, 15.2);
        ts.put(1995, 16.1);
        ts.put(1996, -15.7);
        Collection<Number> years = ts.years();
        System.out.println(years);
        System.out.println();
   }
	


/* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(TimeSeriesTest.class);    
    }
}
	