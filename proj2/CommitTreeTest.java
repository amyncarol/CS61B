import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class CommitTreeTest {

	@Test
	public void testAddNode(){
		CommitTree ct = new CommitTree();
		Set<String> deleteFiles = new HashSet<String>();
		Set<String> addFiles = new HashSet<String>();
		ct.addNode("initial commit", deleteFiles, addFiles);
		deleteFiles.add("deletefile1");
		deleteFiles.add("deletefile2");
		addFiles.add("addfile1");
		addFiles.add("addfile2");
		ct.addNode("commit message 1", deleteFiles, addFiles);

		System.out.println(ct.getIds());
		long id = ct.getHead();
		System.out.println(ct.getTime(id));
		assertEquals("commit message 1", ct.getMessage(id));
		System.out.println(ct.getParent(id));
		System.out.println(ct.getHead());
		System.out.println(ct.getFileMap(id));
		System.out.println(ct.getOriginalCommit(id, "addfile1"));

		addFiles.remove("addfile1");
		addFiles.remove("addfile2");
		addFiles.add("addfile3");
		ct.addNode("commit message 2", deleteFiles, addFiles);

		System.out.println(ct.getIds());
		id = ct.getHead();
		System.out.println(ct.getTime(id));
		assertEquals("commit message 2", ct.getMessage(id));
		System.out.println(ct.getParent(id));
		System.out.println(ct.getHead());
		System.out.println(ct.getFileMap(id));
		System.out.println(ct.getOriginalCommit(id, "addfile1"));

	}


/* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(CommitTreeTest.class);    
    }
}
	