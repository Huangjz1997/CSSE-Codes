package hardysTaxi;

import java.util.*;

/**
 * Provides the static method hardySolutionsLessThan(N) which finds all "taxicab
 * numbers that are less than n. These are represented as TaxicabNumber objects,
 * which include the sum and two different ways of writing that as a sum of two
 * cubes.
 * 
 * @author Claude Anderson.
 * 
 */
public class Hardy {
	//set records the result of a^2+b^2
	private static HashSet<Long> set;
	//use ArrayList records ans which can get number by index.
	private static ArrayList<Long> ansList;
	//use TreeSet record ans which can use log(n) to get .contain()
	private static TreeSet<Long> ansSet;
	//avoid the duplicated, a little hard to explain.
	private static ArrayList<Integer>index;
	//Minimal is the current minimal, then delete num less than minimal from the set
	private static int Minimal = 0;
	//Record the current MAX calculate by a^2+b^2
	private static Long MAX = 0L;
	//the size of the AnsList
	private static Long ans = 0L;
	//the range of each search 
	private static final Long DELTAMAX=200000000L;
	
	
	//return the cube
	private static long cube(int k) {
		long ans = k;
		ans = ans * ans * ans;
		return ans;
	}

	//expand of index
	public static void clear(int k) {
		for (int i =0; i <k; i++) {
			index.add(0);
		}
	}
	
	public static Long init(long MAX) {
		//i is the current a  (a^2+b^2=n)
		int i = Minimal + 1;
		//the min n we can get in this round
		//(a^2+b^2<=MAX)
		//tmp1 is a^3, tmp2 is b^2, tmp3 is n=a^2+b^2.
		while (cube(i) + 1 <= MAX) {
			long tmp1 = cube(i);
			if (i >=index.size())
				clear(10000);
			//avoid many times of pointers move, we get the index
			int b=index.get(i);
			long tmp2 = cube(b+1);
			//while a^2+b^2<=MAX we keeping search 
			while (tmp1 + tmp2 <= MAX) {
				//update the min b of a
				b++;
				//get n(tmp3)
				long tmp3 = tmp1 + tmp2;
				//if it contains, we get a ans
				if (set.contains(tmp3)) {
					//if ansSet doesn't contain tmp3, we get the ans
					if (!ansSet.contains(tmp3)) {
						//Set for log(n) contains
						ansSet.add(tmp3);
						//List for sort and get num by index
						ansList.add(tmp3);
					}
				} else {
					set.add(tmp3);
				}
				//we won't use it again
				if (b== i) {
					Minimal = i;
					break;
				}
				tmp2 = cube(b + 1);
			}
			index.set(i, b);
			//a plus one
			i++;
		}
		
		return (long)ansList.size();
	}

	//refresh them all
	public static void refresh(){
		ansList =new ArrayList<>();
		ansSet=new TreeSet<>();
		index = new ArrayList<>();
		ans=0L;MAX=0L;Minimal=0;
	}
	/**
	 * Find the nth Hardy number (start counting with 1, not 0) and the numbers
	 * whose cubes demonstrate that it is a Hardy number.
	 * 
	 * @param n
	 * @return the nth Hardy number
	 */
	public static long nthHardyNumber(int n) {
		//delete the last ans and search.
		//refresh all datas
		
		refresh();
		
		//keep searching until get enough hardyNumber
		while (ans < n) {
			//just force on MAX~MAX+DELTA
			set=new HashSet<>();
			//increase the MAX of the hardyNumber
			MAX = MAX +DELTAMAX;
			ans = init(MAX);
		}
		//sort the list
		Collections.sort(ansList);
		return ansList.get(n - 1);
	}

}
