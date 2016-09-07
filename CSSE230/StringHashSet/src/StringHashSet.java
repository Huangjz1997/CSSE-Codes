import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * A hash set implementation for Strings. Cannot insert null into the set. Other
 * requirements are given with each method.
 *
 * @author Matt Boutell and Yilun Wu. Created Oct 6, 2014.
 */
public class StringHashSet {

	// The initial size of the internal array.
	private static final int DEFAULT_CAPACITY = 5;
	private int current_size=DEFAULT_CAPACITY;
	private int num_of_nodes;
	private Node[] HashArray;
	private final  Node NULL_NODE=new Node("null",null);
	private int version;

	// You'll want fields for the size (number of elements) and the internal
	// array of Nodes. I also added one for the capacity (the length
	// of the internal array).

	private class Node {
		private String Str;
		private Node next;
		public Node(){
			
		}
		public Node(String str,Node next){
			this.Str=str;
			this.next=next;
		}
		public String getStr(){
			return this.Str;
		}
		public Node getnext(){
			return this.next;
		}
		public void setnext(Node next){
			this.next=next;
		}
	}

	/**
	 * Creates a Hash Set with the default capacity.
	 */
	public StringHashSet() {
		// Recall that using this as a method calls another constructor
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Creates a Hash Set with the given capacity.
	 */
	public StringHashSet(int initialCapacity) {
		version=0;
		initialize(initialCapacity);
	}

	private void initialize(int initialCapacity) {
		num_of_nodes=0;
		current_size=initialCapacity;
		HashArray=new Node[initialCapacity+1];		
		for(int i=0;i<=current_size;i++) HashArray[i]=NULL_NODE;
	}

	/**
	 * Calculates the hash code for Strings, using the x=31*x + y pattern.
	 * Follow the specification in the String.hashCode() method in the Java API.
	 * Note that the hashcode can overflow the max integer, so it can be
	 * negative. Later, in another method, you'll need to account for a negative
	 * hashcode by adding Integer.MAX_VALUE before you mod by the capacity
	 * (table size) to get the index.
	 *
	 * This method is NOT the place to calculate the index though.
	 *
	 * @param item
	 * @return The hash code for this String
	 */
	public static int stringHashCode(String item) {
		int tmp=0;
		for (int i=0;i<item.length();i++)
			tmp=tmp*31+(int)item.charAt(i);
		return tmp;
	}

	public int getHashCode(String item){
		int HashCode=StringHashSet.stringHashCode(item);
		if (HashCode<0) HashCode+=Integer.MAX_VALUE;
		HashCode=HashCode % current_size;
		return HashCode;
	}
	/**
	 * Adds a new node if it is not there already. If there is a collision, then
	 * add a new node to the -front- of the linked list.
	 * 
	 * Must operate in amortized O(1) time, assuming a good hashcode function.
	 *
	 * If the number of nodes in the hash table would be over double the
	 * capacity (that is, lambda > 2) as a result of adding this item, then
	 * first double the capacity and then rehash all the current items into the
	 * double-size table.
	 *
	 * @param item
	 * @return true if the item was successfully added (that is, if that hash
	 *         table was modified as a result of this call), false otherwise.
	 */
	public boolean add(String item) {
		if (this.contains(item)) return false;
		if (2*current_size==num_of_nodes) this.grow();
		num_of_nodes++;
		int HashCode=this.getHashCode(item);
		Node k=HashArray[HashCode];
		HashArray[HashCode]=new Node(item,k);
		version++;
		return true;
	}

	private void grow(){
		ArrayList<String> list=new ArrayList<>();
		Iterator<String> iterator=new HashSetIterator();
		while (iterator.hasNext()) list.add(iterator.next());
		current_size*=2;
		HashArray=new Node[current_size+1];		
		for(int i=0;i<=current_size;i++) HashArray[i]=NULL_NODE;
		addAll(list);
	}
	/**
	 * Prints an array value on each line. Each line will be an array index
	 * followed by a colon and a list of Node data values, ending in null. For
	 * example, inserting the strings in the testAddSmallCollisions example
	 * gives "0: shalom hola null 1 bonjour null 2 caio hello null 3 null 4 hi
	 * null". Use a StringBuilder, so you can build the string in O(n) time.
	 * (Repeatedly concatenating n strings onto a growing string gives O(n^2)
	 * time)
	 * 
	 * @return A slightly-formatted string, mostly used for debugging
	 */
	public String toRawString() {
		StringBuilder sb=new StringBuilder();
		for (int i=0;i<current_size;i++){
			sb.append(i+":");
			Node k=HashArray[i];
			while(true){
				sb.append(" "+k.getStr());
				if (k==NULL_NODE) break;
				k=k.getnext();
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * 
	 * Checks if the given item is in the hash table.
	 * 
	 * Must operate in O(1) time, assuming a good hashcode function.
	 *
	 * @param item
	 * @return True if and only if the item is in the hash table.
	 */
	public boolean contains(String item) {
		int HashCode=this.getHashCode(item);
		Node k=HashArray[HashCode];
		while (k!=NULL_NODE){
			if (k.getStr().equals(item)) return true;
			k=k.getnext();
		}
		return false;
	}

	/**
	 * Returns the number of items added to the hash table. Must operate in O(1)
	 * time.
	 *
	 * @return The number of items in the hash table.
	 */
	public int size() {
		return num_of_nodes;
	}

	/**
	 * @return True iff the hash table contains no items.
	 */
	public boolean isEmpty() {
		return num_of_nodes==0;
	}

	/**
	 * Removes all the items from the hash table. Resets the capacity to the
	 * DEFAULT_CAPACITY
	 */
	public void clear() {
		initialize(DEFAULT_CAPACITY);
		// DONE: Write this. Should take 1 line if you read carefully above.
	}

	/**
	 * Removes the given item from the hash table if it is there. You do NOT
	 * need to resize down if the load factor decreases below the threshold.
	 * 
	 * @param item
	 * @return True If the item was in the hash table (or equivalently, if the
	 *         table changed as a result).
	 */
	public boolean remove(String item) {
		version++;
		num_of_nodes--;
		int HashCode=getHashCode(item);
		Node k=HashArray[HashCode];
		Node p=NULL_NODE;
		while (k!=NULL_NODE){
			if (k.getStr().equals(item)){
				if (p==NULL_NODE){
					HashArray[HashCode]=k.getnext();
					return true;
				}
				p.setnext(k.next);
				return true;
			}
			p=k;
			k=k.getnext();
		}
		num_of_nodes--;
		return false;
	}

	/**
	 * Adds all the items from the given collection to the hash table.
	 *
	 * @param collection
	 * @return True if the hash table is modified in any way.
	 */
	public boolean addAll(Collection<String> collection) {
		for (String k:collection)
			add(k);
		return true;
	}

	/**
	 * 
	 * Challenge Feature: Returns an iterator over the set. Return the items in
	 * any order that you can do efficiently. Should throw a
	 * NoSuchElementException if there are no more items and next() is called.
	 * Should throw a ConcurrentModificationException if next() is called and
	 * the set has been mutated since the iterator was created.
	 *
	 * @return an iterator.
	 */
	public Iterator<String> iterator() {
		return new HashSetIterator();
	}
	private class HashSetIterator implements Iterator<String>{
		int pos,num,ver;
		Node now;
		public  HashSetIterator() {
			pos=-1;
			num=0;
			now=NULL_NODE;
			ver=version;
		}
		@Override
		public boolean hasNext() {
			if (ver!=version) throw new ConcurrentModificationException();
			return num<num_of_nodes;
		}

		@Override
		public String next() {
			if (ver!=version) throw new ConcurrentModificationException();
			if (!hasNext()) 
				throw new NoSuchElementException();
			num++;
			if (now!=NULL_NODE) now=now.getnext();
			if (now==NULL_NODE){
				pos++;
				while (HashArray[pos]==NULL_NODE) pos++;
				now=HashArray[pos];
			}
			return now.getStr();
		}
		
	}

	// Challenge Feature: If you have an iterator, this is easy. Use a
	// StringBuilder, so you can build the string in O(n) time. (Repeatedly
	// concatenating n strings onto a string gives O(n^2) time)
	// Format it like any other Collection's toString (like [a, b, c])
	@Override
	public String toString() {
		boolean first=true;
		StringBuilder sb=new StringBuilder("[");
		Iterator<String> iterator=new HashSetIterator();
		while (iterator.hasNext()){
			if (!first) sb.append(", ");
			sb.append(iterator.next());
			first=false;
		}
		sb.append(']');
		return sb.toString();
	}
}
