package ngordnet;
import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class YearlyRecord {
    /** Creates a new empty YearlyRecord. */
    private HashMap<String, Integer> wordToCount;
    private HashMap<String, Integer> wordToRank;
    private boolean needUpdate;
    private String[] words;

    private class CountComparator implements Comparator<String> {
        public int compare(String word1, String word2) {
            return wordToCount.get(word1) - wordToCount.get(word2);
        }

    }

    private void updateWordToRank() {
        wordToRank = new HashMap<String, Integer>();
        words = new String[wordToCount.size()];
        int i = 0;
        for (String word : wordToCount.keySet()) {
            words[i] = word;
            i = i + 1;
        }

        Arrays.sort(words, new CountComparator());
        for (int j = 0; j < wordToCount.size(); j++) {
            wordToRank.put(words[j], words.length - j);
        }

    }

    public YearlyRecord() {
        wordToCount = new HashMap<String, Integer>();
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        wordToCount = new HashMap<String, Integer>(otherCountMap);
        needUpdate = true;
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
        needUpdate = true;
    }

    /** Returns the number of words recorded this year. */
    public int size() {
        return wordToCount.size();
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        Collection<String> ascendingWords = new ArrayList<String>();
        if (needUpdate) {
            updateWordToRank();
            needUpdate = false;
        }
        for (int i = 0; i < wordToRank.size(); i++) {
            ascendingWords.add(words[i]);
        }
        return ascendingWords;
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        Collection<Number> ascendingCounts = new ArrayList<Number>();
        if (needUpdate) {
            updateWordToRank();
            needUpdate = false;
        } 
        for (int i = 0; i < wordToRank.size(); i++) {
            ascendingCounts.add(wordToCount.get(words[i]));
        } 
        return ascendingCounts;
    }

    /** Returns rank of WORD. Most common word is rank 1. 
      * If two words have the same rank, break ties arbitrarily. 
      * No two words should have the same rank.
      */
    public int rank(String word) {
        if (needUpdate) {
            updateWordToRank();
            needUpdate = false;
        }
        return wordToRank.get(word);
    }
} 
