import java.util.Map;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Prefix-Trie. Supports linear time find() and insert(). 
 * Should support determining whether a word is a full word in the 
 * Trie or a prefix
 * @author yao cai
 */
public class Trie {
    private Node root;
    private Comparator<Character> comparator;
    private boolean needsComparator;
    private LinkedList<String> strings;

    /** Trie constructor
     */
    public Trie() {
        needsComparator = false;
        root = new Node();
        strings = new LinkedList<String>();
    }

    /** Trie constructor with customized comparator
     * @param comparator customized comparator
     */
    public Trie(Comparator<Character> comparator) {
        needsComparator = true;
        this.comparator = comparator;
        root = new Node();
        strings = new LinkedList<String>();
    }

    /** find string s in the trie, if isFullWord is false, prefix will be ok
     * @param s string to search for
     * @param isFullWord whether full word is required
     * @return return whether s exists.
     */
    public boolean find(String s, boolean isFullWord) {
        Node currentNode = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (currentNode.link.containsKey(c)) {
                currentNode = currentNode.link.get(c);
            } else {
                return false;
            }
            if (i == s.length() - 1) {
                if (currentNode.exist) {
                    return true;
                } else if (!isFullWord) {
                    return true;
                } 
            } 
        }
        return false;
    }

    /** should be able to update maxSubWeight
     * @param s the string to be inserted
     */
    public void insert(String s) {
        if (s == null || s.length() == 0) {
            throw new IllegalArgumentException();
        }
        Node currentNode = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (currentNode.link.containsKey(c)) {
                currentNode = currentNode.link.get(c);
            } else {
                Node n = new Node();
                currentNode.link.put(c, n);
                currentNode = n;
            }
            if (i == s.length() - 1) {
                currentNode.exist = true;
            }
        }
    }

    /** traversal the Trie, return all the words in order
     * @return return the string lists containing all of the words in order
     */
    public LinkedList<String> traversal() {
        oneCharDeeper("", root);
        return strings;
    }

    /** recursive calling oneCharDeeper
     * @param s string before the node n
     * @param n current node n
     */
    private void oneCharDeeper(String s, Node n) {
        if (n == null) {
            return;
        }
        if (n.exist) {
            strings.add(s);
        } 
        for (Character c : n.link.keySet()) {
            oneCharDeeper(s + c.toString(), n.link.get(c));
        }
    }

    /** Node class of Trie
     */
    private class Node {
        private boolean exist;
        private Map<Character, Node> link;

        /** constructor of Node
         */
        public Node() {
            exist = false;
            if (needsComparator) {
                link = new TreeMap<Character, Node>(comparator);
            } else {
                link = new TreeMap<Character, Node>();
            }
        }
    }

    /** main method for testing
     * @param args argument from StdIn
     */
    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("hello");
        t.insert("hey");
        t.insert("goodbye");
        System.out.println(t.find("hell", false));
        System.out.println(t.find("hello", true));
        System.out.println(t.find("good", false));
        System.out.println(t.find("bye", false));
        System.out.println(t.find("heyy", false));
        System.out.println(t.find("hell", true));  
        System.out.println(t.traversal());
    }
}
