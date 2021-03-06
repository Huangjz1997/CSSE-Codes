@SuppressWarnings("unchecked")
public class BinaryHeap<T extends Comparable<? super T>> {
	
	int  DEFAULT_SIZE=10;
	int current_size=DEFAULT_SIZE;
	int pointer=0;
	T[] HeapArray;
	

	
	public BinaryHeap() {
		HeapArray=(T[]) new Comparable[DEFAULT_SIZE];
	}
	

	public BinaryHeap(Class<?> a) {
		HeapArray=(T[]) new Comparable[DEFAULT_SIZE];
	}


	//when the array is not enought double it.
	private void grow(){
		T[] TmpArray=(T[]) new Comparable[current_size];
		for (int i=0;i<=pointer;i++)
			TmpArray[i]=HeapArray[i];
		current_size*=2;
		HeapArray=(T[]) new Comparable[current_size];
		for (int i=0;i<=pointer;i++)
			HeapArray[i]=TmpArray[i];
	}	
	
	//swap i and j in array
	private void swap(T[] array,int i,int j){
		T tmp=array[i];
		array[i]=array[j];
		array[j]=tmp;
	}
	
	//help method of deleteMin
	//return the delete element
	private T deletemin(T[] HeapArray,int length){
		if (length==0) return null;
		T ans=HeapArray[1];
		swap(HeapArray,1, length--);
		int pos=1;
		while(pos*2<=length){
			int i=0;
			if (pos*2==length){
				if (HeapArray[pos].compareTo(HeapArray[pos*2])>0) i=pos*2;
				if (i==0) break;
			}
			else{
				if (HeapArray[pos*2].compareTo(HeapArray[pos*2+1])>0) 
					i=pos*2+1;
				else
					i=pos*2;
				if (HeapArray[pos].compareTo(HeapArray[i])<0) break;
			}
			swap(HeapArray, pos, i);
			pos=i;
		}
		return ans;
		
	}
	
	//swap the root(minest) with the last one
	public T deleteMin(){
		if (pointer==0) return null;
		return deletemin(HeapArray, pointer--);
	}

	//help method for insert
	private void add(T i,T[] HeapArray, int pos){
		HeapArray[pos]=i;
		while (pos>1){
			if (HeapArray[pos].compareTo(HeapArray[pos/2])<0){
				swap(HeapArray, pos, pos/2);
				pos=pos/2;
			}
			else 
				break;
		}
	}
	//insert i into the heap
	public void insert(T i) {
		if (pointer==current_size-1) grow();
		add(i, HeapArray, ++pointer);
	}
	


	//toString 
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder("[null");
		for (int i=1;i<=pointer;i++){
			sb.append(", "+HeapArray[i]);
		}
		sb.append("]");
		return sb.toString();
	}

	/*
	 * check is the i th element in HeapArray follow the rule of heap
	 * @param T[] HeapArray,int length, int i
	 */
	public void check(T[] HeapArray,int length,int i){
		if (i*2>length) return;
		int tmp=i*2+1;
		if (tmp>length||HeapArray[tmp].compareTo(HeapArray[tmp-1])>0)
			tmp--;
		if (HeapArray[i].compareTo(HeapArray[tmp])>0){
			swap(HeapArray, i, tmp);
			check(HeapArray, length, tmp);
		}
	}
	
	/*
	 * Build heap in O(n)
	 * @param T[] HeapArray, int length;
	 */
	public void buildHeap(T[] HeapArray,int length){
		for (int i=length/2;i>0;i--)
			check(HeapArray,length,i);
	}
	
	/*
	 * HeapSort From small to large
	 * @param T[] array
	 * 
	 */
	public void sort(T[] array) {
		int length=array.length;
		if (length==0) return;
		T tmp=array[0];
		buildHeap(array, length-1);
		for (int i=1;i<length;i++)
			deletemin(array, length-i);
		for (int i=1;i<length;i++)
			if (array[i].compareTo(tmp)>0){
				array[i-1]=array[i];
				if(i==length-1) array[length-1]=tmp;
			}
			else{
				array[i-1]=tmp;
				break;
			}
		
		for (int i=0;i<length/2;i++){
			swap(array,i, length-1-i);
		}
	}

}
