/**
 * 
 * @author Huangzf
 *
 */
public class Subset {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args.length != 1) throw new IllegalArgumentException();
		int k = Integer.parseInt(args[0]);
		if (k < 0) throw new IllegalArgumentException();
		
		RandomizedQueue<String> queue = new RandomizedQueue<String>();
		
		String string;
		while(!StdIn.isEmpty()) {
			string = StdIn.readString();
			queue.enqueue(string);
		}
		
		if (queue.size() < k) throw new IllegalArgumentException();
		
		for (int i=0; i < Integer.parseInt(args[0]); i++)
			StdOut.println(queue.dequeue());
	}

}
