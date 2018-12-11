public class IntList {
	public int first;
	public IntList rest;
	public IntList(int f, IntList r) {
	/** Instantialize IntList*/
		first = f;
		rest = r;
	}
	public int size() {
	/** Get the size of IntList*/
		if (rest == null) {
			return 1;
		} 
		return 1 + rest.size();
	}
	public int get(int i) {
	/** Get the ith element of IntList*/
		if (i==0) {
			return first;
		}
		return rest.get(i-1);
	}
	public static IntList incrList(IntList L, int x) {
	/**return an IntList identical to L, but with all values incremented by x*/
		int N = L.size();
		IntList Q = new IntList(x + L.get(N-1),null);		
		for (int i = 1; i< N; i+= 1) {
			Q = new IntList(L.get(N-1-i)+x, Q);
		}
		return Q;
	}
	public static IntList dincrList(IntList L, int x) {
	/**similiar but in-place*/
		int N = L.size();
		IntList Q = L;
		//for (int i = 0; i<N; i+=1) {
		while (Q!=null){
			Q.first += x;
			Q = Q.rest;
		}
		return L;
	}
	public static void main(String[] args){
		IntList L = new IntList(15,null);
		L = new IntList(10, L);
		L = new IntList(5, L);
		//System.out.println(L.size());
		//System.out.println(L.get(1));
		//IntList Q = IntList.incrList(L,2);
		IntList Q = IntList.dincrList(L, 3);
		System.out.println(L.get(0));
		System.out.println(L.get(1));
		System.out.println(L.get(2));
	}
}