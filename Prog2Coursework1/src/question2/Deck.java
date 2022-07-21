package question2;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class Deck implements Serializable, Iterable<Card> {
	static final long serialVersionUID = 100242165;
	private Card[] cards = new Card[52];

	Deck() {

		int counter = 0;
		for (Card.Suit s : Card.Suit.values()) {
			//for every suit
			for (Card.Rank r : Card.Rank.values()) {
				//for every rank
				this.cards[counter] = new Card(r, s);
				//make a card with current suit and rank
				counter++;
			}
		}
		//System.out.println(counter);
	}

	static int size(Card[] card) {
		int counter = 0;
		for (int i = 0; i < 52; i++) {
			if (card[i] instanceof Card) {
				counter++;
			}
		}
		return counter;
	}

	public void shuffle() {
		Random random = new Random();

		for (int i = 0; i < cards.length; i++) {
			int toSwap = random.nextInt(cards.length);
			Card temp = cards[toSwap];
			cards[toSwap] = cards[i];
			cards[i] = temp;
		}
		//System.out.println(Arrays.toString(cards));
	}

	public Card deal() {
		//shifts all the nulls to the end of the array
		int x = cards.length - 1; //size() might not work if not actually removing cards
		Card dealt = cards[x];
		System.out.println("Dealt card: " + dealt.toString() + " card index: " + x);
		cards[x] = null;
		int nullCounter = 0;
		for (int i = 0; i < cards.length; i++) {
			if (cards[i] == null) {
				nullCounter++;
			}
		}
		for (int i = cards.length - 1; i > 0; i--) {
			cards[i] = cards[i - 1];

		}
		for (int i = 0; i < nullCounter; i++) {
			cards[i] = null;
		}
		//cards[x];
		//remove card????
		return dealt;
	}

	public Card[] getArray() {
		return this.cards;
	}

	public final void newDeck() {	//not sure why this just doesn't call the constructor but it's late
		int counter = 0;
		for (Card.Suit s : Card.Suit.values()) {
			//for every suit
			for (Card.Rank r : Card.Rank.values()) {
				//for every rank
				this.cards[counter] = new Card(r, s);
				//make a card with current suit and rank
				counter++;
			}
		}

		System.out.println("Resetting deck...\n");
	}

	@Override
	public Iterator<Card> iterator() {
		return new Deck.stdIterator();
	}

	 class stdIterator implements Iterator<Card> {
		//go through all of them sequentially

		int cardIndex = 0;
		Card currentCard;

		@Override
		public boolean hasNext() {
			if (cardIndex < cards.length) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Card next() {
			//I think this uses cardIndex, then increments it
			return cards[cardIndex++];
		}

		@Override
		public void remove() {
			cards[cardIndex - 1] = null;

		}
	}

	 class OddEvenIterator implements Iterator<Card> {


		//Shuffle cards then go through all of them sequentially

		int cardIndex = 0;
		Card currentCard;

		@Override
		public boolean hasNext() {
			//1,3,5,7...51
			if (cardIndex < cards.length) {
				//finish at 51
				return true;
				//
			}
			if (cardIndex == cards.length) {
				//if bigger than 52
				cardIndex = 1;
				return true;
			}
			return false;

		}

		@Override
		public Card next() {
			Card temp = cards[cardIndex];
			//System.out.println(cardIndex);
			cardIndex = cardIndex + 2;
			return temp;
		}

		@Override
		public void remove() {
			cards[cardIndex - 1] = null;

		}

	}

	private static void serialTest(Deck deck) {
		String filename = "Deck.ser";
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(deck);
			out.close();
		} catch (Exception e) {
			System.out.println("Creating serial mucked up\n");
		}
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fis);
			Deck test = (Deck) in.readObject();
			in.close();
			System.out.println("\nSerial test results: ");
			Iterator yeet = test.new stdIterator();
			while (yeet.hasNext()) {
				System.out.println(yeet.next());
			}
		} catch (Exception e) {
			System.out.println("Reading serial ducked up\n");
		}
	}

	public static void main(String args[]) {
		Deck yeeet = new Deck();
		System.out.println(Arrays.toString(yeeet.getArray()));
		System.out.println(size(yeeet.getArray()));
		yeeet.newDeck();
		yeeet.shuffle();
		yeeet.deal();
		yeeet.newDeck();
		System.out.println(size(yeeet.getArray()));
		yeeet.deal();


		Iterator yeet = yeeet.new stdIterator();
		OddEvenIterator yeetxd = yeeet.new OddEvenIterator();

		System.out.println("\nStandard iteration");
		while (yeet.hasNext()) {
			System.out.println(yeet.next());
		}
		yeeet = new Deck();
		yeeet.deal();
		OddEvenIterator duck = yeeet.new OddEvenIterator();
		System.out.println("\nOdd Even iteration");
		int i = 0;
		while (yeetxd.hasNext()) {
			//output looks wrong but it works
			System.out.println(yeetxd.next());
			i++;
			//System.out.println(i);
		}
		serialTest(yeeet);

	}
}
