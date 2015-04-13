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

public class CommitTree implements Serializable {
	private long head;
	private Map<Long, CommitNode> idToNode;

	public CommitTree() {
		idToNode = new HashMap<Long, CommitNode>();
		head = 0; ///may be a problem.
	}

	public void addNode(String message, Set<String> deleteFiles, Set<String> addFiles) {
		CommitNode node = new CommitNode(head, message);
		long id = node.hash();
 		/*** create FileMap for this commit, mapping them to their location(which commit) */
 		long parentId = head;
 		head = id;
		Map<String, Long> fileMap;
		if (parentId == 0) {
			fileMap = new HashMap<String, Long>();
		} else {
			fileMap = idToNode.get(parentId).fileMap();
		}
		for (String file : addFiles) {
			fileMap.put(file, head);
		}
		for (String file : deleteFiles) {
			fileMap.remove(file);
		}
		node.putFileMap(fileMap);
		/****/
		idToNode.put(id, node);
	}

	public void rebaseAddNode(long idParent, long idOriginal, String message) {
		CommitNode node;
		if (message == null) {
			node = new CommitNode(idParent, getMessage(idOriginal));
		} else {
			node = new CommitNode(idParent, message);
		}
		long id = node.hash();
		head = id;
		Map<String, Long> fileMap = getFileMap(idParent);
		Map<String, Long> fileMap2 = getFileMap(idOriginal); //should overwrite fileMap
		fileMap.putAll(fileMap2);
		node.putFileMap(fileMap);
		idToNode.put(id, node);
	}

	public String getTime(long id) {
		return idToNode.get(id).time(); //immutable??
	}

	public String getMessage(long id) {
		return idToNode.get(id).message(); //immutable??
	}

	public long getParent(long id) {
		return idToNode.get(id).parent();
	}

	public long getHead() {
		return head;
	}

	public void changeHead(long id) {
		head = id;
	}

	public Set<Long> getIds() {
		return idToNode.keySet(); //immutable??
	}

	public boolean containsId(long id) {
		return idToNode.containsKey(id);
	}

	public Map<String, Long> getFileMap(long id) { //immutable?
		return idToNode.get(id).fileMap();
	}

	/** return the commit folder of the file */
	public String getOriginalCommit(long id, String filename) {
		Map<String, Long> fileMap = idToNode.get(id).fileMap();
		if (fileMap.containsKey(filename)) {
			long originalId = fileMap.get(filename);
			return String.valueOf(originalId);
		} else {
			return null;
		}
	}

	private class CommitNode implements Serializable {
		private long parent;
		private String message;
		private String time;
		private Map<String, Long> fileMap; //All files for this commit mapping to their original commit.

		public CommitNode(long parent, String message) {
			this.parent = parent;
			this.message = message;
			this.time = getTimeStamp();
			fileMap = new HashMap<String, Long>();
		}

		public String getTimeStamp() {
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
			return time;
		}

		public long hash() {
			long hashCode;
			if (parent == 0) {
				hashCode = 0;
			} else {
				Long parentObject = new Long(parent);
				hashCode = (long) parentObject.hashCode();
			}
			hashCode += (long) message.hashCode();
			hashCode += (long) time.hashCode();
			return Math.abs(hashCode);
		}

		public long parent() {
			return parent;
		}
		public String message() {
			return message;
		}
		public String time() {
			return time;
		}
		public Map<String, Long> fileMap() {
			return fileMap;
		}
		public void putFileMap(Map<String, Long> map) {
			this.fileMap = map;
		}
	}

}
	