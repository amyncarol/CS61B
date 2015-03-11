package ngordnet;
import java.util.TreeMap;
import java.util.NavigableMap;
import java.util.Set;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.Collection;
import java.util.ArrayList;

public class TimeSeries<T extends Number> extends TreeMap<Integer, T> {    
    /** Constructs a new empty TimeSeries. */
    
    public TimeSeries() {
        super();
    }

    /** Returns the years in which this time series is valid. Doesn't really
      * need to be a NavigableSet. This is a private method and you don't have 
      * to implement it if you don't want to. */
    private NavigableSet<Integer> validYears(int startYear, int endYear) {
        NavigableMap<Integer, T> shortMap = this.subMap(startYear, true, endYear, true);
        return shortMap.navigableKeySet();
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR. 
     * inclusive of both end points. */
    public TimeSeries(TimeSeries<T> ts, int startYear, int endYear) {
        super(ts.subMap(startYear, true, endYear, true));
    }
    

    /** Creates a copy of TS. */
    public TimeSeries(TimeSeries<T> ts) {
        super(ts);
    }

    /** Returns the quotient of this time series divided by the relevant value in ts.
      * If ts is missing a key in this time series, return an IllegalArgumentException. */
    public TimeSeries<Double> dividedBy(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> tQuotient = new TimeSeries<Double>();
        Collection<Number> years = this.years();
        for (Number yearNumber : years) {
            int year = yearNumber.intValue();
            if (!ts.containsKey(year)) {
                throw new IllegalArgumentException();
            } else {
                double value = this.get(year).doubleValue();
                double valueTs = ts.get(year).doubleValue();
                tQuotient.put(year, value / valueTs);
            }
        }
        return tQuotient;
    }

    /** Returns the sum of this time series with the given ts. The result is a 
      * a Double time series (for simplicity). */
    public TimeSeries<Double> plus(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> tPlus = new TimeSeries<Double>();
        Collection<Number> years = this.years();
        Collection<Number> yearsTs = ts.years();
        double value, valueTs;
        years.addAll(yearsTs); //will change the map or not??
        for (Number yearNumber : years) {
            int year = yearNumber.intValue();
            if (this.containsKey(year)) {
                value = this.get(year).doubleValue();
            } else {
                value = 0;
            }
            if (ts.containsKey(year)) {
                valueTs = ts.get(year).doubleValue();
            } else {
                valueTs = 0;
            }
            tPlus.put(year, value + valueTs);
        }
        return tPlus;
    }

    /** Returns all years for this time series (in any order). */
    public Collection<Number> years() {
        Set<Integer> years = keySet();
        Collection<Number> years2 = new TreeSet<Number>();
        for (Integer year : years) {
            years2.add((Number) year);
        }
        return years2;
    //may change to Number first; 
    }

    /** Returns all data for this time series (in any order). */
    public Collection<Number> data() {
        Collection<T> data = values();
        Collection<Number> data2 = new ArrayList<Number>();
        for (T datapoint : data) {
            data2.add((Number) datapoint);
        }
        return data2;
      //may change to Number first;     
    }
}
