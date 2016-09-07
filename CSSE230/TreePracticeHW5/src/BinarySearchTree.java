import com.oracle.webservices.internal.api.databinding.Databinding.Builder;

/**
 * More Binary Tree practice problems. This problem creates BSTs of type
 * Integer: 1. Neither problem makes use of the BST ordering property; I just
 * found insert() to be a convenient way to build trees for testing. 2. I used
 * Integer instead of T since the makeTree method sets the data value of each
 * node to be a depth, which is an Integer.
 * 
 * @author Matt Boutell and wuy8
 * @param <T>
 */

/*
 * DONE: 0 You are to implement the methods below. Use recursion!
 */
public class BinarySearchTree {

	private BinaryNode root;

	private final BinaryNode NULL_NODE = new BinaryNode(null);

	public BinarySearchTree() {
		root = NULL_NODE;
	}

	/**
	 * This constructor creates a full tree of Integers, where the value of each
	 * node is just the depth of that node in the tree.
	 * 
	 * @param maxDepth
	 *            The depth of the leaves in the tree.
	 */
	public BinarySearchTree(int maxDepth) {
		// DONE: 1 Write this.
		// Hint: You may find it easier if your recursive helper method is
		// outside of the BinaryNode class.
		this.root=TreeBuild(0,maxDepth);

	}

	private BinaryNode TreeBuild(int i, int maxDepth) {
		if (i>maxDepth) return NULL_NODE;
		BinaryNode tmp=new BinaryNode(i);
		if (i==maxDepth) return tmp;
		tmp.left=TreeBuild(i+1, maxDepth);
		tmp.right=TreeBuild(i+1, maxDepth);
		return tmp;
	}

	//class to return two values in getSumOfHeights
	public class ans{
		int sum,height;
		public ans(int sum,int height){
			this.sum=sum;this.height=height;
		}
	}
	public int getSumOfHeights() {
		// DONE: 2 Write this.
		// Can you do it in O(n) time instead of O(n log n) by avoiding repeated
		// calls to height()?
		return this.root.getSumOfHeights().sum;
	}

	// These are here for testing.
	public void insert(Integer e) {
		root = root.insert(e);
	}

	/**
	 * @return A string showing an in-order traversal of nodes with extra
	 *         brackets so that the structure of the tree can be determined.
	 */
	public String toStructuredString() {
		return root.toStructuredString();
	}

	// /////////////// BinaryNode
	public class BinaryNode {

		public Integer data;
		public BinaryNode left;
		public BinaryNode right;

		public BinaryNode(Integer element) {
			this.data = element;
			this.left = NULL_NODE;
			this.right = NULL_NODE;
		}
		
		
		public ans getSumOfHeights() {
			if (this==NULL_NODE) return new ans(0, -1);					//if it is null return sum=0 and NowHeight=-1
			ans tmpleft=this.left.getSumOfHeights();					//record the sum and height of left child
			ans tmpright=this.right.getSumOfHeights();					//record the sum and height of right child
			int tmp=Math.max(tmpleft.height, tmpright.height)+1;		//get current height
			return new ans(tmpleft.sum+tmpright.sum+tmp, tmp);			//return current sum and height
		}

		public BinaryNode insert(Integer e) {
			if (this == NULL_NODE) {
				return new BinaryNode(e);
			} else if (e.compareTo(data) < 0) {
				left = left.insert(e);
			} else if (e.compareTo(data) > 0) {
				right = right.insert(e);
			} else {
				// do nothing
			}
			return this;
		}

		public String toStructuredString() {
			if (this == NULL_NODE) {
				return "";
			}
			return "[" + left.toStructuredString() + this.data
					+ right.toStructuredString() + "]";
		}

	}
}