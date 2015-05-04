import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class AlphabetSortOld {
	
	private static List<String> inText = new ArrayList<String>();
	private static String alphabet;
	private static int maxLength = 0;

	private static void readText() {
		Scanner sc = new Scanner(System.in);
		int lineNumber = 0;
		if (sc.hasNextLine()) {
	    	alphabet = sc.nextLine();
	    }
	    String line;
	    while (sc.hasNextLine()) {
	    	lineNumber++;
	    	line = sc.nextLine();
	    	inText.add(line);
			maxLength = Math.max(maxLength, line.length());
	    }
	    if (lineNumber < 1) {
	    	throw new IllegalArgumentException();
	    } else if (alphabet != null) {
	    	Set alphabetSet = new HashSet<Character>();
	    	for (int i = 0; i < alphabet.length(); i++) {
	    		alphabetSet.add(alphabet.charAt(i)); 
	    	} 
	    	if (alphabetSet.size() < alphabet.length()) {
	    		throw new IllegalArgumentException();
	    	}
	    }

	}

	private static void countingSort(int whichDigit) {
		ArrayList<String> sortedText = new ArrayList<String>(inText);
		int inTextSize = inText.size();
		int alphabetSize = alphabet.length()+2;
		int[] count = new int[alphabetSize];
		int[] position = new int[alphabetSize];
		int index;
		int index2;
		for (int i = 0; i < inTextSize; i++) {
			String line = inText.get(i);
			if (line.length() <= whichDigit) {
				count[0]++;
			} else {
				char c = line.charAt(whichDigit);
				String s = "" + c;
				if (!alphabet.contains(s)) {
					index = alphabetSize-1;
				} else {
					index = alphabet.indexOf(c)+1;
				}
				count[index]++;
			}
		}
		position[0] = 0;
        for (int j = 1; j < alphabetSize; j++) {
            position[j] = position[j-1] + count[j-1];
        }
        for (int i = 0; i < inTextSize; i++) {
        	String line = inText.get(i);
			if (line.length() <= whichDigit) {
				index = 0;
			} else {
        		char c = line.charAt(whichDigit);
        		String s = "" + c;
				if (!alphabet.contains(s)) {
					index = alphabetSize-1;
				} else {
					index = alphabet.indexOf(c)+1;
				}
        	}
        	index2 = position[index];
        	sortedText.set(index2, line);
        	position[index]++; 
        }
        for (int i = 0; i<count[alphabetSize-1]; i++) {
        	sortedText.remove(position[alphabetSize-1]-1-i);
        }
        inText = sortedText;
	}

	private static void radixSort() {
		for (int i = 0; i < maxLength; i++) {
			countingSort(maxLength-i-1);
		}
	}

	private static void print() {
		for (int i = 0; i < inText.size(); i++) {
			System.out.println(inText.get(i));
		}
	}

	public static void main(String[] args) {
		readText();
		radixSort();
		print();
	}
}
	