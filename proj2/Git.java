import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.file.FileSystem;
import java.security.MessageDigest;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;
import java.nio.file.StandardCopyOption;

public class Git {
	private CommitTree ct;
	private Set<String> addFiles;
	private Set<String> deleteFiles;

	public Git() {
		ct = new CommitTree();
		addFiles = new HashSet<String>();
		deleteFiles = new HashSet<String>();
	}

	public void init() {
		File gitlet = new File(".gitlet");
		if (gitlet.exists()) {
			System.out.println("A gitlet version control system already exists in the current directory.");
			return;		
		} else {
			gitlet.mkdir();
			ct.addNode("initial commit", deleteFiles, addFiles);
			saveAllObject();
		}
	}

	public void add(String filename) {
		loadAllObject();
		File file = new File(filename);
		if (!file.exists()) {
			String msg = "File does not exist.";
			System.out.println(msg);
			return;
		}
		String filename2 = ct.getOriginalCommit(ct.getHead(), filename);
		if (filename2 != null) {
			filename2 = ".gitlet/" + filename2 + "/" + filename;
			if (isSameFile(filename, filename2)) {
				String msg = "File has not been modified since the last commit.";
				System.out.println(msg);
				return;
			}
		}
		addFiles.add(filename);
		if (deleteFiles.contains(filename)) {
			deleteFiles.remove(filename);
		} 
		saveAllObject();
	}

	public void commit(String message) {
		loadAllObject();
		if (addFiles.size() == 0 && deleteFiles.size() == 0) {
			printError("No changes added to the commit.");
			return;
		}
		if (message == null || message.equals("")) {
			printError("Please enter a commit message.");
			return;
		}
		ct.addNode(message, deleteFiles, addFiles);
		long head = ct.getHead();
		for (String file : addFiles) {
			File commitFolder = new File(".gitlet/" + String.valueOf(head) + "/" + file);
			commitFolder.mkdirs();
			String path = ".gitlet/" + String.valueOf(head) + "/" + file;
			copyFile(file, path);
		}
		addFiles.clear();
		deleteFiles.clear();
		saveAllObject();
	}

	public void remove(String filename) {
		loadAllObject();
		Map<String, Long> fileMap = ct.getFileMap(ct.getHead());
		if (!fileMap.containsKey(filename) && !addFiles.contains(filename)) {
			printError("No reason to remove the file.");
			return;
		}
		deleteFiles.add(filename);
		if (addFiles.contains(filename)) {
			addFiles.remove(filename);
		}
		saveAllObject();
	}

	public void log() {
		loadAllObject();
		long head = ct.getHead();
		while (head != 0) {
			printlog(head);
			head = ct.getParent(head);
		}	
	}

	public void globalLog() {
		loadAllObject();
		Set<Long> idSet = ct.getIds();
		for (Long id : idSet) {
			printlog(id);
		}
	}

	public void checkout(String filename) {
		loadAllObject();
		long head = ct.getHead();
		Map<String, Long> fileMap = ct.getFileMap(head);
		if (!fileMap.containsKey(filename)) {
			printError("File does not exist in the most recent commit, or no such branch exists.");
			return;
		}
		long id = fileMap.get(filename);
		String fromPath = ".gitlet/" + String.valueOf(id) + "/" + filename;
		copyFile(fromPath, filename);
	}

	private static void copyFile(String fromfile, String tofile) {
		File from = new File(fromfile);
		File to = new File(tofile); 
		//System.out.println(from);
		//System.out.println(to);
		try {
    		Files.copy(from.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING);
    	} catch (IOException e) {
    		printError("copy : IOException.");
    	}
	}


	private void printlog(long id) {
		System.out.println("====");
		System.out.println(id);
		System.out.println(ct.getTime(id));
		System.out.println(ct.getMessage(id));
		System.out.println();
	}

	private static void printError(String error) {
		System.out.println(error);
	}

	private static boolean isSameFile(String filename1, String filename2) {
		Path filepath1 = FileSystems.getDefault().getPath(filename1);
		Path filepath2 = FileSystems.getDefault().getPath(filename2);
		//System.out.println(filepath1);
		//System.out.println(filepath2);
		byte[] file1;
		byte[] file2;
		try {
			file1 = Files.readAllBytes(filepath1);
			file2 = Files.readAllBytes(filepath2);
		} catch (IOException e) {
			printError("reading file : IOException.");
			return false;
		}
		return Arrays.equals(file1, file2);
	}


	private static Object loadObject(String filename) {
        Object o = null;
        File file = new File(filename);
        if (file.exists()) {
            try {
                try (
                	FileInputStream fileIn = new FileInputStream(file);
                	ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                ) {
                	o = objectIn.readObject(); 
            	}
            } catch (IOException e) {
                String msg = "IOException while loading Object.";
                System.out.println(msg);
            } catch (ClassNotFoundException e) {
                String msg = "ClassNotFoundException while loading Object.";
                System.out.println(msg);
            }
        }
        return o;
    }

    private static void saveObject(Object o, String filename) {
        if (o == null) {
            return;
        }
        try {
            File file = new File(filename);
            try (
            	FileOutputStream fileOut = new FileOutputStream(file);
            	ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            ) {
            	objectOut.writeObject(o);
            }
        } catch (IOException e) {
            String msg = "IOException while saving Object.";
            System.out.println(msg);
        }
    }

    private void saveAllObject() {
    	saveObject(ct, ".gitlet/commitTree.ser");
		saveObject(addFiles, ".gitlet/addFiles.ser");
		saveObject(deleteFiles, ".gitlet/deleteFiles.ser");
    }

    private void loadAllObject() {
    	ct = (CommitTree) loadObject(".gitlet/commitTree.ser");
    	addFiles = (Set<String>) loadObject(".gitlet/addFiles.ser");
    	deleteFiles = (Set<String>) loadObject(".gitlet/deleteFiles.ser");
    }
	
}
