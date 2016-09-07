package priorityQueue;

import java.util.ArrayList;

/**
 * An implementation of the Priority Queue interface using an array list.
 * 
 * @author Matt Boutell and <<Yilun Wu>>>. Created Mar 29, 2014.
 * 
 * @param <T>
 *            Generic type of PQ elements
 */
public class ArrayListMinPQ<T extends Comparable<T>> {
	// Requirement: You must use this instance variable without changes.
	private ArrayList<T> items;

	public ArrayListMinPQ() {
		items = new ArrayList<T>();
	}

	public T findMin() {
		// This is also known as peekMin
		// DONE: implement
		if (this.isEmpty())
			return null;
		return this.items.get(0);
	}

	public T deleteMin() {
		// DONE: implement
		if (this.isEmpty())
			return null;
		return this.items.remove(0);
	}

	public void insert(T item) {
		// DONE: implement

		if (this.isEmpty()) {
			this.items.add(item);
			return;
		}
		// binary search to get the location
		int l = 0;
		int r = this.size() - 1;
		int m = 0;
		if (item.compareTo(items.get(r)) > 0) {
			items.add(item);
			return;
		}
		if (item.compareTo(items.get(l)) < 0) {
			items.add(0, item);
			return;
		}
		while (l <= r) {
			m = (l + r) / 2;
			if (item.compareTo(items.get(m)) < 0)
				r = m - 1;
			if (item.compareTo(items.get(m)) > 0)
				l = m + 1;
		}
		if (m < l)
			items.add(m + 1, item);
		if (m > r)
			items.add(m, item);
	}

	public int size() {
		// DONE: implement
		return this.items.size();
	}

	public boolean isEmpty() {
		// DONE: implement
		return this.items.isEmpty();
	}

	public void clear() {
		// DONE: implement
		items.clear();
	}
}
