package ngordnet;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.TreeSet;
import java.util.Collection;


public class NGramMapTest {

	@Test
	public void countHistoryTest() {
		NGramMap ngm = new NGramMap("./ngordnet/ngrams/words_that_start_with_q.csv", 
                                    "./ngordnet/ngrams/total_counts.csv");
		TimeSeries<Integer> countHistory = ngm.countHistory("quantity");
        assertEquals(null, countHistory.get(1507));
        assertEquals(1, countHistory.get(1505).intValue());

	}

	@Test
	public void countHistoryTest2() {
		NGramMap ngm = new NGramMap("./ngordnet/ngrams/words_that_start_with_q.csv", 
                                    "./ngordnet/ngrams/total_counts.csv");
		TimeSeries<Integer> countHistory = ngm.countHistory("quantity", 1500, 2000);
        assertEquals(null, countHistory.get(1507));
        assertEquals(1, countHistory.get(1505).intValue());
	}

	@Test
	public void summedWeightHistoryTest() {
		NGramMap ngm = new NGramMap("./ngordnet/ngrams/words_that_start_with_q.csv", 
                                    "./ngordnet/ngrams/total_counts.csv");
		Collection<String> words = new TreeSet<String>();
		words.add("quantity");
		words.add("questions");
		TimeSeries<Double> swh = ngm.summedWeightHistory(words, 1900, 2000);
		double a = (107967.0+98567.0)/1266236889.0;
		assertEquals(a, swh.get(1903).doubleValue(), 10E-30);
	}

/* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(NGramMapTest.class);    
    }
}
	