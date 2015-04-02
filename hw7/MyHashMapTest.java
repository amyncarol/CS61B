import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MyHashMapTest {

	@Test
	public void basicTest(){
	    MyHashMap<String, String> hm = new MyHashMap<String, String>(2, (float) 0.5);
	    hm.put("hi", "yao");
	    hm.put("bybye", "123");
	    hm.put("byby", "342");
	    System.out.println(hm.keySet());
	    assertTrue(hm.containsKey("hi"));
	    assertFalse(hm.containsKey("ho"));
	    assertEquals("yao", hm.get("hi"));
	    assertEquals("yao", hm.remove("hi","yao"));
	    assertEquals(2, hm.size());
	    assertEquals("123", hm.remove("bybye"));
	    assertEquals(1, hm.size());
	    
	}


/* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(MyHashMapTest.class);    
    }
}
	


