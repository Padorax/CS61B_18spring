public class drawtriangle {
	public static void draw(int N) {
		int row = 1;
		while (row <= N) {
			int i = 1;
			while (i <= row) {
				System.out.print("*");
				i = i+1;
			}
			System.out.println();
			row = row +1;
		}
	}
	public static void main(String[] args) {
		draw(10);
	}
}