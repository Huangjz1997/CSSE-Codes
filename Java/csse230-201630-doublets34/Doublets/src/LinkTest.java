import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;


/**
 * Tests the Links class.
 *
 * @author Matt Boutell.
 *         Created Mar 18, 2011.
 */
public class LinkTest {

	/**
	 * Test method for {@link Link#getCandidates(java.lang.String)}.
	 */
	@Test
	public void testGetCandidatesTinyThreeLetterWords() {
		Links linksTiny = new Links("tiny.dictionary.txt");
		Set<String> fooCandidates = linksTiny.getCandidates("foo");
		assertTrue(fooCandidates.contains("for"));
		assertTrue(fooCandidates.contains("too"));
		assertFalse(fooCandidates.contains("bar"));
		assertEquals(2, fooCandidates.size());
	}
	
	
	/**
	 * Test method for {@link Links#getCandidates(java.lang.String)}.
	 */
	@Test
	public void testGetCandidatesTinyFourLetterWords() {
		Links linksTiny = new Links("tiny.dictionary.txt");
		Set<String> candidates = linksTiny.getCandidates("math");
		assertTrue(candidates.contains("path"));
		assertTrue(candidates.contains("moth"));
		assertFalse(candidates.contains("mouth"));
		assertEquals(2, candidates.size());
	}

	/**
	 * Test method for {@link Links#getCandidates(java.lang.String)}.
	 */
	@Test
	public void testGetCandidatesTinyMissingWords() {
		Links linksTiny = new Links("tiny.dictionary.txt");
		Set<String> candidates = linksTiny.getCandidates("fwump");
		assertNull(candidates);
		candidates = linksTiny.getCandidates("bryllyg");
		assertNull(candidates);
	}

	/**
	 * Test method for {@link Links#getCandidates(java.lang.String)}.
	 */
	@Test
	public void testGetCandidatesTinyNoCandidates() {
		Links linksTiny = new Links("tiny.dictionary.txt");
		Set<String> candidates = linksTiny.getCandidates("silk");
		assertNull(candidates);
		candidates = linksTiny.getCandidates("mouth");
		assertNull(candidates);
	}

	/**
	 * Test method for {@link Links#getCandidates(java.lang.String)}.
	 */
	@Test
	public void testGetCandidates10() {
		Links links10 = new Links("english.cleaned.all.10.txt");
		Set<String> candidates = links10.getCandidates("bar");
		assertTrue(candidates.contains("bad"));
		assertTrue(candidates.contains("ban"));
		assertTrue(candidates.contains("car"));
		assertTrue(candidates.contains("far"));
		assertTrue(candidates.contains("war"));
		assertEquals(5, candidates.size());
		
		candidates = links10.getCandidates("left");
		assertTrue(candidates.contains("lift"));
		assertEquals(1, candidates.size());
	}

	/**
	 * Test method for {@link Links#getCandidates(java.lang.String)}.
	 */
	@Test
	public void testGetCandidates20() {
		Links links20 = new Links("english.cleaned.all.20.txt");
		Set<String> candidates = links20.getCandidates("row");
		assertTrue(candidates.contains("bow"));
		assertTrue(candidates.contains("cow"));
		assertTrue(candidates.contains("how"));
		assertTrue(candidates.contains("low"));
		assertTrue(candidates.contains("now"));
		assertTrue(candidates.contains("raw"));
		assertTrue(candidates.contains("rod"));
		assertTrue(candidates.contains("rot"));
		assertTrue(candidates.contains("wow"));
		assertEquals(9, candidates.size());

		candidates = links20.getCandidates("parse");
		assertTrue(candidates.contains("pause"));
		assertEquals(1, candidates.size());
		
		candidates = links20.getCandidates("reduction");
		assertTrue(candidates.contains("deduction"));
		assertEquals(1, candidates.size());
		
		candidates = links20.getCandidates("love");
		assertTrue(candidates.contains("live"));
		assertTrue(candidates.contains("lose"));
		assertTrue(candidates.contains("move"));
	}
}
