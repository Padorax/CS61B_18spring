public class ArgsSum {
	/**prints out the sum of the command line arguments*/
	public static void main(String[] args) {
		int N = args.length;
		int sum = 0;
		for (int i = 0; i < N; i+= 1) {
			int foo = Integer.parseInt(args[i]);
			sum += foo;
		}
		System.out.println(sum);
	}
}