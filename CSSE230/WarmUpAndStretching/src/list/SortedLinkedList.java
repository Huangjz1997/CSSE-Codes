package list;

import java.util.ArrayList;

/**
 * 
 * @author anderson
 * 
 * @param <T>
 *            Any Comparable type
 * 
 *            A linked list whose elements are kept in sorted order.
 */
public class SortedLinkedList<T extends Comparable<T>> extends DoublyLinkedList<T> {

	/**
	 * Create an empty list
	 * 
	 */
	public SortedLinkedList() {
		super();
	}

	/**
	 * Creates a sorted list containing the same elements as the parameter
	 * 
	 * @param list
	 *            the input list
	 */
	public SortedLinkedList(DoublyLinkedList<T> list) {
		super();
		DLLIterator<T> iterator = list.iterator();
		while (iterator.hasNext()) {
			this.add(iterator.next());
		}

		// DONE: finish implementing this constructor
	}

	/**
	 * Adds the given element to the list, keeping it sorted.
	 */
	@Override
	public void add(T element) {
		Node current = this.head;
		Node prev = this.head;
		while (current.next != this.tail) {
			current = current.next;
			if (element.compareTo(current.data) < 0) {
				prev.addAfter(element);
				return;
			}
			prev = current;
		}
		prev.addAfter(element);
	}

	@Override
	public void addFirst(T element) {
		// DONE: throw UnsupportedOperationException exception
		throw new UnsupportedOperationException();

	}

	@Override
	public void addLast(T element) {
		// DONE: throw UnsupportedOperationException exception
		throw new UnsupportedOperationException();
	}
}
