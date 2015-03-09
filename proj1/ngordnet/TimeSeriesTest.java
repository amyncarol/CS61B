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
   public void YearsDataTest(){
   		TimeSeries<Double> ts = new TimeSeries<Double>();
        ts.put(1992, 3.6);
        ts.put(1993, 3.6);
        ts.put(1994, 15.2);
        ts.put(1995, 16.1);
        ts.put(1996, -15.7);
        ts.put(1996, 10.9);
        Collection<Number> years = ts.years();
        Collection<Number> data = ts.data();
        System.out.println(years);
        System.out.println(data);
        System.out.println();
   }

   @Test
   public void PlusTest(){
   		TimeSeries<Double> ts = new TimeSeries<Double>();
        ts.put(1992, 3.6);
        ts.put(1993, 9.2);
        ts.put(1994, 15.2);
        ts.put(1995, 16.1);
        ts.put(1996, -15.7);
        TimeSeries<Integer> ts2 = new TimeSeries<Integer>();
        ts2.put(1991, 10);
        ts2.put(1992, -5);
        ts2.put(1993, 1);
        TimeSeries<Double> tSum = ts.plus(ts2);
        System.out.println(tSum);
        System.out.println();
   }

   @Test
   public void DividedByTest1(){
   		TimeSeries<Double> ts = new TimeSeries<Double>();
        ts.put(1992, 3.6);
        ts.put(1993, 9.2);
        ts.put(1994, 15.2);
        ts.put(1995, 16.1);
        ts.put(1996, -15.7);
        TimeSeries<Integer> ts2 = new TimeSeries<Integer>();
        ts2.put(1991, 10);
        ts2.put(1992, -5);
        ts2.put(1993, 1);
        TimeSeries<Double> tQuotient = ts.dividedBy(ts2);
   }
	
	@Test
   public void DividedByTest2(){
   		TimeSeries<Double> ts = new TimeSeries<Double>();
        ts.put(1992, 3.6);
        ts.put(1993, 9.2);
        TimeSeries<Integer> ts2 = new TimeSeries<Integer>();
        ts2.put(1992, -5);
        ts2.put(1993, 1);
        ts2.put(1991, 10);
        TimeSeries<Double> tQuotient = ts.dividedBy(ts2);
        System.out.println(tQuotient);
        System.out.println();
   }


/* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(TimeSeriesTest.class);    
    }
}
	