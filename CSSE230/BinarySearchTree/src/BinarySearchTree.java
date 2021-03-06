import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;


/**
 * 
 * Implementation of most of the Set interface operations using a Binary Search Tree
 *
 * @author Matt Boutell and Yilun Wu.
 * @param <T>
 */

public class BinarySearchTree<T> {
	private BinaryNode root;
	private int version;
	// Most of you will prefer to use NULL NODES once you see how to use them.
	private final BinaryNode NULL_NODE = new BinaryNode();

	public BinarySearchTree() {
		root =NULL_NODE;
		version=0;
	}

	// For manual tests only
	void setRoot(BinaryNode n) {
		this.root = n;
	}
	
	// Not private, since we need access for manual testing.
	class BinaryNode implements Comparable<T>{
		private T data;
		private BinaryNode left;
		private BinaryNode right;
		public int height;

		public BinaryNode() {
			this.data = null;
			this.left = null;
			this.right = null;
		}

		public BinaryNode(T element) {
			this.data = element;
			this.left =NULL_NODE;
			this.right =NULL_NODE;
		}

		public T getData() {
			return this.data;
		}

		public BinaryNode getLeft() {
			return this.left;
		}


		public BinaryNode getRight() {
			return this.right;
		}

		// For manual testing
		public void setLeft(BinaryNode left) {
			this.left = left;
		}
		
		public void setRight(BinaryNode right) {
			this.right = right;
		}

		public int size() {
			if(this==NULL_NODE) return 0;
			return this.left.size()+this.right.size()+1;
		}

		public int height() {
			if (this==NULL_NODE) return -1;
			return 1+Math.max(this.left.height(), this.right.height());
		}

		public boolean containsN(T i) {
			if (this==NULL_NODE) return false;
			if (this.data.equals(i)) return true;						//find it
			return this.left.containsN(i)||this.right.containsN(i);		//find it in both children
			
		}

		public void toArrayList(ArrayList<T> ans) {
			if (this==NULL_NODE) return ;
			this.left.toArrayList(ans);
			ans.add((T) this.data);
			this.right.toArrayList(ans);
		}

		public int toArray(int i,Object[] ans) {
			if (this==NULL_NODE) return i;
			int k=this.left.toArray(i, ans);
			ans[k]=this.data;
			return this.right.toArray(k+1, ans);
		}

		public BinaryNode insert(T i) {
			if (this==NULL_NODE) return new BinaryNode(i);				//get the correct position
			if (this.compareTo(i)<0) this.right=this.right.insert(i);	
			else this.left=this.left.insert(i);
			return this;
		}

		public boolean contains(T i) {									//find it in BST
			if (this==NULL_NODE) return false;
			if (this.data.equals(i)) return true;
			if (this.compareTo(i)<0) return this.right.contains(i);		//determine find in left or right child
			return this.left.contains(i);
		}

		@Override
		public int compareTo(T arg0) {
			if ((int)this.data==(int)arg0) return 0;
			if ((int)this.data<(int)arg0) return -1;
			return 1;
		}

		public BinaryNode remove(T i) {
			if (this.compareTo(i)==0){
				if (this.left==NULL_NODE) return this.right;			//if it just has one child, return its child.
				if (this.right==NULL_NODE) return this.left;
				BinaryNode pre=this;									//record its father.
				BinaryNode tmp=this.left;
				while (tmp.right!=NULL_NODE) 							//find the largest node in left child
					{
						pre=tmp;
						tmp=tmp.right;
					}
				if (pre==this){											//the largest is its left child
					tmp.right=this.right;
					return tmp;
				}
				pre.right=tmp.left;										//common condition
				tmp.left=this.left;
				tmp.right=this.right;
				return tmp;
			}
			if (this.compareTo(i)<0) this.right=this.right.remove(i);
			else this.left=this.left.remove(i);
			return this;
			
		}

		
	}

	public int size() {
		return root==NULL_NODE?0:root.size();
	}

	public boolean isEmpty() {
		return root==NULL_NODE;
	}

	public int height() {
		return root.height();
	}

	public boolean containsNonBST(T i) {
		return root.containsN(i);
	}

	public ArrayList<T> toArrayList() {
		ArrayList<T> ans=new ArrayList<>();
		this.root.toArrayList(ans);
		return ans;
	}

	public Object[] toArray() {
		Object[] ans=new Object[this.root.size()];
		this.root.toArray(0,ans);
		return ans;
	}
	
