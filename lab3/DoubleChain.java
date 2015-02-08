
public class DoubleChain {
	
	private DNode head;
	private DNode tail;
	private int size;

	public int getSize(){
		int s=size;
		return s;
	}
	
	public DoubleChain(){
		size = 0;
		head = null;
		tail = null;
	}

	public DoubleChain(double val) {
		size = 1; 
		head = new DNode(null,val,null);
		tail = head;
	}

	public DNode getFront() {
		return head;
	}

	/** Returns the last item in the DoubleChain. */		
	public DNode getBack() {
		return tail;
	}
	
	/** Adds D to the front of the DoubleChain. */	
	public void insertFront(double d) {
		DNode temp = head;
		head = new DNode(null,d,temp);
		temp.prev = head;
		size = size + 1; 
	}
	
	/** Adds D to the back of the DoubleChain. */	
	public void insertBack(double d) {
		DNode temp = tail;
		tail = new DNode(temp,d,null);
		temp.next = tail;
		size = size + 1;

	}
	
	/** Removes the last item in the DoubleChain and returns it. 
	  * This is an extra challenge problem. */
	public DNode deleteBack() {
		if (size==0){
			return null;
		} else if (size==1){
			DNode temp = tail;
			size = 0;
			tail = null;
			head = null;
			return temp;
		} else{
		DNode temp = tail;
		tail = temp.prev;
		tail.next = null;
		size = size - 1;
		return temp;
		}
	}
	
	/* Returns a string representation of the DoubleChain. 
	  * This is an extra challenge problem. */
	public String toString() {
		String s = "<[";
		DNode p = head;
		while (p.next!=null){
			s = s + Double.toString(p.val);
			s = s + ", ";
			p=p.next;
		}
		s = s+ Double.toString(p.val);
		s = s + "]>";
		return s;
	}

	public static class DNode {
		public DNode prev;
		public DNode next;
		public double val;
		
		private DNode(double val) {
			this(null, val, null);
		}
		
		private DNode(DNode prev, double val, DNode next) {
			this.prev = prev;
			this.val = val;
			this.next =next;
		}
	}
	
}
