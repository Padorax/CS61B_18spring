public class Dog3{
	public int weightInPounds;

	/**One integer constructor for dogs.*/
	public Dog3(int w){
		weightInPounds = w;
	}//similar to def __init__  in python
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