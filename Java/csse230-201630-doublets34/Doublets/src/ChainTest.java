import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;


/**
 * Tests the Chain class.
 *
 * @author Matt Boutell.
 *         Created Mar 18, 2011.
 */
public class ChainTest {

	/**
	 * Test method for {@link Chain#addLast(java.lang.String)}.
	 */
	@Test
	public void testAddLast() {
		Chain ch = new Chain(); 
		ch = ch.addLast("test");
		assertEquals("test", ch.getLast());
		ch = ch.addLast("again");
		assertEquals("again", ch.getLast());
		Chain ch2 = ch.addLast("whoa");
		assertEquals("whoa", ch2.getLast());
		// Make sure ch was not mutated.
		assertEquals("again", ch.getLast());
		
	}

	/**
	 * Test method for {@link Chain#length()}.
	 */
	@Test
	public void testLength() {
		Chain ch = new Chain(); 
		assertEquals(0, ch.length());
		ch = ch.addLast("test");
		assertEquals(1, ch.length());
		ch = ch.addLast("again");
		assertEquals(2, ch.length());
	}

	/**
	 * Test method for {@link Chain#contains(java.lang.String)}.
	 */
	@Test
	public void testContains() {
		Chain ch = new Chain(); 
		ch = ch.addLast("test");
		ch = ch.addLast("again");
		assertTrue(ch.contains("test"));
		assertTrue(ch.contains("again"));
		assertFalse(ch.contains("rote"));
		
		// Add a couple more onto a new chain.
		Chain ch2 = ch.addLast("whoa");
		ch2 = ch2.addLast("there");
		assertTrue(ch2.contains("whoa"));
		assertTrue(ch2.contains("there"));
		
		// Make sure original ch was not mutated.
		assertFalse(ch.contains("whoa"));
	}

	/**
	 * Test method for {@link Chain#iterator(java.lang.String)}.
	 */
	@Test
	public void testIterator() {
		Chain ch = new Chain(); 
		ch = ch.addLast("test");
		ch = ch.addLast("again");
		ch = ch.addLast("and");
		ch = ch.addLast("more");
		StringBuffer sb = new StringBuffer();
		Iterator<String> iter = ch.iterator();
		while (iter.hasNext()) {
			sb.append(iter.next());
		}
		assertEquals("testagainandmore", sb.toString());
	}

}
