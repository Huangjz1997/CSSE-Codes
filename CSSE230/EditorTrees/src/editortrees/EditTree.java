package editortrees;

import java.util.ArrayList;

// A height-balanced binary tree with rank that could be the basis for a text editor.

public class EditTree {
	public static Node NULL_NODE = new Node();
	private Node root;
	public int numOfRotation;

	/**
	 * Construct an empty tree
	 */
	public EditTree() {
		this.root = NULL_NODE;
	}

	public EditTree(Node root) {
		this.root = root;
		this.root.updateHeight();
	}

	/**
	 * Construct a single-node tree whose element is c
	 * 
	 * @param c
	 */
	public EditTree(char c) {
		this.root = new Node(c);
	}

	/**
	 * Create an EditTree whose toString is s. This can be done in O(N) time,
	 * where N is the length of the tree (repeatedly calling insert() would be
	 * O(N log N), so you need to find a more efficient way to do this.
	 * 
	 * @param s
	 */
	public EditTree(String s) {
		this();
		if (s.length() == 0)
			return;
		this.root = buildTreebyString(s);
		this.root.updateHeight();
	}

	// Each time divide s equally into two parts.
	public Node buildTreebyString(String s) {
		int l = s.length();
		if (l == 0)
			return NULL_NODE;

		// get the mid of the string be the root
		Node tmp = new Node(s.charAt(l / 2));
		tmp.rank = l / 2;
		if (l == 1)
			return tmp;
		tmp.left = buildTreebyString(s.substring(0, l / 2));
		tmp.right = buildTreebyString(s.substring(l / 2 + 1, l));
		return tmp;
	}

	/**
	 * Make this tree be a copy of e, with all new nodes, but the same shape and
	 * contents.
	 * 
	 * @param e
	 */
	public EditTree(EditTree e) {
		this.root = cloneTree(e.root);

	}

	// helper method of the (EditTree e)
	public Node cloneTree(Node node) {
		if (node == NULL_NODE)
			return NULL_NODE;
		// clone current node
		Node tmp = node.clone();
		// recursion
		tmp.left = cloneTree(node.left);
		tmp.right = cloneTree(node.right);
		return tmp;
	}

	/**
	 * 
	 * @return the height of this tree
	 */
	public int height() {
		return this.root.height;
	}

	/**
	 * 
	 * returns the total number of rotations done in this tree since it was
	 * created. A double rotation counts as two.
	 *
	 * @return number of rotations since tree was created.
	 */
	public int totalRotationCount() {
		return this.numOfRotation; // replace by a real calculation.
	}

	/**
	 * return the string produced by an inorder traversal of this tree
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		this.root.toString(sb);
		return sb.toString();
	}

	/**
	 * This one asks for more info from each node. You can write it like the
	 * arraylist-based toString() method from the BST assignment. However, the
	 * output isn't just the elements, but the elements, ranks, and balance
	 * codes. Former CSSE230 students recommended that this method, while making
	 * it harder to pass tests initially, saves them time later since it catches
	 * weird errors that occur when you don't update ranks and balance codes
	 * correctly. For the tree with node b and children a and c, it should
	 * return the string: [b1=, a0=, c0=] There are many more examples in the
	 * unit tests.
	 * 
	 * @return The string of elements, ranks, and balance codes, given in a
	 *         pre-order traversal of the tree.
	 */
	public String toDebugString() {
		ArrayList<String> ar = new ArrayList<>();
		// StringBuilder sb = new StringBuilder();
		this.root.debug(ar);
		return ar.toString();
	}

	/**
	 * 
	 * @param pos
	 *            position in the tree
	 * @return the character at that position
	 * @throws IndexOutOfBoundsException
	 */
	public char get(int pos) throws IndexOutOfBoundsException {
		if (pos < 0 || pos >= this.root.size())
			throw new IndexOutOfBoundsException();
		return this.root.get(pos);

	}

	/**
	 * 
	 * @param c
	 *            character to add to the end of this tree.
	 */
	public void add(char c) {
		// add c into the rightest of the tree
		add(c, this.size());
	}

	/**
	 * 
	 * @param c
	 *            character to add
	 * @param pos
	 *            character added in this inorder position
	 * @throws IndexOutOfBoundsException
	 *             id pos is negative or too large for this tree
	 */
	public void add(char c, int pos) throws IndexOutOfBoundsException {
		if (pos < 0 || pos > this.size())
			throw new IndexOutOfBoundsException();
		this.root = this.insert(this.root, c, pos);
	}

	// helper method of add
	private Node insert(Node root, char c, int pos) {
		// if root==NULL_NODE we just add the new node on the leaf of the tree
		if (root == NULL_NODE)
			return new Node(c);
		// should insert it into left subtree;
		if (root.rank >= pos) {
			root.left = insert(root.left, c, pos);
			root.rank++;
		} else if (root.rank < pos)
			root.right = insert(root.right, c, pos - root.rank - 1);

		// update the height by O(1)
		root.height();
		// if the current node is not balance, rotate it.
		if (Math.abs(root.balance()) > 1)
			return rotation(root);

		return root;
	}

