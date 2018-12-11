public class Deck {
	public static int[] cards;
	Deck(){
		cards = new int[]{1,3,4,10};
	}

	public static void main(String[] args) {
		Deck dingie = new Deck();
		dingie.cards[3] = 3;//in Python this will assign instance vairiable; in Java, the change is directly reflected on class variable

		Deck pilates = new Deck();
		pilates.cards[1] = 2;// change class variable

		int[] newArrWhoDis = {2,2,4,1,3};
		dingie.cards = pilates.cards;
		pilates.cards = newArrWhoDis;
		newArrWhoDis = null;//change of newArrWhoDis will not affect class variable cards
	}
}