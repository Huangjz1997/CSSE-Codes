package hardysTaxi;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author Claude Anderson, points and ordering revised by Matt Boutell.
 * 
 *         When the tests are run for grading purposes, we will set Eclipse's
 *         heap memory to 1024 megabytes (in Program Files\Eclipse\eclipse.ini,
 *         and run these tests giving the jre 900 Megabytes of heap space (set
 *         this in Run Configurations). The leading zeros on the test names are
 *         used in conjunction with FixMethodOrder to present the tests in
 *         ascending order. (The order of tests in Java 7 is nondeterministic)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HardyTests {

	private static int points = 0;
	private static boolean previousSuccess = true;

	/**
	 * Run a single test of nthHardyNumber. If a previous test failed or timed
	 * out, this one would almost certainly do the same, so it is not run. The
	 * global variable previousSuccess is used to keep track of this. Assumes
	 * that tests are run sequentially. There could be problems if tests are run
	 * concurrently.
	 * 
	 * @param nth
	 *            We are finding the nth Hardy number
	 * @param correctSum
	 *            This is what the answer should be
	 * @param pointsForCorrectNumber
	 *            Points awarded for getting the correct answer
	 * @param pointsForTime
	 *            Points awarded for getting the answer in less than a minute.
	 */
	private void runOneTest(int nth, long correctSum,
			int pointsForCorrectNumber, int pointsForTime) {
		assertTrue(previousSuccess); // if false, don't waste time trying this
										// one.
		previousSuccess = false; // If anything fails here, don't run more
									// tests.
		System.gc(); // run garbage collector to your timed test starts with as
						// much available memory as it can.
		long startTime = System.currentTimeMillis(); // Give the user a little
														// more info.
		long num = Hardy.nthHardyNumber(nth);
		long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
		assertEquals(correctSum, num);
		points += pointsForCorrectNumber;
		System.out.println("n = " + nth + ", elapsed time = " + elapsedTime
				+ "     " + num);
		assertTrue(elapsedTime <= 80);
		points += pointsForTime;
		System.out.printf("Points so far: %d-%d, depending on doc and style; lower if you violated hardy commandments\n", points, (points+16));
		//System.out.println("Points so far: " + points);
		previousSuccess = true; // This one worked; try the next one.
	}

	@Test
	public void testHardyNumber_000001() {
		runOneTest(1, 1729L, 2, 2);
	}

	@Test
	public void testHardyNumber_000002() {
		runOneTest(2, 4104L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_000005() {
		runOneTest(5, 32832L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_000010() {
		runOneTest(10, 65728L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_000020() {
		runOneTest(20, 262656L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_000040() {
		runOneTest(40, 920673L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_000080() {
		runOneTest(80, 3375008L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_000160() {
		runOneTest(160, 10811017L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_000320() {
		runOneTest(320, 42549416L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_000640() {
		runOneTest(640, 166560193L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_001000() {
		runOneTest(1000, 416318561L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_002000() {
		runOneTest(2000, 1671816384L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_004000() {
		runOneTest(4000, 7103146447L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_006000() {
		runOneTest(6000, 16541054656L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_008000() {
		runOneTest(8000, 30225888875L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_010000() {
		runOneTest(10000, 48538460952L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_012000() {
		runOneTest(12000, 72125011153L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_014000() {
		runOneTest(14000, 100547229384L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_016000() {
		runOneTest(16000, 133882383096L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_018000() {
		runOneTest(18000, 172921387464L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_020000() {
		runOneTest(20000, 216264806875L, 2, 2);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_025000() {
		runOneTest(25000, 349961119928L, 1, 1);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_030000() {
		runOneTest(30000, 520890296211L, 1, 1);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_040000() {
		runOneTest(40000, 976889700163L, 1, 1);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_060000() {
		runOneTest(60000, 2389857538048L, 1, 1);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_090000() {
		runOneTest(90000, 5858794561158L, 1, 1);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_120000() {
		runOneTest(120000, 11149741127168L, 1, 1);
	}

	@Test(timeout = 90000)
	public void testHardyNumber_150000() {
		runOneTest(150000, 18362449483541L, 1, 1);
	}

	@Test(timeout = 150000)
	public void testHardyNumber_200000() {
		runOneTest(200000, 35059220195419L, 1, 1);
	}

	@AfterClass
	public static void summary() {
		System.out.println("Total Points: " + points);
		System.out
				.println("You will lose at least 20% of these points if you did not follow\n"
						+ "the \"no fixed limits or fixed-sized arrays\" guidelines.");
		System.out
				.println("When we test your code for grading, we may use some \n"
						+ "different numbers (including larger ones), but the tests will have the same form.");
	}

}
