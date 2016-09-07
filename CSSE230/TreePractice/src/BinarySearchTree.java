/**
 * Binary Tree practice problems
 * 
 * @author Matt Boutell and <<<YOUR NAME HERE>>>. 2014.
 * @param <T>
 */

/*
 * DONE: 0 You are to implement the four methods below. I took most of them from
 * a CSSE230 exam given in a prior term. These can all be solved by recursion -
 * I encourage you to do so too, since most students find practicing recursion
 * to be more useful.
 */
public class BinarySearchTree<T extends Comparable<? super T>> {

	private BinaryNode root;

	private final BinaryNode NULL_NODE = new BinaryNode(null);

	public BinarySearchTree() {
		root = NULL_NODE;
	}

	/**
	 * This method counts the number of occurrences of positive Integers in the
	 * tree that is of type Integer. Hint: You may assume this tree contains
	 * integers, so may use a cast.
	 * 
	 * @return The number of positive integers in the tree.
	 */

	public int countPositives() {
		// DONE: 1 Write this.
		return this.root.countPositives();
	}

	/**
	 * Recall that the depth of a node is number of edges in a path from this
	 * node to the root. Returns the depth of the given item in the tree. If the
	 * item isn't in the tree, then it returns -1.
	 * 
	 * @param item
	 * @return The depth, or -1 if it isn't in the tree.
	 */
	public int getDepth(T item) {
		// DONE: 2 Write this.
		return this.root.getDepth(item);
	}

	/**
	 * This method visits each node of the BST in pre-order and determines the
	 * number of children of each node. It produces a string of those numbers.
	 * If the tree is empty, an empty string is to be returned. For the
	 * following tree, the method returns the string: "2200110"
	 * 
	 * 10 5 15 2 7 18 10
	 * 
	 * @return A string representing the number of children of each node when
	 *         the nodes are visited in pre-order.
	 */

	public String numChildrenOfEachNode() {
		// DONE: 3 Write this.
		return this.root.numChildrenOfEachNode();
	}

	/**
	 * This method determines if a BST forms a zig-zag pattern. By this we mean
	 * that each node has exactly one child, except for the leaf. In addition,
	 * the nodes alternate between being a left and a right child. An empty tree
	 * or a tree consisting of just the root are both said to form a zigzag
	 * pattern. For example, if you insert the elements 10, 5, 9, 6, 7 into a
	 * BST in that order. , you will get a zig-zag.
	 * 
	 * @return True if the tree forms a zigzag and false otherwise.
	 */
	public boolean isZigZag() {
		return this.root.isZigZag(0);
	}

	public void insert(T e) {
		root = root.insert(e);
	}

	// /////////////// BinaryNode
	public class BinaryNode {

		public T element;
		public BinaryNode left;
		public BinaryNode right;

		public BinaryNode(T element) {
			this.element = element;
			this.left = NULL_NODE;
			this.right = NULL_NODE;
		}

		// k==1 means this is the right child of his father 
		// k==-1 means this is the left child of his father
		// k==0 means this is the root
		public boolean isZigZag(int k) {
			if (this==NULL_NODE) return true;									//reach the leaf
			if ((k==1) && (this.right!=NULL_NODE)) return false;				//both right child. false
			if ((k==-1) && (this.left!=NULL_NODE)) return false;				//both left child.  false
			if ((this.left!=NULL_NODE) && (this.right!=NULL_NODE)) return false;//have two children false
			return this.left.isZigZag(-1) && this.right.isZigZag(1);			
		}

		public String numChildrenOfEachNode() {
			if (this==NULL_NODE) return "";
			int tmp=0;
			if (this.left!=NULL_NODE) tmp++;
			if (this.right!=NULL_NODE) tmp++;
			String ans=tmp+this.left.numChildrenOfEachNode()+this.right.numChildrenOfEachNode();
			return ans;
		}

		public int getDepth(T item) {
			if (this==NULL_NODE) return -1;										//cannot find the certain item
			if (this.element.compareTo(item)==0) return 0;						//we find the certain item
			int tmp=-1;															//-1 means does not find it 
			if (this.element.compareTo(item)>0) tmp=this.left.getDepth(item);	// in the left sub tree
			else tmp=this.right.getDepth(item);									// in the right sub tree
			if (tmp==-1) return -1; else return tmp+1;							// return -1 if tmp==-1 
		}

		public int countPositives() {
			if(this==NULL_NODE) return 0;
			if((int)this.element<=0) return this.right.countPositives();	//all elements in left is not positive.
			return this.left.countPositives()+1+this.right.countPositives();
		}

		//insert element e
		public BinaryNode insert(T e) {
			// get the location
			if (this == NULL_NODE) {
				return new BinaryNode(e);
			} else if (e.compareTo(element) < 0) {		//the location should be left
				left = left.insert(e);
			} else if (e.compareTo(element) > 0) {		//the location should be right
				right = right.insert(e);
			}
			return this;
		}
	}
}