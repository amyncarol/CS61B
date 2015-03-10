package ngordnet;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Set;

public class YearlyRecord2 {
    /** Creates a new empty YearlyRecord. */
    private HashMap<String, Integer> wordToCount;
    private TreeMap<Integer, TreeSet<String>> countToWord;
    private HashMap<String, Integer> wordToRank;

    public YearlyRecord2() {
        wordToCount = new HashMap<String, Integer>();
        countToWord = new TreeMap<Integer, TreeSet<String>>();
        wordToRank = new HashMap<String, Integer>();

    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord2(HashMap<String, Integer> otherCountMap) {
        wordToCount = new HashMap<String, Integer>(otherCountMap);
        countToWord = new TreeMap<Integer, TreeSet<String>>();
        Set<String> words = wordToCount.keySet();
        for (String word : words) {
            Integer count = wordToCount.get(word);
            TreeSet<String> sameCountWords = new TreeSet<String>();
            if (countToWord.containsKey(count)) {
                sameCountWords.addAll(countToWord.get(count));
            }
            sameCountWords.add(word);
            countToWord.put(count, sameCountWords);
        }
    }


    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        if (wordToCount.containsKey(word)) {
           return wordToCount.get(word);
        } else {
            return 0;
        }
    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        wordToCount.put(word, count);
        TreeSet<String> sameCountWords = new TreeSet<String>();
        if (countToWord.containsKey(count)) {
            sameCountWords.addAll(countToWord.get(count));
        }
        sameCountWords.add(word);
        countToWord.put(count, sameCountWords);
    }

    /** Returns the number of words recorded this year. */
    public int size() {
        return wordToCount.size();
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        Collection<String> ascendingWords = new ArrayList<String>();
        Set<Integer> counts = countToWord.keySet();
        for (Integer count : counts ) {
            ascendingWords.addAll(countToWord.get(count));
        }
        return ascendingWords;
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        Collection<Number> ascendingCounts = new ArrayList<Number>();
        Set<Integer> counts = countToWord.keySet();
        for (Integer count : counts ) {
            TreeSet<String> sameCountWords = countToWord.get(count);
            for (int i=0; i<sameCountWords.size(); i++) {
                ascendingCounts.add(count);
            }
        }
        return ascendingCounts;
    }

    /** Returns rank of WORD. Most common word is rank 1. 
      * If two words have the same rank, break ties arbitrarily. 
      * No two words should have the same rank.
      */
    public int rank(String word) {
        int i = 0;
        Integer count = wordToCount.get(word);
        Map<Integer, TreeSet<String>> tailMap = countToWord.tailMap(count, false);
        Set<Integer> subCounts = tailMap.keySet();
        for (Integer subCount : subCounts) {
            i = i + tailMap.get(subCount).size();
        }
        TreeSet<String> sameCountWords = countToWord.get(count);
        for (String someWord : sameCountWords) {
            while (!someWord.equals(word)) {
                i = i+1;
            }
        }
        return i+1;
    }
} 