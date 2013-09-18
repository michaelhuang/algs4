import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] a;
	private int N;
	
	// construct an empty randomized queue
	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		a = (Item[]) new Object[2];
	}

	// is the queue empty?
	public boolean isEmpty() {
		return N == 0;
	}

	// return the number of items on the queue
	public int size() {
		return N;
	}
	
	@SuppressWarnings("unchecked")
	private void resize(int newSize) {
		Item[] temp = (Item[]) new Object[newSize];
		for (int i=0; i < N; i++) {
			temp[i] = a[i];
		}
		a = temp;
	}

	// add the item
	public void enqueue(Item item) {
		if (null == item) throw new NullPointerException();
		if (N == a.length) resize(2*a.length);
		a[N++] = item;
	}

	// delete and return a random item
	public Item dequeue() {
		if (isEmpty()) throw new NoSuchElementException();
		int i = StdRandom.uniform(N);
		Item value = a[i];
		N--;
		if (i == N) {    // a[i] is the last item
			a[i] = null;
		} else {
			a[i] = a[N];
			a[N] = null;  // to avoid loitering
		}
		if (N > 0 && N == a.length/4) resize(a.length/2);
		return value;
	}

	// return (but do not delete) a random item
	public Item sample() {
		if (isEmpty()) throw new NoSuchElementException();
		int i = StdRandom.uniform(N);
		return a[i];
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new ListIterator<Item>(a);
	}
	
	private static class ListIterator<Item> implements Iterator<Item> {
		private Item[] cur;
		private int n;
		
		@SuppressWarnings("unchecked")
		public ListIterator(Item[] a) {
			n = a.length;
			cur = (Item[]) new Object[n];
			for (int i=0; i < n; i++)
				cur[i] = a[i];
			StdRandom.shuffle(cur);
		}
		
		public boolean hasNext() {
			return n > 0;
		}

		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			return cur[--n];
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
