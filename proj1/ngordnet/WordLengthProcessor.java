package ngordnet;
import java.util.Collection;

public class WordLengthProcessor implements YearlyRecordProcessor {

	public double process(YearlyRecord yearlyRecord) {
		double wl;
		long totalCount = 0;
		long totalLength = 0;
		Collection<String> words = yearlyRecord.words();
		for (String word : words) {
			int count = yearlyRecord.count(word);
			totalCount += count;
			totalLength += word.length()*count;
		}
		wl = ((double) totalLength)/((double) totalCount);
		return wl;
	}



}