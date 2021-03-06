package queues;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

/**
 * A circular queue that grows as needed.
 * 
 * @author Matt Boutell and Yilun Wu and
 * @param <T>
 */
public class GrowableCircularArrayQueue<T> implements SimpleQueue<T> {
	// DONE: Declare this class to implement SimpleQueue<T>, then add the
	// missing methods (Eclipse will help).
	// DONE: The javadoc for overridden methods is in the MyQueue interface.
	// Read it!

	private static final int INITIAL_CAPACITY = 5;

	private T[] array; // the queue
	private Class<T> type;
	private int head; // the head of the queue
	private int tail; // the tail of the queue
	private int numberOfElements;
	private int length; // the max length for the current Queue

	/**
	 * Creates an empty queue with an initial capacity of 5
	 * 
	 * @param type
	 *            So that an array of this type can be constructed.
	 */
	@SuppressWarnings("unchecked")
	public GrowableCircularArrayQueue(Class<T> type) {
		this.type = type;
		// This is a workaround due to a limitation Java has with
		// constructing generic arrays.
		this.array = (T[]) Array.newInstance(this.type, INITIAL_CAPACITY);
		this.length = INITIAL_CAPACITY;
		this.head = 0;
		this.tail = 0;
		this.numberOfElements = 0;
	}

	/**
	 * Displays the contents of the queue in insertion order, with the
	 * most-recently inserted one last, in other words, not wrapped around. Each
	 * adjacent pair will be separated by a comma and a space, and the whole
	 * contents will be bounded by square brackets. See the unit tests for
	 * examples.
	 */
	@Override
	public String toString() {
		if (this.isEmpty())return "[]";
		StringBuilder ans = new StringBuilder("[");
		ans.append(this.array[head]); // the first element
		int point = (this.head + 1) % this.length; // move backward
		while (point != this.tail) {
			ans.append(", " + this.array[point]);
			point = (point + 1) % this.length;
		}
		ans.append("]"); // add the ] in the last
		return ans.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		this.array = (T[]) Array.newInstance(this.type, INITIAL_CAPACITY);
		this.head = 0;
		this.tail = 0;
		this.numberOfElements = 0;

	}

	@Override
	public void enqueue(T item) {
		if (this.numberOfElements == this.length) { // the queue is full
			@SuppressWarnings("unchecked")
			T[] newArray = (T[]) Array.newInstance(this.type, this.length * 2); // DOUBLE
																				// ARRAY
																				// SIZE
			int point = 0;
			while (!this.isEmpty()) { // copy the old to the new
				newArray[point++] = this.dequeue();
			}
			this.numberOfElements = this.length;
			this.head = 0;
			this.tail = point;
			this.array = newArray;
			this.length *= 2;

		}
		this.array[tail] = item; // add the new one into queue
		this.tail = (this.tail + 1) % this.array.length;
		this.numberOfElements++;
	}

	@Override
	public T dequeue() throws NoSuchElementException {
		// DONE
		if (this.isEmpty()) // throw the exception
			throw new NoSuchElementException();
		T tmp = this.array[this.head]; // head backward
		this.head = (this.head + 1) % this.length;
		this.numberOfElements--;
		return tmp;
	}

	@Override
	public T peek() throws NoSuchElementException {
		if (this.isEmpty())
			throw new NoSuchElementException(); // return the head element of
												// the queue
		return this.array[this.head];
	}

	@Override
	public boolean isEmpty() {
		return (this.size() == 0);
	}

	@Override
	public int size() {
		return this.numberOfElements;
	}

	@Override
	public boolean contains(T item) { // search the whole queue until we found
										// it
		if (this.isEmpty())
			return false;
		if (item.equals(this.peek()))
			return true;
		int point = (this.head + 1) % this.length;
		while (point != this.tail) {
			if (item.equals(this.array[point]))
				return true;
			point = (point + 1) % this.length;
		}
		return false;
	}

	@Override
	public String debugString() {
		return null;
	}

}
