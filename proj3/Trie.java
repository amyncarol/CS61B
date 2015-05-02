import java.util.Map;
import java.util.TreeMap;

/**
 * Prefix-Trie. Supports linear time find() and insert(). 
 * Should support determining whether a word is a full word in the 
 * Trie or a prefix.
 * @author 
 */
public class Trie {
	private Node root;

	public Trie() {
		root = new Node();
	}

    public boolean find(String s, boolean isFullWord) {
    	Node currentNode = root;
    	for (int i = 0; i < s.length(); i++) {
    		char c = s.charAt(i);
    		if (currentNode.link.containsKey(c)) {
    			currentNode = currentNode.link.get(c);
    		} else {
    			return false;
    		}
    		if (i == s.length()-1) {
    			if (currentNode.exist == true) {
    				return true;
    			} else if (!isFullWord) {
    				return true;
    			} else {
    				return false;
    			}
    		} 
    	}
    	return false;
    }

    /** should be able to update maxSubWeight
     */
    public void insert(String s) {
    	if (s == null || s.length() ==0) {
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
    		if (i == s.length()-1) {
    			currentNode.exist = true;
    		}
    	}
    }

    private class Node {
    	private boolean exist;
    	private Map<Character, Node> link;

    	public Node() {
    		exist = false;
    		link = new TreeMap<Character, Node>();
    	}
    }

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
	}
}
