import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeSet;


/**
 * This program runs various sorts and gathers timing information on them.
 *
 * @author Yilun Wu
 *         Created May 7, 2013.
 */
public class SortRunner {
	private static Random rand = new Random(); // uses a fixed seed for debugging. Can remove later.
	
	//Ratio is swap two int every X ints.
	private static final int AlmostRatio=100;
	private static final int ShuffledRatio=2;
	/**
	 * Starts here.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// array size must be an int. You will need to use something much larger
		int size = 1000000; 
		
		// Each integer will have the range from [0, maxValue). If this is significantly higher than size, you
		// will have small likelihood of getting duplicates.
		int maxValue = Integer.MAX_VALUE; 
		
		// Test 1: Array of random values.
		System.out.println("-------------All Random----------------");
		int[] randomIntArray = getRandomIntArray(size, maxValue);
		runAllSortsForOneArray(randomIntArray);

		// DONE: Tests 2-4
		// Generate the three other types of arrays (shuffled, almost sorted, almost reverse sorted)
		// and run the sorts on those as well.

		System.out.println("-------------Unique Shuffled----------------");
		int[] shuffledIntArray=getUniqueElementArray(size);
		runAllSortsForOneArray(shuffledIntArray);
		
		System.out.println("-------------Almost Sorted----------------");
		int[] almostSortedIntArray=getAlmostSortedArray(size);
		runAllSortsForOneArray(almostSortedIntArray);
		
		System.out.println("-------------Almost Rev Sorted----------------");
		int[] reverseSortedIntArray=reverseArray(almostSortedIntArray.clone());
		runAllSortsForOneArray(reverseSortedIntArray);
	}

	/**
	 * 
	 * Runs all the specified sorts on the given array and outputs timing results on each.
	 *
	 * @param array
	 */
	private static void runAllSortsForOneArray(int[] array) {
		long startTime, elapsedTime; 
		boolean isSorted = false;
		
		int[] sortedIntsUsingDefaultSort = array.clone();
		Integer[] sortedIntegersUsingDefaultSort = copyToIntegerArray(array);
		Integer[] sortedIntegersUsingHeapSort = sortedIntegersUsingDefaultSort.clone();
		Integer[] sortedIntegersUsingTreeSort = sortedIntegersUsingDefaultSort.clone();
		int[] sortedIntsUsingQuickSort = array.clone();

		int size = array.length;
		
		// What is the default sort for ints? Read the javadoc.
		startTime = System.currentTimeMillis();  
		Arrays.sort(sortedIntsUsingDefaultSort); 
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntsUsingDefaultSort);
		displayResults("int", "the default sort", elapsedTime, size, isSorted);
		
