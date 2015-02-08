
public class DoubleChain {
	
	private DNode head;
	private int size;

	public int getSize(){
		int s=size;
		return s;
	}
	
	public DoubleChain(){
		size = 0;
		head = new DNode(null,-1,null);
	}

	public DoubleChain(double val) {
		size = 1;
		head = new DNode(null,-1,null);  
		head.next = new DNode(null,val,null);
	}

	public DNode getFront() {
		return head.next;
	}

	/** Returns the last item in the DoubleChain. */		
	public DNode getBack() {
		DNode p = head;
		while (p.next!=null){
			p=p.next;
		}
		return p;
	}
	
	/** Adds D to the front of the DoubleChain. */	
	public void insertFront(double d) {
		head.next = new DNode(null,d,head.next);
		size = size + 1; 
	}
	
	/** Adds D to the back of the DoubleChain. */	
	public void insertBack(double d) {
		DNode p = head;
		while (p.next!=null){
			p=p.next;
		}
		p.next=new DNode(p,d,null);
		size = size + 1;

	}
	
	/** Removes the last item in the DoubleChain and returns it. 
	  * This is an extra challenge problem. */
	public DNode deleteBack() {
		if (size==0){
			return null;
		}
		if (size==1){
			size = size-1;
			return head.next;

		}
		DNode p = head;
		while (p.next!=null){
			p=p.next;
		}
		p.prev.next=null;
		size = size - 1;
		return p;
	}
	
	/** Returns a string representation of the DoubleChain. 
	  * This is an extra challenge problem. */
	// public String toString() {
	// 	String s = "<[";
	// 	DNode p = head;
	// 	while (p.next.next!=null){
	// 		s = s + Double.toString(p.next.val);
	// 		s = s + ", ";
	// 		p=p.next;
	// 	}
	// 	s = s+ Double.toString(p.next.val);
	// 	s = s + "]>";
	// 	return s;
	// }

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
