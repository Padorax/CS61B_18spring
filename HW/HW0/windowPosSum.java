public class windowPosSum {
	public static int[] window(int[] a, int n) {
		for (int i = 0; i < a.length; i += 1){
			if (a[i] <= 0) {continue;}
			//else
			for (int j = i + 1; j <= i+n; j += 1){
				if (j >= a.length) {break;}
				a[i] = a[i] + a[j];
				//System.out.print(a[i] + "  ");
			} 
		}
		return a;
	}
	public static void main(String[] args) {
		//int[] b = new int[]{1,2,-3,4,5,4};
		int[] b = new int[]{1, -1, -1, 10, 5, -1};
		int m = 2;
		//int[] c = window(b,m);
		System.out.println(java.util.Arrays.toString(window(b,m)));
	}
}