	public String toString(){
		ArrayList<T> ans=new ArrayList<>();
		this.root.toArrayList(ans);
		return ans.toString();
		
		
	}

	public Iterator<T> inefficientIterator() {
		return new ArrayListIterator(version);
	}
	class ArrayListIterator implements Iterator<T>{
		private ArrayList<T> ar;
		private int pos;
		private int ver;
		public  ArrayListIterator(int version) {
			this.ar=toArrayList();
			this.pos=0;
			this.ver=version;
		}
		@Override
		public boolean hasNext() {
			//if the version is different with the original version version, that is changed.
			if (version!=this.ver) throw new ConcurrentModificationException();
			return pos<this.ar.size();
		}

		@Override
		public T next() {
			//if the version is different with the original version version, that is changed.
			if (version!=this.ver) throw new ConcurrentModificationException();
			if (!hasNext()){
				throw new NoSuchElementException();
			}
			pos++;
			return ar.get(pos-1);
		}
		
	}

	public Iterator<T> preOrderIterator() {
		return new preOrderIter(version);
	}
	class preOrderIter implements Iterator<T>{
		BinaryNode last;
		Stack<BinaryNode> stack;
		int ver;
		public  preOrderIter(int version) {
			this.ver=version;
			stack=new Stack<>();
			if (root!=NULL_NODE) stack.push(root);
			last=null;
		}
		@Override
		public boolean hasNext() {
			//if the version is different with the original version, that is changed. throw the exception
			if (version!=this.ver) throw new ConcurrentModificationException();
			return !stack.isEmpty();
		}

		@Override
		public T next() {
			//if the version is different with the original version , that is changed, so throw the exception
			if (version!=this.ver) throw new ConcurrentModificationException();
			if (!hasNext()){
				throw new NoSuchElementException();
			}
			BinaryNode top=stack.pop();
			//push right child then left child into the stack
			if (top.right!=NULL_NODE) stack.push(top.right);
			if (top.left!=NULL_NODE) stack.push(top.left);
			last=top;
			return top.data;
		}
		
		@Override
		public void remove(){
			//if the iter now is not point to a exist node, throw the error
			if (last==null) throw new IllegalStateException();
			BinarySearchTree.this.remove(last.data);
			last=null;
			//it changes do not affect the version, so decrease the version
			changeversion();
			
		}
		
	}
	
	public Iterator<T> iterator() {
		return new inOrderIter(version);
	}

	class inOrderIter implements Iterator<T>{
		
		BinaryNode last;
		Stack<BinaryNode> stack;
		HashSet<BinaryNode> list;
		int ver;
		public  inOrderIter(int version) {
			list=new HashSet<>();
			this.ver=version;
			stack=new Stack<>();
			if (root!=NULL_NODE) stack.push(root);
			last=null;
		}
		@Override
		public boolean hasNext() {
			//if the version is different with the original version version, that is changed.
			if (version!=this.ver) throw new ConcurrentModificationException();
			return !stack.isEmpty();
		}

		@Override
		public T next() {
			//if the version is different with the original version , that is changed.
			if (version!=this.ver) throw new ConcurrentModificationException();
			if (!hasNext()){
				throw new NoSuchElementException();
			}
			//get the peek of the stack
			BinaryNode top=stack.peek();
			//if it is the second time of visiting, pop it and push its right child.
			if (list.contains(top)){
				stack.pop();
				if (top.right!=NULL_NODE) stack.push(top.right);
				last=top;
				return top.data;
			}
			//add it to list to mark its visiting and push its left child.
			list.add(top);
			if (top.left!=NULL_NODE) stack.push(top.left);
			//continue to its left child.
			return next();		
		}
		
		public void remove(){
			//if the iter now is not point to a exist node, throw the error
			if (last==null) throw new IllegalStateException();
			BinarySearchTree.this.remove(last.data);
			last=null;
			changeversion();
		}
	}

	public boolean insert(T i) {
		if (i==null) throw new IllegalArgumentException();
		//if it contains it return false;
		if (this.contains(i)) return false;
		this.version++;
		this.root=this.root.insert(i);
		return true;
		
	}

	public boolean contains(T i) {
		return this.root.contains(i);
	}

	public boolean remove(T i) {
		if (i==null) throw new IllegalArgumentException();
		if (!this.contains(i)) return false;
		//add the version to show the action.
		this.version++;
		this.root=this.root.remove(i);
		return true;
		
	}
	//if we call remove from iter, we don't count this action.
	public void changeversion(){
		this.version--;
	}
}
