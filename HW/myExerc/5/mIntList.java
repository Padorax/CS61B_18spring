/**L4 Exercise A-1*/
public class mIntList {
	public int first;
	public mIntList rest;

	public mIntList(int f, mIntList r) {
		this.first = f;
		this.rest = r;
	}

	public void addAdjacent() {
		mIntList p = this;
		if(p == null) {
			return;
		}
		mIntList s = p;
		while (s.rest != null) {
			if (s.first == s.rest.first) {
				s.first = 2* s.first;
				s.rest = s.rest.rest;
				s = p;//addAdjacent() will also do
				//break; 
			} else {
				s = s.rest;
			}
		}
	}

	public static void main(String[] args) {
		mIntList L = new mIntList(3, null);
		L = new mIntList(2,L);
		L = new mIntList(2,L);
		L = new mIntList(4,L);
		L.addAdjacent();
		System.out.println(L.first);
	}
}