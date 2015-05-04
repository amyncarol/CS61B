import java.util.Scanner;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

/** AlphabetSort class used to sort words according to a alphabet
 * @author yao cai@
 */
public class AlphabetSort {
    
    private Trie t;
    private String alphabet;
    private Map<Character, Integer> alphabetMap;
    private Comparator<Character> comparator;

    /** Alphabet constructor
     */
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

    /** print the words in order
     */
    private void print() {
        LinkedList<String> strings = t.traversal();
        for (String s : strings) {
            System.out.println(s);
        }
    }

    /** the customized comparator class according to alphabet
     */
    private class CharComparator implements Comparator<Character> {
        /** compare method in comparator
         * @param c1 1st character
         * @param c2 2nd character
         * @return positive if c1 after c2 in alphabet
         */
        public int compare(Character c1, Character c2) {
            return alphabetMap.get(c1).compareTo(alphabetMap.get(c2));
        }
    }

    /** main method for testing purposes
     * @param args args
     */
    public static void main(String[] args) {
        AlphabetSort as = new AlphabetSort();
        as.print();
    }
}



