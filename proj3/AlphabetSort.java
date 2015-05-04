import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

public class AlphabetSort {
	
	private Trie t;
	private String alphabet;
	private Map<Character, Integer> alphabetMap;
	private Comparator<Character> comparator;

	public AlphabetSort() {
		Scanner sc = new Scanner(System.in);
		int lineNumber = 0;
		if (sc.hasNextLine()) {
	    	alphabet = sc.nextLine();
	    }
	    if (alphabet != null) {
	    	alphabetMap = new HashMap<Character, Integer>();
	    	for (int i = 0; i < alphabet.length(); i++) {
	    		alphabetMap.put(alphabet.charAt(i), i); 
	    	} 
	    	if (alphabetMap.size() < alphabet.length()) {
	    		throw new IllegalArgumentException();
	    	}
	    }
	    this.comparator = new CharComparator();
	    t = new Trie(comparator);
	    String line;
	    while (sc.hasNextLine()) {
	    	lineNumber++;
	    	line = sc.nextLine();
	    	boolean shouldInsert = true;
	    	for (int i = 0; i < line.length(); i++) {
	    		if (!alphabetMap.containsKey(line.charAt(i))) {
	    			shouldInsert = false;
	    			break;
	    		}
	    	}	 
	    	if (shouldInsert) {
	    		t.insert(line);
	    	}
	    }
	    if (lineNumber < 1) {
	    	throw new IllegalArgumentException();
	    } 
	}

	private void print() {
		LinkedList<String> strings = t.traversal();
		for (String s : strings) {
			System.out.println(s);
		}
	}

	private class CharComparator implements Comparator<Character> {
		public int compare(Character c1, Character c2) {
			return alphabetMap.get(c1).compareTo(alphabetMap.get(c2));
		}
	}

	public static void main(String[] args) {
		AlphabetSort as = new AlphabetSort();
		as.print();
	}
}
	