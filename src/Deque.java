import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author Huangzf
 *
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {
	private int N;
	private Node<Item> first;
	private Node<Item> last;
	
	private static class Node<Item> {
		private Item item;
		private Node<Item> next;
		private Node<Item> prev;
		public Node(Item item) {
			this.item = item;
			next = prev = null;
		}
	}
	
	// construct an empty deque
	public Deque() {
		first = null;
		last = null;
		N = 0;
	}

	// is the deque empty?
	public boolean isEmpty() {
		return first == null;
	}

	// return the number of items on the deque
	public int size() {
		return N;
	}

	// insert the item at the front
	public void addFirst(Item item) {
		if (null == item)
			throw new NullPointerException();
		Node<Item> oldfirst = first;
		first = new Node<Item>(item);
		first.next = oldfirst;
		if (N == 0) {
			last = first;
		} else {
			oldfirst.prev = first;
		}
		N++;
	}

	// insert the item at the end
	public void addLast(Item item) {
		if (null == item)
			throw new NullPointerException();
		Node<Item> oldlast = last;
		last = new Node<Item>(item);
		last.prev = oldlast;
		if (N == 0) {
			first = last;
		} else {
			oldlast.next = last;
		}
		N++;
	}

	// delete and return the item at the front
	public Item removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException();
		Item item = first.item;
		first = first.next;
		if (null == first) {
			last = null;
		} else {
			first.prev = null;
		}
		N--;
		return item;
	}

	// delete and return the item at the end
	public Item removeLast() {
		if (isEmpty())
			throw new NoSuchElementException();
		Item item = last.item;
		last = last.prev;
		if (null == last) {
			first = null;
		} else {
			last.next = null;
		}
		N--;
		return item;
	}

	// return an iterator over items in order from front to end
	public Iterator<Item> iterator() {
		return new ListIterator<Item>(first);
	}
	
	// an iterator, doesn't implement remove()
	private static class ListIterator<Item> implements Iterator<Item> {
		private Node<Item> cur;

		public ListIterator(Node<Item> first) {
			cur = first;
		}
		
		public boolean hasNext() {
			return cur != null;
		}

		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Item item = cur.item;
			cur = cur.next;
			return item;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
