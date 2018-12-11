public class classname {
	/** Returns the maximum value from m. */
	public static int max(int[] m) {
		int maxnum = m[0];
		int i = 0;
		while (i < m.length) {
			if (maxnum < m[i]) {
				maxnum = m[i];
			}
			i = i + 1;
		}
		return maxnum;
	}
	public static void main(String[] args) {
		int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
		//return max(numbers);
		System.out.println(max(numbers));
	}	
}