		// What is the default sort for Integers (which are objects that wrap ints)?
		startTime = System.currentTimeMillis();  
		Arrays.sort(sortedIntegersUsingDefaultSort); 
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntegersUsingDefaultSort);
		displayResults("Integer", "the default sort", elapsedTime, size, isSorted);

		// Sort using the following methods, and time and verify each like done above. 
		// DONE: a simple sort that uses a TreeSet 
		startTime = System.currentTimeMillis();  
		TreeSet<Integer> set=new TreeSet<>();
		//use Hashmap to record the times each Int appears 
		HashMap<Integer, Integer> map=new HashMap<>();
		for (Integer k:sortedIntegersUsingTreeSort){
			if(!set.add(k)){
				map.put(k, map.get(k)+1);
			}
			else{
				map.put(k, 1);
			}
		}
		int i=0;
		for(Integer k:set){
			//add the duplicated into array
			for (int j=0;j<map.get(k);j++){
				sortedIntegersUsingTreeSort[i++]=k;
			}
		}
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntegersUsingTreeSort);
		displayResults("Integer", "the TreeSet sort", elapsedTime, size, isSorted);
		
		
		// DONE: your BinaryHeap sort 
		startTime = System.currentTimeMillis();  
		BinaryHeap<Integer> heap=new BinaryHeap<>();
		heap.sort(sortedIntegersUsingHeapSort);
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntegersUsingHeapSort);
		displayResults("Integer", "the HeapSort", elapsedTime, size, isSorted);
		
		// DONE: your implementation of quick sort 
		startTime = System.currentTimeMillis();  
		quickSort(sortedIntsUsingQuickSort,0,sortedIntsUsingQuickSort.length-1);
		elapsedTime = (System.currentTimeMillis() - startTime);
		isSorted = verifySort(sortedIntsUsingQuickSort);
		displayResults("int", "quickSort", elapsedTime, size, isSorted);
	}
	
	
	private static void quickSort(int a[],int start,int end){
		if(start>=end) return;
		int i=start;int j=end;
		Random random=new Random();
		int x=a[random.nextInt(end-start)+start];
		do{
			while(a[i]<x)
				i++;
			while(a[j]>x)
				j--;
			if(i<=j){
				int tmp=a[i];
				a[i]=a[j];
				a[j]=tmp;
				i++;j--;
			}
		}while(i<=j);
		if(start<j)
			quickSort(a, start, j);
		if(i<end)
			quickSort(a, i, end);	
	}
	
	
	private static void displayResults(String typeName, String sortName, long elapsedTime, int size,  boolean isSorted) {
		if (isSorted) {
			System.out.printf("Sorted %.1e %ss using %s in %d milliseconds\n", (double)size, typeName, sortName, elapsedTime);
		} else {
			System.out.println("ARRAY NOT SORTED");
		}
	}
	
	/**
	 * Checks in O(n) time if this array is sorted.
	 *
	 * @param a An array to check to see if it is sorted.
	 */
	private static boolean verifySort(int[] a) {
		// DONE: implement this.
		for (int i=1;i<a.length;i++)
			if(a[i]<a[i-1]) return false;
		return true;
	}


	/**
	 * Checks in O(n) time if this array is sorted.
	 *
	 * @param a An array to check to see if it is sorted.
	 */
	private static boolean verifySort(Integer[] a) {
		// DONE: implement this.
		for (int i=1;i<a.length;i++)
			if(a[i-1].compareTo(a[i])>0) return false;
		return true;
	}

	/**
	 * Copies from an int array to an Integer array.
	 *
	 * @param randomIntArray
	 * @return A clone of the primitive int array, but with Integer objects.
	 */
	private static Integer[] copyToIntegerArray(int[] ints) {
		Integer[] integers = new Integer[ints.length];
		for (int i = 0; i < ints.length; i++) {
			integers[i] = ints[i];
		}
		return integers;
	}

	/**
	 * Creates and returns an array of random ints of the given size.
	 *
	 * @return An array of random ints.
	 */
	private static int[] getRandomIntArray(int size, int maxValue) {
		int[] a = new int[size];
		for (int i = 0; i < size; i++) {
			a[i] = rand.nextInt(maxValue);
		}
		return a;
	}

	//generate a array that is {0,1,2,3,4.....}
	private static int[] getSortedArray(int size){
		int[] a=new int[size];
		for (int i=0;i<size;i++)
			a[i]=i;
		return a;
	}
	
	//swap(x,y);
	private static void swap(int[] a,int x,int y){
		int z=a[x];
		a[x]=a[y];
		a[y]=z;
	}
	
	//get the array shuffled.  ratio is how much it shuffled, max is 1
	private static int[] shuffledArray(int[] a,int size,int ratio){
		int k=size<ratio?1:size/ratio;
		for(int i=0;i<k;i++){
			int x=rand.nextInt(size);
			int y=rand.nextInt(size);
			swap(a, x, y);
		}
		return a;
	}
	/**
	 * Creates a shuffled random array.
	 *
	 * @param size
	 * @return An array of the ints from 0 to size-1, all shuffled
	 */
	private static int[] getUniqueElementArray(int size) {
		int[] a=getSortedArray(size);
		shuffledArray(a, size, size/ShuffledRatio);		
		return a;
	}
	
	//generate a array is nearly sorted
	private static int[] getAlmostSortedArray(int size){
		int[] a=getSortedArray(size);
		shuffledArray(a, size, size/AlmostRatio);		
		return a;		
	}
	
	//reverse the certain array.
	private static int[] reverseArray(int[] a){
		for(int i=0;i<a.length/2;i++)
			swap(a, i, a.length-1-i);
		return a;
	}
	
}
