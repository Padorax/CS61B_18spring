public class DLList {
	private static class IntNode {
		public int item;
		public IntNode next;
		public IntNode prev;

		public IntNode(int i, IntNode n, IntNode p) {
			item = i;
			next = n;
			prev = p;
		}
	}

	private IntNode sentinel;
	private int size;

	public DLList(int x) {
		sentinel = new IntNode(63, null, null);
		sentinel.next = new IntNode(x,sentinel,sentinel);
		sentinel.prev = sentinel.next;
		size = 1;
	}

	public addFirst(int x) {
		size += 1;
	}

	public getFirst() {

	}

	public addLast(int x) {
		size += 1;
	}

	public removeLast() {
		size -= 1;
	}

	public int size() {
		return size;
	}
}