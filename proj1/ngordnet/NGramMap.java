package ngordnet;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Set;
import edu.princeton.cs.introcs.In;

public class NGramMap {

    private TreeMap<Integer, YearlyRecord> words;
    private TimeSeries<Long> totalCounts;
    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename) {
        words = new TreeMap<Integer, YearlyRecord>();
        totalCounts = new TimeSeries<Long>();
        readWords(wordsFilename);
        readTotalCounts(countsFilename);
    }

    /** read in the words file into the Map structure
     */
    private void readWords(String wordsFilename) {    
        In wordsFile = new In(wordsFilename);
        String[] wordsLines = wordsFile.readAllLines();       
        for (int i = 0; i < wordsLines.length; i++) {
            String[] eachline = wordsLines[i].split("\t");
            String word = eachline[0];
            int year = Integer.parseInt(eachline[1]);
            int count = Integer.parseInt(eachline[2]);
            YearlyRecord yr;
            if (words.containsKey(year)) {
                yr = words.get(year);
            } else {
                yr = new YearlyRecord();
            }
            yr.put(word, count);
            words.put(year, yr);           
        }   
    }
    /** read in the counts file into a TimeSeries 
     */
    private void readTotalCounts(String countsFilename) {    
        In countsFile = new In(countsFilename);
        String[] countsLines = countsFile.readAllLines();       
        for (int i = 0; i < countsLines.length; i++) {
            String[] eachline = countsLines[i].split(",");
            int year = Integer.parseInt(eachline[0]);
            long count = Long.parseLong(eachline[1]);
            totalCounts.put(year, count);         
        }   
    } 
    
    /** Returns the absolute count of WORD in the given YEAR. If the word
      * did not appear in the given year, return 0. */
    public int countInYear(String word, int year) {
        if (words.containsKey(year)) {
            YearlyRecord yr = words.get(year);
            return yr.count(word);
        } else {
            return 0;
        }   
    }

    /** Returns a defensive copy of the YearlyRecord of WORD. */
    public YearlyRecord getRecord(int year) {
        YearlyRecord yr = new YearlyRecord();
        if (words.containsKey(year)) {
            Collection<String> wordsOfYear = words.get(year).words();
            for (String word : wordsOfYear) {
                yr.put(word, words.get(year).count(word));
            }
            return yr;
        }   else {
            return null;
        }

    }

    /** Returns the total number of words recorded in all volumes. */
    public TimeSeries<Long> totalCountHistory() {
        return totalCounts;
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Integer> countHistory(String word, int startYear, int endYear) {
        TimeSeries<Integer> ts = new TimeSeries<Integer>();
        Map<Integer, Long> totalCountsSub = totalCounts.subMap(startYear, true, endYear, true);
        Set<Integer> years = totalCountsSub.keySet();
        for (int i : years) {
            int count = countInYear(word, i);
            if (count != 0) {
                ts.put(i, count);
            }
        }
        return ts;
    }

    /** Provides a defensive copy of the history of WORD. */
    public TimeSeries<Integer> countHistory(String word) {
        TimeSeries<Integer> ts = new TimeSeries<Integer>();
        Set<Integer> years = totalCounts.keySet();
        for (int i : years) {
            int count = countInYear(word, i);
            if (count != 0) {
                ts.put(i, count);
            }
        }
        return ts;
    }

    /** Provides the relative frequency of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> weightHistory(String word, int startYear, int endYear) {
        TimeSeries<Double> ts = new TimeSeries<Double>();
        ts = countHistory(word, startYear, endYear).dividedBy(totalCounts);
        return ts;
    }

    /** Provides the relative frequency of WORD. */
    public TimeSeries<Double> weightHistory(String word) {
        TimeSeries<Double> ts = new TimeSeries<Double>();
        ts = countHistory(word).dividedBy(totalCounts);
        return ts;
    }

    /** Provides the summed relative frequency of all WORDS between
      * STARTYEAR and ENDYEAR. If a word does not exist, ignore it rather
      * than throwing an exception. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words, 
                              int startYear, int endYear) {
        TimeSeries<Double> ts = new TimeSeries<Double>();
        TimeSeries<Double> tsSum = new TimeSeries<Double>();
        for (String word : words) {
            tsSum = tsSum.plus(countHistory(word, startYear, endYear));
        }
        ts = tsSum.dividedBy(totalCounts);
        return ts;
    }

    /** Returns the summed relative frequency of all WORDS. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words) {
        TimeSeries<Double> ts = new TimeSeries<Double>();
        TimeSeries<Double> tsSum = new TimeSeries<Double>();
        for (String word : words) {
            tsSum = tsSum.plus(countHistory(word));
        }
        ts = tsSum.dividedBy(totalCounts);
        return ts;
    }

    /** Provides processed history of all words between STARTYEAR and ENDYEAR as processed
      * by YRP. */
    public TimeSeries<Double> processedHistory(int startYear, int endYear,
                                               YearlyRecordProcessor yrp) {
        TimeSeries<Double> ts = new TimeSeries<Double>();
        Map<Integer, YearlyRecord> subYear = words.subMap(startYear, true, endYear, true);
        Set<Integer> years = subYear.keySet();
        for (int year : years) {
            ts.put(year, yrp.process(subYear.get(year)));
        }
        return ts;
    }

    /** Provides processed history of all words ever as processed by YRP. */
    public TimeSeries<Double> processedHistory(YearlyRecordProcessor yrp) {
        TimeSeries<Double> ts = new TimeSeries<Double>();
        Set<Integer> years = words.keySet();
        for (int year : years) {
            ts.put(year, yrp.process(words.get(year)));
        }
        return ts;
    }
}
