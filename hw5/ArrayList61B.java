import java.util.AbstractList;

/* 61B implementation of ArrayList */

public class ArrayList61B<E> extends AbstractList<E>{
	private E[] eArray;
	private int number;

	public ArrayList61B(int initialCapacity){
		if (initialCapacity<1){
			throw new IllegalArgumentException();
		}
		eArray = (E[]) new Object[initialCapacity];
	}

	public ArrayList61B(){
		this(1);
	}

	public E get(int i){
		if(i<0 || i>=number){
			throw new IllegalArgumentException();
		}
		return eArray[i];
	}

	public boolean add(E item){
		if (number==eArray.length){
			E[] oldArray = eArray;
			eArray = (E[]) new Object[eArray.length*2];
			System.arraycopy(oldArray, 0, eArray, 0, number);
		}
		eArray[number] = item;
		number = number+1;
		return true;
		}
	public int size(){
		return number;
	}

	}

