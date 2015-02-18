import static org.junit.Assert.*;
import org.junit.Test;

public class TestApplicableIntList {


@Test
public void TestApply(){
ApplicableIntList L = new ApplicableIntList();
L.insert(-1);
L.insert(3);
L.insert(0);
L.insert(-1);
L.insert(1);
System.out.println(L.toString());
L.apply(new square());
System.out.println(L.toString());


}











public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestApplicableIntList.class);
    }
}



