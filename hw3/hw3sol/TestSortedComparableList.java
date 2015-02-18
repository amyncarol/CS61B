import static org.junit.Assert.*;
import org.junit.Test;

/** test the SortedComparabeleList*/

public class TestSortedComparableList{


	@Test
	public void TestInsert(){
		SortedComparableList L = new SortedComparableList();
		L.insert(-1);
		L.insert(2);
		L.insert(0);
		L.insert(2);
		L.insert(-1);
		L.insert(1.5);
		System.out.println(L.toString());
	}












	 public static void main(String[] args) {
        jh61b.junit.textui.runClasses(SortedComparableList.class);
    }
}