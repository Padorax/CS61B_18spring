public class Dog2{
	public int weightInPounds;

	//public static void makeNoise()
	public  void makeNoise(){
		if (weightInPounds < 10){
			System.out.println("yip!");
		} else if (weightInPounds < 30){
			System.out.println("bark");
		} else {
			System.out.println("wooof!");
		}
	}
}