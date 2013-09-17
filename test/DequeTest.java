import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */

/**
 * @author Huangzf
 *
 */
public class DequeTest {

	private Deque<Integer> deque; 
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		deque = new Deque<Integer>();
	}

	/**
	 * Test method for {@link Deque#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
		assertTrue(deque.isEmpty());
	}

	/**
	 * Test method for {@link Deque#size()}.
	 */
	@Test
	public void testSize() {
		assertTrue(deque.size() == 0);
	}

	/**
	 * Test method for {@link Deque#addFirst(java.lang.Object)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testAddFirst() {
		deque.addFirst(null);
	}

	/**
	 * Test method for {@link Deque#addLast(java.lang.Object)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testAddLast() {
		deque.addLast(null);
	}

	/**
	 * Test method for {@link Deque#removeFirst()}.
	 */
	@Test(expected=NoSuchElementException.class)
	public void testRemoveFirst() {
		deque.removeFirst();
	}

	/**
	 * Test method for {@link Deque#removeLast()}.
	 */
	@Test(expected=NoSuchElementException.class)
	public void testRemoveLast() {
		deque.removeLast();
	}

	/**
	 * Test method for {@link Deque#iterator()}.
	 */
	@Test(expected=UnsupportedOperationException.class)
	public void testIterator() {
		Iterator<Integer> iterator = deque.iterator();
		iterator.remove();
	}

	@Test(timeout=200)
	public void testAddFirstThenRemoveLast() {
		Deque<Integer> deque = new Deque<Integer>();
		for (int i=100; i > 0; i--) {
			deque.addFirst(i);
			assertEquals(deque.removeLast().intValue(), i);
		}
		assertTrue(deque.isEmpty());
	}
}
