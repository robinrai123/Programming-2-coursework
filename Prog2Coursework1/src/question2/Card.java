package question2;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Card implements Serializable, Comparable<Card> {

	static final long serialVersionUID = 100242165;
	private Rank r;
	private Suit s;


	public enum Rank {
		TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
		EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

		private final int value;

		Rank(int value) {
			this.value = value;
		}

		public Rank getPrevious() {
			int input = ordinal();
			//if TWO wrap around to ACE
			if (input == 0) {
				input = Rank.values().length;
			}
			return Rank.values()[(input - 1) % Rank.values().length];
		}

		public Rank getNext() {
			int input = ordinal();
			//if ACE wrap around to TWO
			if (input == 12) {
				input = 0;
			}
			return Rank.values()[(input + 1) % Rank.values().length];
		}

		public int getValue() {
			return this.value;
		}
	}

	protected enum Suit {
		CLUBS, DIAMONDS, HEARTS, SPADES;

		//getRandom
		public static Suit getRandom() {
			int choice = new Random().nextInt(Suit.values().length);
			//gets a random number between 0 and the length of suit
			return Suit.values()[choice];
			//returns the value at the number

		}
	}

	//simple constructor
	Card(Rank r, Suit s) {
		this.r = r;
		this.s = s;
	}

	//default constructor
	Card() {
		this.r = Rank.ACE;
		this.s = Suit.CLUBS;
	}

	public Rank getRank() {
		return this.r;
	}

	public Suit getSuit() {
		return this.s;
	}

	public String toString() {
		String returnString;
		returnString = this.r + " of " + this.s;
		return returnString;
	}


	//difference between ten and queen is 2
	static int difference(Card card1, Card card2) {
		int card1Val = card1.getRank().ordinal();
		int card2Val = card2.getRank().ordinal();
		if (card1Val < card2Val) {
			return card2Val - card1Val;
		} else {
			return card1Val - card2Val;
		}
	}

	//difference between ten and queen is 0
	static int differenceValue(Card card1, Card card2) {
		int card1Val = card1.getRank().getValue();
		int card2Val = card2.getRank().getValue();
		if (card1Val < card2Val) {
			return card2Val - card1Val;
		} else {
			return card1Val - card2Val;
		}
	}

	@Override
	public int compareTo(Card other) {
		//compares first with rank
		int potato = other.getRank().ordinal() - this.getRank().ordinal();
		if (potato == 0) {
			return other.getSuit().ordinal() - this.getSuit().ordinal();
		} else {
			return potato;
		}

	}


	static class CompareAscending implements Comparator<Card> {
		//yo these are classes you melon, you make a new instance when you use them
		@Override
		public int compare(Card o1, Card o2) {

			int potato = o1.getRank().ordinal() - o2.getRank().ordinal();
			if (potato == 0) {
				//if the ranks are the same, compare by suit
				CompareSuit compareSuit = new CompareSuit();
				return compareSuit.compare(o1, o2);
			} else {
				return potato;
			}
			//THIS IS FINE NOW PENIS
		}
	}

	static class CompareSuit implements Comparator<Card> {
		@Override
		public int compare(Card o1, Card o2) {

			return o1.getSuit().ordinal() - o2.getSuit().ordinal();


			//return o1.getSuit().ordinal() - o2.getSuit().ordinal();}
		}
	}

/*    static class CompareLambda implements Comparator<Card> {
        @Override
        public int compare(Card o1, Card o2) {
            if (o1.getRank().ordinal() > o2.getRank().ordinal()) {
                return 1;
                //if ranks are equal and suit1 is smaller than suit2
            } else if ((o1.getRank().ordinal() == o2.getRank().ordinal()) && (o1.getSuit().ordinal() < o2.getSuit().ordinal())) {
                return 1;
                //if ranks and suits are equal
            } else if ((o1.getRank().ordinal() == o2.getRank().ordinal()) && (o1.getSuit().ordinal() == o2.getSuit().ordinal())) {
                return 0;
            }
            //else (if not equal and card 1 is smaller than card 2
            else {
                return -1;
            }
        }
    }*/


	static void selectTest(Card card2Cmp) {
		ArrayList<Card> ar = new ArrayList<Card>();
		ar.add(new Card(Rank.TEN, Suit.SPADES));
		ar.add(new Card(Rank.TEN, Suit.DIAMONDS));
		ar.add(new Card(Rank.TWO, Suit.CLUBS));
		ar.add(new Card(Rank.SIX, Suit.HEARTS));
		ar.add(new Card(Rank.SIX, Suit.DIAMONDS));

		CompareAscending compareAscending = new CompareAscending();
		CompareSuit compareSuit = new CompareSuit();

		System.out.println("\nComparing by Rank\n");
		for (int i = 0; i < ar.size(); i++) {


			int result = compareAscending.compare(ar.get(i), card2Cmp);

			if (result == 0) {
				//i == card2cmp
				System.out.println(ar.get(i).getRank() + " = " + card2Cmp.getRank() + ".");
			} else if (result < 0) {
				//i < card2cmp
				System.out.println(ar.get(i).getRank() + " < " + card2Cmp.getRank() + ".");
			} else {
				//i > card2cmp
				System.out.println(ar.get(i).getRank() + " > " + card2Cmp.getRank() + ".");
			}
		}

		System.out.println("\nComparing by Rank Reversed\n");
		for (int i = 0; i < ar.size(); i++) {

			int result = ar.get(i).compareTo(card2Cmp);

			if (result == 0) {
				//i == card2cmp
				System.out.println(ar.get(i).getRank() + " = " + card2Cmp.getRank() + ".");
			} else if (result < 0) {
				//i < card2cmp
				System.out.println(ar.get(i).getRank() + " < " + card2Cmp.getRank() + ".");
			} else {
				//i > card2cmp
				System.out.println(ar.get(i).getRank() + " > " + card2Cmp.getRank() + ".");
			}
		}
		System.out.println("\nComparing by Suit\n");
		for (int i = 0; i < ar.size(); i++) {

			int result = compareSuit.compare(ar.get(i), card2Cmp);

			if (result == 0) {
				//i == card2cmp
				System.out.println(ar.get(i).getSuit() + " = " + card2Cmp.getSuit() + ".");
			} else if (result < 0) {
				//i < card2cmp
				System.out.println(ar.get(i).getSuit() + " < " + card2Cmp.getSuit() + ".");
			} else {
				//i > card2cmp
				System.out.println(ar.get(i).getSuit() + " > " + card2Cmp.getSuit() + ".");
			}
		}

		System.out.println("\nComparing by Lambda\n");

/*        lambda lambdaComparison = (Card card, Card card2) -> {

            CompareLambda compareLambda = new CompareLambda();

            int result = compareLambda.compare(card2, card);

            if (result == 0) {
                //i == card2cmp
                System.out.println(card2.toString() + " = " + card.toString() + ".");
            } else if (result < 0) {
                //i < card2cmp
                System.out.println(card2.toString() + " < " + card.toString() + ".");
            } else {
                //i > card2cmp
                System.out.println(card2.toString() + " > " + card.toString() + ".");

            }
        };*/
		for (int i = 0; i < ar.size(); i++) {
			Comparator<Card> CompareLambda = (Card o1, Card o2) -> {
				if (o1.getRank().ordinal() > o2.getRank().ordinal()) {
					return 1;
					//if ranks are equal and suit1 is smaller than suit2
				} else if ((o1.getRank().ordinal() == o2.getRank().ordinal()) && (o1.getSuit().ordinal() < o2.getSuit().ordinal())) {
					return 1;
					//if ranks and suits are equal
				} else if ((o1.getRank().ordinal() == o2.getRank().ordinal()) && (o1.getSuit().ordinal() == o2.getSuit().ordinal())) {
					return 0;
				}
				//else (if not equal and card 1 is smaller than card 2
				else {
					return -1;
				}

			};

			int result = CompareLambda.compare(ar.get(i), card2Cmp);;
			if (result == 0) {
				//i == card2cmp
				System.out.println(ar.get(i).toString() + " = " + card2Cmp.toString() + ".");
			} else if (result < 0) {
				//i < card2cmp
				System.out.println(ar.get(i).toString() + " < " + card2Cmp.toString() + ".");
			} else {
				//i > card2cmp
				System.out.println(ar.get(i).toString() + " > " + card2Cmp.toString() + ".");

			}

		}
	}


	static void serialTest(Card card) {
		String filename = "Card.ser";
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(card);
			out.close();
		} catch (Exception e) {
			System.out.println("Creating serial mucked up\n");
		}
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fis);
			Card test = (Card) in.readObject();
			in.close();
			System.out.println("Serial test results: " + test.getRank() + " of " + test.getSuit() + "\n");
		} catch (Exception e) {
			System.out.println("Reading serial ducked up\n");
		}
	}

	public static void main(String args[]) {
/*        System.out.println(Rank.getPrevious(Rank.ACE));
        System.out.println(Rank.ACE.getValue());
        System.out.println(Suit.getRandom());
        Card potato = new Card(Rank.ACE, Suit.HEARTS);
        System.out.println(potato.toString());*/
		System.out.println(Rank.TWO.getPrevious());
		ArrayList<Card> ar = new ArrayList<Card>();

		ar.add(new Card(Rank.TEN, Suit.SPADES));
		ar.add(new Card(Rank.TEN, Suit.DIAMONDS));
		ar.add(new Card(Rank.TWO, Suit.CLUBS));
		ar.add(new Card(Rank.SIX, Suit.HEARTS));
		ar.add(new Card(Rank.SIX, Suit.DIAMONDS));


		System.out.println("\nUnsorted");
		for (int i = 0; i < ar.size(); i++)
			System.out.println(ar.get(i));


		Collections.sort(ar, new CompareAscending());

		System.out.println("\nSorted By Rank");
		for (int i = 0; i < ar.size(); i++)
			System.out.println(ar.get(i));

		Collections.sort(ar, new CompareSuit());

		System.out.println("\nSorted By Suit");
		for (int i = 0; i < ar.size(); i++)
			System.out.println(ar.get(i));
		//System.out.println(difference(yeet1, yeet2));
		//System.out.println(differenceValue(yeet1, yeet2));
		System.out.println("\n");

		Card luck = new Card(Rank.FIVE, Suit.HEARTS);
		serialTest(luck);
		selectTest(luck);
	}
}

//club diamond hear spade