	private Node rotation(Node root) {

		// left is deeper
		if (root.balance() > 0) {
			if (root.left.balance() >= 0) {
				// left child's left is deeper Single Right rotation
				return right_rotation(root);
			}

			if (root.left.balance() < 0) {
				// left child's right is deeper Double Right rotation
				root.left = left_rotation(root.left);
				return right_rotation(root);
			}
		}

		// right is deeper
		if (root.balance() < 0) {
			if (root.right.balance() <= 0) {
				// right child's right is deeper Single Left rotation
				return left_rotation(root);
			}

			if (root.right.balance() > 0) {
				// right child's left is deeper Double Left rotation
				root.right = right_rotation(root.right);
				return left_rotation(root);
			}
		}
		// Impossible to reach here
		return new Node();
	}

	private Node left_rotation(Node root) {
		this.numOfRotation++;
		Node child = root.right;
		// change the rank
		child.rank = root.rank + child.rank + 1;
		// change the relationship
		root.right = child.left;
		child.left = root;
		// update their heights
		root.height();
		child.height();
		return child;
	}

	private Node right_rotation(Node root) {
		this.numOfRotation++;
		Node child = root.left;
		root.rank = root.rank - child.rank - 1;
		// change the relationship
		root.left = child.right;
		child.right = root;
		// update their heights
		root.height();
		child.height();
		return child;
	}

	/**
	 * 
	 * @return the number of nodes in this tree
	 */
	public int size() {
		return this.root.size();
	}

	/**
	 * 
	 * @param pos
	 *            position of character to delete from this tree
	 * @return the character that is deleted
	 * @throws IndexOutOfBoundsException
	 */
	public char delete(int pos) throws IndexOutOfBoundsException {
		// Implementation requirement:
		// When deleting a node with two children, you normally replace the
		// node to be deleted with either its in-order successor or predecessor.
		// The tests assume assume that you will replace it with the
		// *successor*.
		if (pos < 0 || pos >= this.size())
			throw new IndexOutOfBoundsException();

		// use ans[] to return the delete char
		char[] ans = new char[2];
		this.root = this.deleteNode(this.root, pos, ans);
		return ans[0];
	}

	// helper method of delete
	public Node deleteNode(Node root, int pos, char[] ans) {
		if (root == NULL_NODE)
			return NULL_NODE;

		// we found the node
		if (root.rank == pos) {
			// record the char
			ans[0] = root.data;
			// if it just has one child or no child.
			if (root.left == NULL_NODE)
				return root.right;
			if (root.right == NULL_NODE)
				return root.left;
			// it has two children, we need to find the smallest node in its
			// right subtree
			root.right = delete(root.right, root);
		} else
			// in the left subtree
			if (root.rank > pos) {
			root.rank--;
			root.left = deleteNode(root.left, pos, ans);
		}
		// in the right sub tree
		else if (root.rank < pos)
			root.right = deleteNode(root.right, pos - root.rank - 1, ans);

		// update height and rotate if it is unbalanced
		root.height();
		if (Math.abs(root.balance()) > 1)
			return rotation(root);
		return root;
	}

	// find the most left node
	private Node delete(Node root, Node original) {

		// we find it
		if (root.left == NULL_NODE) {
			// change the char
			original.data = root.data;
			return root.right;
		}
		// find it in left subtree
		root.rank--;
		root.left = delete(root.left, original);

		// update height and rotate it if needed
		root.height();
		if (Math.abs(root.balance()) > 1)
			return rotation(root);
		return root;
	}

