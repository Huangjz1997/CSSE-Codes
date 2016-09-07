package editortrees;

import java.util.ArrayList;
import java.util.Stack;

// A node in a height-balanced binary tree with rank.
// Except for the NULL_NODE (if you choose to use one), one node cannot
// belong to two different trees.

public class Node {

	enum Code {
		SAME, LEFT, RIGHT;
		// Used in the displayer and debug string
		public String toString() {
			switch (this) {
			case LEFT:
				return "/";
			case SAME:
				return "=";
			case RIGHT:
				return "\\";
			default:
				throw new IllegalStateException();
			}
		}
	}

	// The fields would normally be private, but for the purposes of this class,
	// we want to be able to test the results of the algorithms in addition to
	// the
	// "publicly visible" effects

	int height;
	char data;
	Node left, right; // subtrees
	int rank; // inorder position of this node within its own subtree.
	Code bal;

	public Node() {
		this.left = null;
		this.right = null;
		this.height = -1;
		this.bal=Code.SAME;
	}

	// You will probably want to add several other methods

	public Node(char c) {
		this.data = c;
		this.rank = 0;
		this.right = EditTree.NULL_NODE;
		this.left = EditTree.NULL_NODE;
		this.bal = Code.SAME;
		this.height = 0;
	}

	// For the following methods, you should fill in the details so that they
	// work correctly
	
	//update the whole tree height
	public void updateHeight(){
		if (this==EditTree.NULL_NODE) return;
		this.left.updateHeight();
		this.right.updateHeight();
		this.height();
	}
	
	//update this node's height by O(1) 
	public int height() {
		this.height = Math.max(this.left.height, this.right.height) + 1;
		this.updateCode();
		return this.height;
	}

	//return the difference height between left and right subtree
	public int balance() {
		return this.left.height-this.right.height;

	}

	public void updateCode() {
		if (this.left.height < this.right.height)
			this.bal = Code.RIGHT;
		else if (this.left.height == this.right.height)
			this.bal = Code.SAME;
		else
			this.bal = Code.LEFT;
	}

	public int size() {
		if (this == EditTree.NULL_NODE)
			return 0;
		//rank is its size of left subtree
		return this.rank + this.right.size() + 1;
	}


	//make a same Node
	@Override
	public Node clone() {
		Node tmp = new Node(this.data);
		tmp.left = this.left;
		tmp.right = this.right;
		tmp.height = this.height;
		tmp.bal = this.bal;
		tmp.rank = this.rank;
		return tmp;
	}

	public char get(int pos) {
		//we find it 
		if (this.rank == pos)
			return this.data;
		if (this.rank > pos)
			return this.left.get(pos);
		return this.right.get(pos-this.rank-1);
	}

	public void toString(StringBuilder sb) {
		if (this == EditTree.NULL_NODE)
			return;
		this.left.toString(sb);
		sb.append(this.data);
		this.right.toString(sb);
	}

	public void debug(ArrayList<String> ar) {
		if (this == EditTree.NULL_NODE)
			return;
		String tmp = String.valueOf(this.data);
		tmp += String.valueOf(this.rank) + String.valueOf(this.bal);
		ar.add(tmp);
		this.left.debug(ar);
		this.right.debug(ar);
	}
	

}