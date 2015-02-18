import static org.junit.Assert.*;
import org.junit.Test;

/** test the SortedComparabeleList*/

public class TestSortedComparableList {


	@Test
	public void TestConstructor(){
		SortedComparableList L = new SortedComparableList();
		assertEquals(0, L.head);
		assertEquals(null, L.tail);
	 	L = new SortedComparableList(5, L);
	 	assertEquals(5, L.head);
	 	assertEquals(0, L.tail.head);
	 	assertEquals(null, L.tail.tail);
	}

	@Test
	public void TestInsert(){
		SortedComparableList L = new SortedComparableList();
		L.insert(-1);
		L.insert(2);
		L.insert(0);
		L.insert(2);
		L.insert(-1);
		L.insert(1);
		assertEquals(-1, L.head);
		System.out.println(L.toString());
	}

	@Test
	public void TestGet() {
		SortedComparableList L = new SortedComparableList();
		L.insert(-1);
		L.insert(2);
		L.insert(0);
		L.insert(-1);
		L.insert(1);
		assertEquals(-1, L.get(0));
		assertEquals(1, L.get(4));
		assertEquals(2, L.get(5));
	}

	@Test
	public void TestExtend() {
		SortedComparableList L = new SortedComparableList();
		L.insert(-1);
		L.insert(3);
		L.insert(0);
		L.insert(-1);
		L.insert(1);
		SortedComparableList L2 = new SortedComparableList();
		L2.insert(-10);
		L2.insert(2);
		L2.insert(3);
		L.extend(L2);
		System.out.println(L.toString());
	}

	@Test
	public void TestSubTail(){
		SortedComparableList L = new SortedComparableList();
		L.insert(-1);
		L.insert(3);
		L.insert(0);
		L.insert(-1);
		L.insert(1);
		SortedComparableList L2 = SortedComparableList.subTail(L, 6);
		System.out.println(L.toString());
		assertEquals(null, L2);
	}

	@Test
	public void TestSublist(){
		SortedComparableList L = new SortedComparableList();
		L.insert(-1);
		L.insert(3);
		L.insert(0);
		L.insert(-1);
		L.insert(1);
		SortedComparableList L2 = SortedComparableList.sublist(L, 1, 1);
		System.out.println(L.toString());
		System.out.println(L2.toString());
	}

	@Test
	public void TestExpungeTail(){
		SortedComparableList L = new SortedComparableList();
		L.insert(-1);
		L.insert(3);
		L.insert(0);
		L.insert(-1);
		L.insert(1);
		System.out.println("TestExpungeTail---start");
		System.out.println(L.toString());
		SortedComparableList.expungeTail(L, 1);
		System.out.println(L.toString());
		System.out.println("TestExpungeTail---end");

	}

	@Test
	public void TestSquish(){
		SortedComparableList L = new SortedComparableList();
		L.insert(-1);
		L.insert(3);
		L.insert(0);
		L.insert(-1);
		L.insert(1);
		System.out.println("TestSquish---start");
		System.out.println(L.toString());
		L.squish();
		System.out.println(L.toString());
		System.out.println("TestSquish---end");

	}

	@Test
	public void TestTwin(){
		SortedComparableList L = new SortedComparableList();
		L.insert(-1);
		L.insert(3);
		L.insert(0);
		L.insert(-1);
		L.insert(1);
		System.out.println("TestTwin---start");
		System.out.println(L.toString());
		L.twin();
		System.out.println(L.toString());
		System.out.println("TestTwin---end");

	}

	 public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestSortedComparableList.class);
    }
}