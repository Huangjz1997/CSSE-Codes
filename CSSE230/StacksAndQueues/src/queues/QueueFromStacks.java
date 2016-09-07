package queues;

import java.util.NoSuchElementException;
import java.util.Stack;

public class QueueFromStacks<T> implements SimpleQueue<T> {

	Stack<T> aStack = new Stack<>();
	Stack<T> bStack = new Stack<>();
	Stack<T> cStack = new Stack<>();

	// we use two stack two save datas.

	// when it enqueue we push it into aStack
	public void enqueue(T i) {
		aStack.push(i);
	}

	// this.size is the sum of two stacks
	public int size() {
		return (aStack.size() + bStack.size());
	}

	// when it dequeue, it pop the bstack, if bstack is empty, we pop all
	// elements of aStack to bStack
	public T dequeue() {
		if (this.isEmpty())
			throw new NoSuchElementException();
		if (!bStack.isEmpty())
			return bStack.pop();
		while (!aStack.isEmpty()) {
			bStack.push(aStack.pop());
		}
		return bStack.pop();
	}

	// same as dequeue with out really pop
	public T peek() {
		if (this.isEmpty())
			throw new NoSuchElementException();
		if (!bStack.isEmpty())
			return bStack.peek();
		while (!aStack.isEmpty())
			bStack.push(aStack.pop());
		return bStack.peek();
	}

	public boolean isEmpty() {
		return (aStack.isEmpty() && bStack.isEmpty());
	}

	public boolean contains(T element) {
		return (aStack.contains(element) || bStack.contains(element));
	}

	public void clear() {
		aStack.clear();
		bStack.clear();

	}

	// we use cStack as cache. first pop bStack to cStack then pop aStack to
	// cStack.
	// as it going, we add elements into String.
	// finally. pop all elements in cStack into bStack
	public String toString() {
		if (this.isEmpty())
			return "[]";
		cStack.clear();
		StringBuilder ans = new StringBuilder("[");
		Boolean flag = false;
		while (!this.isEmpty()) {
			if (flag)
				ans.append(", ");
			flag = true;
			ans.append(this.peek());
			cStack.push(this.dequeue());
		}
		while (!cStack.isEmpty()) {
			bStack.push(cStack.pop());
		}
		return ans.toString()+"]";
	}

	public String debugString() {
		return null;
	}

}
