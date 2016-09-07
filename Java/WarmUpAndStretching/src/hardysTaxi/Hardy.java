package hardysTaxi;

import java.util.List;
import java.util.ArrayList;
import static hardysTaxi.TaxicabNumber.cube;

/**
 * Provides the static method hardySolutionsLessThan(N) which finds all
 * "taxicab numbers" that are less than n. These are represented as
 * TaxicabNumber objects: each includes the sum and two different ways of
 * writing that sum as a sum of two cubes.
 * 
 * @author anderson November, 2010.
 *
 */
public class Hardy {

	/**
	 * Returns floor of the cube root of n. Can you see why this method is
	 * useful for this problem? This is not a very efficient implementation.
	 * (Not required) Can you think of a more efficient approach?
	 * 
	 * @param n
	 *            a positive integer
	 * @return integer cube root of n
	 */
	public static int cubeRootFloor(int n) {
		// DONE:Very inefficient, but quick to write.
		return (int) Math.pow(n, 1 / 3.0);
	}

	/**
	 * Computes and returns a sorted list of all taxicab numbers less than n.
	 * 
	 * @param n
	 *            a positive integer
	 * @return a List<TaxicabNumber> object. Each object contains the sum and
	 *         two different ways to write it as a sum of cubes.
	 */

	public static List<TaxicabNumber> hardySolutionsLessThan(int n) {
		List<TaxicabNumber> result = new ArrayList<TaxicabNumber>(); // You are
																		// to
																		// populate
																		// this
																		// list.
		int limit = cubeRootFloor(n);
		int upper = cubeRootFloor(n / 2);
		System.out.println(upper);
		// test all a b c d for taxicab Number
		for (int i = 1; i <= upper; i++)
			for (int j = i; j <= limit; j++) {
				// if i^3+j^3 is more or equal to n, this is illegal
				if ((Math.pow(i, 3) + Math.pow(j, 3)) >= n)
					break;
				// because finally, a is smaller than c so we assume k start
				// from i+1
				for (int k = i + 1; k <= upper; k++)
					for (int w = k; w <= limit; w++) {
						// same as before
						if ((Math.pow(k, 3) + Math.pow(w, 3)) >= n)
							break;
						try {
							TaxicabNumber tmp = new TaxicabNumber((int) (Math.pow(i, 3) + Math.pow(j, 3)), i, j, k, w);
							result.add(tmp);
						} catch (HardyException e) {
						}
					}
			}

		java.util.Collections.sort(result);
		return result;
	}

	/**
	 * main() is provided in case you want to test your code other than with
	 * unit tests. Just put try various arguments in the method call below.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(hardySolutionsLessThan(1730));
	}

}