	/**
	 * This method operates in O(length*log N), where N is the size of this
	 * tree.
	 * 
	 * @param pos
	 *            location of the beginning of the string to retrieve
	 * @param length
	 *            length of the string to retrieve
	 * @return string of length that starts in position pos
	 * @throws IndexOutOfBoundsException
	 *             unless both pos and pos+length-1 are legitimate indexes
	 *             within this tree.
	 */
	public String get(int pos, int length) throws IndexOutOfBoundsException {
		if (pos < 0 || pos > this.size() || pos + length - 1 < 0 || pos + length - 1 > this.size())
			throw new IndexOutOfBoundsException();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(this.get(pos + i));
		}
		return sb.toString();
	}

	/**
	 * This method is provided for you, and should not need to be changed. If
	 * split() and concatenate() are O(log N) operations as required, delete
	 * should also be O(log N)
	 * 
	 * @param start
	 *            position of beginning of string to delete
	 * 
	 * @param length
	 *            length of string to delete
	 * @return an EditTree containing the deleted string
	 * @throws IndexOutOfBoundsException
	 *             unless both start and start+length-1 are in range for this
	 *             tree.
	 */
	public EditTree delete(int start, int length) throws IndexOutOfBoundsException {
		if (start < 0 || start + length >= this.size())
			throw new IndexOutOfBoundsException(
					(start < 0) ? "negative first argument to delete" : "delete range extends past end of string");
		EditTree t2 = this.split(start);
		EditTree t3 = t2.split(length);
		this.concatenate(t3);
		return t2;
	}

	/**
	 * Don't worry if you can't do this one efficiently.
	 * 
	 * @param s
	 *            the string to look for
	 * @return the position in this tree of the first occurrence of s; -1 if s
	 *         does not occur
	 */
	public int find(String s) {
		return this.findHelper(s, 0);
	}

	/**
	 * 
	 * @param s
	 *            the string to search for
	 * @param pos
	 *            the position in the tree to begin the search
	 * @return the position in this tree of the first occurrence of s that does
	 *         not occur before position pos; -1 if s does not occur
	 */
	public int find(String s, int pos) {
		return this.findHelper(s, pos);
	}

	public int findHelper(String s, int pos) {
		if (s.isEmpty())
			return 0;
		String str = this.toString();
		for (int i = pos; i < this.size(); i++) {
			if (str.charAt(i) == s.charAt(0)) {
				if (str.substring(i, i + s.length()).equals(s)) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Append (in time proportional to the log of the size of the larger tree)
	 * the contents of the other tree to this one. Other should be made empty
	 * after this operation.
	 * 
	 * @param other
	 * @throws IllegalArgumentException
	 *             if this == other
	 */
	public void concatenate(EditTree other) throws IllegalArgumentException {
		// If two trees are the same, throw exception.
		if (this.equals(other))
			throw new IllegalArgumentException();
		other.root.updateHeight();
		if (other.root == NULL_NODE)
			return;
		// If the tree is empty, set it equals to the other tree.
		if (this.root == NULL_NODE) {
			this.root = other.root;
			this.root.updateHeight();
			other.root = NULL_NODE;
			return;
		}
		// Paste other tree and tree to the node q which deleted from one of the
		// tree.
		if (other.root.height > this.root.height) {
			Node q = new Node(this.delete(this.size() - 1));
			other.root = paste(this.root, q, other.root, -1);
			this.root = other.root;
			other.root = NULL_NODE;
			return;
		}
		Node q = new Node(other.delete(0));
		this.root = paste(this.root, q, other.root, 1);
		other.root = NULL_NODE;
	}
	
	// Paste T and V as q.left and q.right. Delta represent the direction of
	// paste(either right or left).
	public Node paste(Node T, Node q, Node V, int delta) {
		if ((T.height == V.height) || (V.height + delta == T.height)) {
			q.left = T;
			q.right = V;
			q.height();
			q.rank = q.left.size();
			return q;
		}
		if (delta == 1) {
			T.right = paste(T.right, q, V, delta);
			T.height();
			if (Math.abs(T.balance()) > 1)
				return rotation(T);
			return T;
		} else {
			V.left = paste(T, q, V.left, delta);
			V.height();
			if (Math.abs(V.balance()) > 1)
				return rotation(V);
			return V;
		}
	}

	// Split a tree into two trees in position pos.
	private void split(int pos, Node[] s, Node[] t, Node root) {
		if (root.rank == pos) {
			s[0] = root.left;
			if (root.right == NULL_NODE)
				t[0] = new Node(root.data);
			else
				t[0] = this.insert(root.right, root.data, 0);
			return;
		}
		if (root.rank > pos) {
			split(pos, s, t, root.left);
			t[0] = merge(t[0], root, root.right);
			return;
		}
		split(pos - root.rank - 1, s, t, root.right);
		s[0] = merge(root.left, root, s[0]);
	}

	// Combine the two trees.
	private Node merge(Node a, Node b, Node c) {
		if (a == NULL_NODE)
			return insert(c, b.data, 0);
		if (c == NULL_NODE)
			return insert(a, b.data, a.size());
		if (a.height > c.height)
			return paste(a, b, c, 1);
		return paste(a, b, c, -1);
	}

	/**
	 * This operation must be done in time proportional to the height of this
	 * tree.
	 * 
	 * @param pos
	 *            where to split this tree
	 * @return a new tree containing all of the elements of this tree whose
	 *         positions are >= position. Their nodes are removed from this
	 *         tree.
	 * @throws IndexOutOfBoundsException
	 */
	public EditTree split(int pos) throws IndexOutOfBoundsException {
		if (pos < 0 || pos > this.size())
			throw new IndexOutOfBoundsException();
		Node[] s = new Node[2];
		Node[] t = new Node[2];
		split(pos, s, t, this.root);
		this.root = s[0];
		return new EditTree(t[0]);
	}

	/**
	 * @return The root of this tree.
	 */
	public Node getRoot() {
		return this.root;
	}

}
