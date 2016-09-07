import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

// make the chain comparable so it can be put into the priorityqueue.
public class Chain implements Comparable<Chain>{

	private HashSet<String> set;
	private LinkedList<String> list;
	
	// construction
	public Chain(){
		this.set=new HashSet<String>();
		this.list=new LinkedList<String>();
	}
	
	// build a set and a list.  
	// set for contains and list record the chain
	public Chain(HashSet<String> set,LinkedList<String> list){
		this();
		this.set.addAll(set);
		this.list.addAll(list);
	}
	
	// add the word in the Chain. if it is already in it, just return.
	private void add(String string){
		if (this.set.contains(string)) return;
		this.set.add(string);
		this.list.add(string);
	}
	
	public Chain addLast(String string) {
		Chain newchain=new Chain(this.set,this.list);
		newchain.add(string);
		return newchain;
	}

	public String getLast() {
		return list.getLast();
	}

	public boolean contains(String string) {
		return set.contains(string);
	}

	public int length() {
		return list.size();
	}
	public LinkedList<String> getList(){
		return this.list;
	}
	public Iterator<String> iterator() {
		return list.iterator();
	}
	
	// the value is chain.length+the difference with the end word.
	public int getValue(){
		int ans=this.length();
		String end=Doublets.getEnd();
		String mine=this.getLast();
		for (int i=0;i<this.getLast().length();i++)
			if (mine.charAt(i)!=end.charAt(i)) ans++;
		return ans;
	}
	@Override
	public int compareTo(Chain o) {
		return this.getValue()-o.getValue();
	}

}
