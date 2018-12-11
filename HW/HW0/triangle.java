public class triangle {
	public static void main(String[] args) {
		int row = 1;
		while (row <= 5) {
			int i = 1;
			while (i<= row) {
				System.out.print("*");
				i = i+1;
			}
			row = row+1;
			System.out.println();
		}
	}
}