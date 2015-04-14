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
import java.util.Stack;
import java.util.Scanner;

public class Git {
	private CommitTree ct;
	private Set<String> addFiles;
	private Set<String> deleteFiles;
	private Map<String, HashSet<Long>> messageToId;
	private String currentBranch;
	private Map<String, Long> branchToId;

	public Git() {
		ct = new CommitTree();
		addFiles = new HashSet<String>();
		deleteFiles = new HashSet<String>();
		messageToId = new HashMap<String, HashSet<Long>>();
		currentBranch = "master";
		branchToId = new HashMap<String, Long>();
	}

	public void init() {
		File gitlet = new File(".gitlet");
		if (gitlet.exists()) {
			System.out.println("A gitlet version control system already exists in the current directory.");
			return;		
		} else {
			gitlet.mkdir();
			ct.addNode("initial commit", deleteFiles, addFiles);
			long head = ct.getHead();
			putMessageToId("initial commit", head);
			branchToId.put(currentBranch, head);
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
		if (!isValidMessage(message)) {
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
		putMessageToId(message, head);
		branchToId.put(currentBranch, head);
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

	public void find(String message) {
		loadAllObject();
		if (!messageToId.containsKey(message)) {
			printError("Found no commit with that message.");
		} else {
			Set<Long> idset = messageToId.get(message);
			for (Long id : idset) {
				System.out.println(id);
			}
		}
	}

	public void status() {
		loadAllObject();
		System.out.println("=== Branches ===");
		Set<String> branchSet = branchToId.keySet();
		for (String branch : branchSet) {
			if (currentBranch.equals(branch)) {
				System.out.println("*" + branch);
			} else {
				System.out.println(branch);
			}
		}
		System.out.println();
		System.out.println("=== Staged Files ===");
		for (String file : addFiles) {
			System.out.println(file);
		}
		System.out.println();
		System.out.println("=== Files Marked for Removal ===");
		for (String file : deleteFiles) {
			System.out.println(file);
		}
	}

	public void checkout(String filename) {
		if (!canProceed()) {
			return;
		}
		loadAllObject();
		//checkout branch
		if (branchToId.containsKey(filename)) {
			if (currentBranch.equals(filename)) {
				printError("No need to checkout the current branch.");
			} else {
				currentBranch = filename;
				long head = branchToId.get(filename);
				ct.changeHead(head);
				Map<String, Long> fileMap = ct.getFileMap(head);
				Set<String> files = fileMap.keySet();
				long id;
				String fromPath;
				for (String file : files) {
					id = fileMap.get(file);
					fromPath = ".gitlet/" + String.valueOf(id) + "/" + file;
					copyFile(fromPath, file);
				}
				saveAllObject();
				return;
			}	
		} else { //checkout file.
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
	}

	public void checkout2(long id, String filename) {
		if (!canProceed()) {
			return;
		}
		loadAllObject();
		if (!ct.containsId(id)) {
			printError("No commit with that id exists.");
			return;
		} else {
			Map<String, Long> fileMap = ct.getFileMap(id);
			if (!fileMap.containsKey(filename)) {
				printError("File does not exist in that commit.");
				return;
			}
			long originalId = fileMap.get(filename);
			String fromPath = ".gitlet/" + String.valueOf(originalId) + "/" + filename;
			copyFile(fromPath, filename);
		}
	}

	public void branch(String branchname) {
		loadAllObject();
		if (branchToId.containsKey(branchname)) {
			printError("A branch with that name already exists.");
			return;
		}
		long head = ct.getHead();
		branchToId.put(branchname, head);
		saveAllObject();
	}

	public void rmBranch(String branchname) {
		loadAllObject();
		if (!branchToId.containsKey(branchname)) {
			printError("A branch with that name does not exist.");
			return;
		} else if (currentBranch.equals(branchname)) {
			printError("Cannot remove the current branch.");
			return;
		}
		branchToId.remove(branchname);
		saveAllObject();
	}

	public void reset(long id) {
		if (!canProceed()) {
			return;
		}
		loadAllObject();
		reset2(id);
		saveAllObject();
	}

	private void reset2(long id) {		
		if (!ct.containsId(id)) {
			printError("No commit with that id exists.");
			return;
		}
		long head = id;
		ct.changeHead(head);
		branchToId.put(currentBranch, id);
		Map<String, Long> fileMap = ct.getFileMap(id);
		Set<String> files = fileMap.keySet();
		String fromPath;
		long originalId;
		for (String file : files) {
			originalId = fileMap.get(file);
			fromPath = ".gitlet/" + String.valueOf(originalId) + "/" + file;
			copyFile(fromPath, file);
		}
	}

	public void merge(String branchname) {
		if (!canProceed()) {
			return;
		}
		loadAllObject();
		if (!branchToId.containsKey(branchname)) {
			printError("A branch with that name does not exist.");
			return;
		}
		if (currentBranch.equals(branchname)) {
			printError("Cannot merge a branch with itself.");
			return;
		}
		//get split point id
		// long currentBranchParent = branchToId.get(currentBranch);
		// Set<Long> currentBranchHistory = new HashSet<Long>(currentBranchParent);
		// long branchParent = branchToId.get(branchname);
		// Set<Long> branchHistory = new HashSet<Long>(branchParent);
		// while (!currentBranchHistory.contains(branchParent) && !branchHistory.contains(currentBranchParent)) {
		// 	currentBranchParent = ct.getParent(currentBranchParent);
		// 	currentBranchHistory.add(currentBranchParent);
		// 	branchParent = ct.getParent(branchParent);
		// 	branchHistory.add(branchParent);
		// }
		// long splitPoint;
		// if (currentBranchHistory.contains(branchParent)) {
		// 	splitPoint = branchParent;
		// } else if (branchHistory.contains(currentBranchParent)) {
		// 	splitPoint = currentBranchParent;
		// }
		//get diff fileMap
		long branchId = branchToId.get(branchname);
		long currentBranchId = branchToId.get(currentBranch);
		Map<String, Long> branchfileMap = ct.getFileMap(branchId);
		Map<String, Long> currentBranchfileMap = ct.getFileMap(currentBranchId);
		Set<String> branchFiles = branchfileMap.keySet();
		long originalId;
		String fromPath;
		String conflictFile;
		long id;
		for (String file : branchFiles) {
			originalId = branchfileMap.get(file);
			fromPath = ".gitlet/" + String.valueOf(originalId) + "/" + file;
			if (!currentBranchfileMap.containsKey(file)) {
				copyFile(fromPath, file);
			} else {
				id = currentBranchfileMap.get(file);
				if (id != originalId) {
					conflictFile = file + ".conflicted";
					copyFile(fromPath, conflictFile);
				}
			}
		}
	}

	public void rebase(String basebranch, boolean interactive) {
		if (!canProceed()) {
			return;
		}
		loadAllObject();
		//failure cases
		if (!branchToId.containsKey(basebranch)) {
			printError("A branch with that name does not exist.");
			return;
		}
		if (currentBranch.equals(basebranch)) {
			printError("Cannot rebase a branch onto itself.");
			return;
		}
		long currentBranchParent = branchToId.get(currentBranch);
		long basebranchId = branchToId.get(basebranch);
		Set<Long> currentBranchHistory = new HashSet<Long>();
		currentBranchHistory.add(currentBranchParent);
		while (currentBranchParent != 0) {
			currentBranchParent = ct.getParent(currentBranchParent);
			currentBranchHistory.add(currentBranchParent);
			if (currentBranchHistory.contains(basebranchId)) {
				printError("Already up-to-date.");
				return;
			}
		}
		/*** If the current branch is in the history of the given branch, 
		   * rebase just moves the current branch to point to the same commit 
		   * that the given branch points to.
		   */
		long basebranchParent = branchToId.get(basebranch);
		long currentBranchId = branchToId.get(currentBranch);
		Set<Long> basebranchHistory = new HashSet<Long>();
		basebranchHistory.add(basebranchParent);
		while (basebranchParent != 0) {
			basebranchParent = ct.getParent(basebranchParent);
			basebranchHistory.add(basebranchParent);
			if (basebranchHistory.contains(currentBranchId)) {
				ct.changeHead(basebranchId);
				branchToId.put(currentBranch, basebranchId);
				saveAllObject();
				return;
			}
		}		
		/*** get to split point, store all the currentBranch ids till the split point in a stack
		   */
		currentBranchParent = branchToId.get(currentBranch);
		basebranchParent = branchToId.get(basebranch);
		currentBranchHistory = new HashSet<Long>();
		currentBranchHistory.add(currentBranchParent);
		basebranchHistory = new HashSet<Long>();
		basebranchHistory.add(basebranchParent);
		Stack<Long> currentBranchStack = new Stack<Long>();
		currentBranchStack.push(currentBranchParent);
		while (!currentBranchHistory.contains(basebranchParent) && !basebranchHistory.contains(currentBranchParent)) {
			currentBranchParent = ct.getParent(currentBranchParent);
			currentBranchHistory.add(currentBranchParent);
			basebranchParent = ct.getParent(basebranchParent);
			basebranchHistory.add(basebranchParent);
			currentBranchStack.push(currentBranchParent);
		}
		long currentId = currentBranchStack.pop();
		while (!currentBranchStack.empty() && !basebranchHistory.contains(currentId)) {
			currentId = currentBranchStack.pop();
		}
		/*** replay start from here
		   */
		long head = ct.getHead();
		int inChar;
		boolean isValidInput;
		long firstId = currentBranchStack.peek();
		while (!currentBranchStack.empty()) {
			currentId = currentBranchStack.pop();
			if (interactive) {
				System.out.println("Currently replaying:");
				printlog(currentId);
				isValidInput = false;
				while (!isValidInput) {
					System.out.println("Would you like to (c)ontinue, (s)kip this commit, or change this commit's (m)essage?");
					try {
						inChar = System.in.read();
					} catch (IOException e) {
						//printError("IOException");
						continue;
					}
					switch (inChar) {
						case 99: //c
							isValidInput = true;
							ct.rebaseAddNode(basebranchId, currentId, null);
							head = ct.getHead();
							putMessageToId(ct.getMessage(head), head);
							basebranchId = head;
							break;
						case 114: //s
							if (currentId != firstId && !currentBranchStack.empty()) {
								isValidInput = true;
							}
							break;
						case 109: //m
							isValidInput = true;
							String message;
							while (true) {
								System.out.println("Please enter a new message for this commit.");
								Scanner keyboard = new Scanner(System.in);
								message = keyboard.nextLine();
								if (isValidMessage(message)) {
									break;
								}
							}
							ct.rebaseAddNode(basebranchId, currentId, message);
							head = ct.getHead();
							putMessageToId(ct.getMessage(head), head);
							basebranchId = head;
							break;
						default:
							break;
					}
				}	
			} else {
				ct.rebaseAddNode(basebranchId, currentId, null);
				head = ct.getHead();
				putMessageToId(ct.getMessage(head), head);
				basebranchId = head;
			}
			branchToId.put(currentBranch, head);
			reset2(head);
			saveAllObject();
		}
	}

	private boolean canProceed() {
		boolean proceed;
		System.out.println("Warning: The command you entered may alter " +
		 "the files in your working directory. Uncommitted changes may be lost. " +
		  "Are you sure you want to continue? (yes/no)");
		Scanner keyboard = new Scanner(System.in);
		String message = keyboard.nextLine();
		if (message.equals("yes")) {
			proceed = true;
		} else {
			proceed = false;
		}
		return proceed;
	}

	private boolean isValidMessage(String message) {
		if (message == null || message.equals("")) {
			return false;
		} else {
			return true;
		}
	}

	private void putMessageToId(String message, long id) {
		HashSet<Long> idset;
		if (!messageToId.containsKey(message)) {
			idset = new HashSet<Long>();
		} else {
			idset = messageToId.get(message);
		}
		idset.add(id);
		messageToId.put(message, idset);
	}

	private static void copyFile(String fromfile, String tofile) {
		File from = new File(fromfile);
		File to = new File(tofile); 
		//System.out.println(from);
		//System.out.println(to);
		if (from.exists()) {
			try {
	    		Files.copy(from.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING);
	    	} catch (IOException e) {
	    		//printError("copy : IOException.");
	    	}
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
		saveObject(messageToId, ".gitlet/messageToId.ser");
		saveObject(currentBranch, ".gitlet/currentBranch.ser");
		saveObject(branchToId, ".gitlet/branchToId.ser");
    }

    private void loadAllObject() {
    	ct = (CommitTree) loadObject(".gitlet/commitTree.ser");
    	addFiles = (Set<String>) loadObject(".gitlet/addFiles.ser");
    	deleteFiles = (Set<String>) loadObject(".gitlet/deleteFiles.ser");
    	messageToId = (Map<String, HashSet<Long>>) loadObject(".gitlet/messageToId.ser");
    	currentBranch = (String) loadObject(".gitlet/currentBranch.ser");
    	branchToId = (Map<String, Long>) loadObject(".gitlet/branchToId.ser");
    }
	
}
