public class classnamehere {
	/**Rewrite classname using for loop*/
	public static int max(int[] m){
		int maxnum = m[0];
		for (int i = 0; i < m.length; i += 1) {
			if (maxnum < m[i]) {
				maxnum = m[i];
			}
		}
		return maxnum;
	}
	public static void main(String[] args) {
		int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
		System.out.println(max(numbers));
	}
}