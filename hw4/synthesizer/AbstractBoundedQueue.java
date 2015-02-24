package synthesizer;

/** AbstractBoundedQueue is an abstract class that implements BoundedQueue
 */

public abstract class AbstractBoundedQueue implements BoundedQueue{

	protected int fillCount; //the number of items in the queue
	protected int capacity;  //the capacity of the queue

	public int capacity() {   //get the capacity of the queue
		return capacity;
	}

	public int fillCount() {
		return fillCount;    //get the number of items in the queue
	}
	

	public boolean isEmpty(){  //return true if the queue is empty
		if (fillCount()==0){
			return true;
		} else {
			return false;
		}
	}

	public boolean isFull(){ //return true if the queue is full
		if(fillCount()==capacity()){
			return true;
		} else {
			return false;
		}
	}

}
