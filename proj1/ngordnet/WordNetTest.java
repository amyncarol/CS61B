package ngordnet;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class WordNetTest {

	@Test
	public void constructorTest() {
		WordNet wn = new WordNet("./p1data/wordnet/synsets11.txt", "./p1data/wordnet/hyponyms11.txt");
		wn.printData();
	}

	@Test
	public void isNounTest() {
		WordNet wn = new WordNet("./p1data/wordnet/synsets11.txt", "./p1data/wordnet/hyponyms11.txt");
        assertTrue(wn.isNoun("jump"));
        assertTrue(wn.isNoun("leap"));
       	assertTrue(wn.isNoun("nasal_decongestant"));
       	assertFalse(wn.isNoun("Amy"));
	}

	@Test
	public void nounsTest() {
		WordNet wn = new WordNet("./p1data/wordnet/synsets11.txt", "./p1data/wordnet/hyponyms11.txt");
		for (String noun : wn.nouns()) {
            System.out.println(noun);
        }
        System.out.println();
	}

	@Test
	public void hyponymsTest() {
		WordNet wn2 = new WordNet("./p1data/wordnet/synsets14.txt", "./p1data/wordnet/hyponyms14.txt");
        for (String noun : wn2.hyponyms("change")) {
            System.out.println(noun);
        } 
        System.out.println();
	}

	@Test
	public void hyponymsTest2() {
		WordNet wn2 = new WordNet("./p1data/wordnet/synsets.txt", "./p1data/wordnet/hyponyms.txt");
        for (String noun : wn2.hyponyms("animal")) {
            System.out.println(noun);
        } 
        System.out.println();
	}

/* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(WordNetTest.class);    
    }
}
	