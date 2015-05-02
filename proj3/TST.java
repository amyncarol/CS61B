import java.util.Map;
import java.util.TreeMap;
import java.util.PriorityQueue;
import java.util.LinkedList;

/**
 * Ternary Search Trie. Supports linear time find() and insert(). 
 * 
 * 
 * @author Yao Cai
 */
public class TST {
	private Node root;

	public TST() {
		root = new Node();
	}

    /** return the pq containing the first k matches and their weight, if no matches, return null or empty PQ. with 
     *  the smallest weight to be the first one
     */
    public Iterable<String> kMatches(String s, int k) {
        PriorityQueue<WordAndWeight> pqWord = kMatchHelper(s,k);
        if (pqWord == null || pqWord.size() == 0) {
            return null;
        }
        Stack<String> strings = new Stack<String>();
        while (pqWord.size() != 0) {
            strings.push(pqWord.poll().word);
        }
        LinkedList<String> kStrings = new LinkedList<String>();
        int size = strings.size();
        for (int i = 0; i < Math.min(k, size); i++) {
            kStrings.add(strings.pop());
        }
        return kStrings;
    }

    public String kMatch(String s) {
        PriorityQueue<WordAndWeight> pqWord = kMatchHelper(s,1);
        if (pqWord == null || pqWord.size() == 0) {
            return null;
        }
        Stack<String> strings = new Stack<String>();
        while (pqWord.size() != 0) {
            strings.push(pqWord.poll().word);
        }
        return strings.pop();
    }
    /** returning a pq with the head has the smallest weight
     */
    public PriorityQueue<WordAndWeight> kMatchHelper(String s, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException();
        }
        Node prefixRoot = findPrefix(s);
        if (prefixRoot == null) {
            return null;
        }
        PriorityQueue<WordAndWeight> pqWord = new PriorityQueue<WordAndWeight>();
        PriorityQueue<NodeWithWord> pqNode = new PriorityQueue<NodeWithWord>();
        NodeWithWord nw = new NodeWithWord(s, prefixRoot);
        pqNode.add(nw);
        NodeWithWord largest = pqNode.poll();
        WordAndWeight ww;
        if (largest.node.weight != null) {
            ww = new WordAndWeight(s, largest.node.weight);
            pqWord.add(ww);
        }
        if (largest.node.down != null) {
            nw = new NodeWithWord(largest.word+largest.node.down.c, largest.node.down);
            pqNode.add(nw);
        }
        while(pqNode.size() != 0) {
            if (pqWord.size() >= k && pqWord.peek().weight >= largest.node.maxSubWeight) {
                return pqWord;
            } 
            largest = pqNode.poll();
            if (largest.node.weight != null) {
                ww = new WordAndWeight(largest.word, largest.node.weight);
                pqWord.add(ww);
            }
            String whole = largest.word;
            String sub = largest.word.substring(0, largest.word.length()-1);
            if (largest.node.down != null) {
                nw = new NodeWithWord(whole+largest.node.down.c, largest.node.down);
                pqNode.add(nw);
            }
            if (largest.node.left != null) {
                nw = new NodeWithWord(sub+largest.node.left.c, largest.node.left);
                pqNode.add(nw);
            }
            if (largest.node.right != null) {
                nw = new NodeWithWord(sub+largest.node.right.c, largest.node.right);
                pqNode.add(nw);
            }
        }
        return pqWord; 
    }
    /** return the Node where the prefix ends, if prefix not exists, return null.
     */
    public Node findPrefix(String s) {
        Node currentNode = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            boolean found = false;
            while (!found) {
                if (currentNode == null) {
                    return null;
                }
                if (currentNode.c.compareTo(c) == 0) {
                    if (i == s.length()-1) {
                            return currentNode;
                    } else {
                        currentNode = currentNode.down;
                        found = true;
                    }
                } else if (currentNode.c.compareTo(c) > 0) {
                    currentNode = currentNode.left;
                } else if (currentNode.c.compareTo(c) < 0) {
                    currentNode = currentNode.right;
                }
            }
        }
        return null;
    }
    /** return the weight of a given term, if not existed, return 0.0
     */
    public double find(String s) {
    	Node currentNode = root;
    	for (int i = 0; i < s.length(); i++) {
    		char c = s.charAt(i);
            boolean found = false;
            while (!found) {
                if (currentNode == null) {
                    return 0.0;
                }
        		if (currentNode.c.compareTo(c) == 0) {
                    if (i == s.length()-1) {
                        if (currentNode.weight == null) {
                            return 0.0;
                        } else {
                            return currentNode.weight;
                        }
                    } else {
                        currentNode = currentNode.down;
                        found = true;
                    }
                } else if (currentNode.c.compareTo(c) > 0) {
                    currentNode = currentNode.left;
                } else if (currentNode.c.compareTo(c) < 0) {
                    currentNode = currentNode.right;
                }
            }
    	}
        return 0.0;
    }

    /** should be able to update maxSubWeight
    */
    public void insert(String s, double weight) {
    	if (s == null || s.length() ==0) {
    		throw new IllegalArgumentException();
    	}
    	Node currentNode = root;
    	for (int i = 0; i < s.length(); i++) {
    		char c = s.charAt(i);
            boolean inserted = false;
            while (!inserted) {
        		if (currentNode.c == null) {
                    currentNode.c = c;
                    inserted = true;
                    currentNode.maxSubWeight = weight;
                    // System.out.println("---");
                    // System.out.println(currentNode.c);
                    // System.out.println(currentNode.maxSubWeight);
                    if (i == s.length()-1) {
                        currentNode.weight = weight;
                        return;
                    }
                    if (currentNode.down == null) {
                        currentNode.down = new Node();
                    }
                    currentNode = currentNode.down;
                } else if (currentNode.c.compareTo(c) == 0) {
                    if (currentNode.maxSubWeight < weight) {
                        currentNode.maxSubWeight = weight;
                    }
                    inserted = true;
                    // System.out.println("--------");
                    // System.out.println(currentNode.c);
                    // System.out.println(currentNode.maxSubWeight);
                    if (i == s.length()-1) {
                        if (currentNode.weight != null) {
                            throw new IllegalArgumentException();
                        } else {
                            currentNode.weight = weight;
                            return;
                        }
                    }
                    if (currentNode.down == null) {
                        currentNode.down = new Node();
                    }
                    currentNode = currentNode.down;
                } else if (currentNode.c.compareTo(c) > 0) {
                    if (currentNode.left == null) {
                        currentNode.left = new Node();
                    }
                    currentNode = currentNode.left;
                } else if (currentNode.c.compareTo(c) < 0) {
                    if (currentNode.right == null) {
                        currentNode.right = new Node();
                    }
                    currentNode = currentNode.right;
                }
    		}
    	}
    }

    private class Node implements Comparable<Node> {
    	private Double weight;
    	private Double maxSubWeight;
        private Character c;
        private Node down;
        private Node left;
        private Node right;

        public Node() {
            this.c = null;
            this.weight = null;
            this.maxSubWeight = null;
            this.down = null;
            this.left = null;
            this.right = null;
        }
        public int compareTo(Node other) {
            if (this.maxSubWeight < other.maxSubWeight) {
                return 1;
            } else if (this.maxSubWeight == other.maxSubWeight) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    private class NodeWithWord implements Comparable<NodeWithWord> {
        private Node node;
        private String word;

        public NodeWithWord(String word, Node node) {
            this.node = node;
            this.word = word;
        }
        public int compareTo(NodeWithWord other) {
            return this.node.compareTo(other.node);
        }
    }

    private class WordAndWeight implements Comparable<WordAndWeight> {
        private String word;
        private Double weight;

        public WordAndWeight(String word, Double weight) {
            this.word = word;
            this.weight = weight;
        }
        public int compareTo(WordAndWeight other) {
            if (this.weight > other.weight) {
                return 1;
            } else if (this.weight == other.weight) {
                return 0;
            } else {
                return -1;
            }
        }
    }

 //    public static void main(String[] args) {
	//     TST t = new TST();
	//     t.insert("smog", 5);
	//     t.insert("buck", 10);
	//     t.insert("sad", 12);
	//     // System.out.println(t.find("hell"));
	//     // System.out.println(t.find("hello"));
	//     // System.out.println(t.find("hey"));
	//     // System.out.println(t.find("goodbye"));
	//     // System.out.println(t.find("heyy"));
	//     // System.out.println(t.find("hell"));  
 //        t.insert("spite", 20);
 //        t.insert("spit", 15);
 //        t.insert("spy", 7);
 //        t.insert("smog", 18);
 //        System.out.println(t.kMatch("sp"));
 //        // System.out.println(t.kMatch("he"));
 //        // System.out.println(t.kMatch("uu"));
	// }
}
