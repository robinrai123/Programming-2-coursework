package question2;


import question2.Card.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class Hand implements Serializable, Iterable<Card> {
	private ArrayList<Card> hand;
	private ArrayList<Card> handInOrder;
	private int[] cardCount;
	static final long serialVersionUID = 100242165;

	Hand() {
		//creates an empty hand
		hand = new ArrayList<Card>();
		handInOrder = new ArrayList<Card>();
		cardCount = new int[52];
		//cardCount is a linear list. So two to ace for clubs is 0-12, diamonds is 13-25, 26-38, 39-51etc
	}

	Hand(Card card) {
		hand = new ArrayList<Card>();
		handInOrder = new ArrayList<Card>();
		cardCount = new int[52];
		this.hand.add(card);
		this.handInOrder.add(card);

	}

	Hand(Card[] cards) {
		//takes an array of cards and adds it to the hand
		hand = new ArrayList<Card>();
		handInOrder = new ArrayList<Card>();
		cardCount = new int[52];
		for (int i = 0; i < cards.length; i++) {
			if (cards[i] instanceof Card) {
				this.hand.add(cards[i]);
				this.handInOrder.add(cards[i]);
			}
		}
	}

	Hand(Hand secondHand) {
		//takes another hand's cards and adds it to the current hand
		hand = new ArrayList<Card>();
		handInOrder = new ArrayList<Card>();
		cardCount = new int[52];

		for (int i = 0; i < secondHand.hand.size(); i++) {
			this.hand.add(secondHand.hand.get(i));
			this.handInOrder.add(secondHand.hand.get(i));
		}
	}

	public Card getCard(int index) {
		return this.hand.get(index);
	}

	public ArrayList<Card> getHand() {
		return this.hand;
	}

	@Override
	public Iterator<Card> iterator() {
		return handInOrder.iterator();
		//iterates over the one with order maintained
	}

/*	class Iterator implements Iterator<Card> {


		//Shuffle cards then go through all of them sequentially

		int cardIndex = 0;
		Card currentCard;

		@Override
		public boolean hasNext() {
			if (cardIndex < handInOrder.size()) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Card next() {
			//I think this uses cardIndex, then increments it
			return handInOrder.get(cardIndex++);
		}

	}*/

	public int size() {
		return this.hand.size();
	}

	private void cardCounter() {
		//goes through a hand, resets it's cardCount, then recounts it
		//it's not hardcoded, it's simply inflexible!
		//System.out.println(this.hand.size() + " cards in hand.");
		for (int i = 0; i < 52; i++) {
			//resets the array
			cardCount[i] = 0;
		}
		for (int cardInHand = 0; cardInHand < this.hand.size(); cardInHand++) {
			//for every card in the hand
			int index = 0;
			if (this.hand.get(cardInHand).getSuit() == Card.Suit.DIAMONDS) {
				index += 13;
			} else if (this.hand.get(cardInHand).getSuit() == Card.Suit.HEARTS) {
				index += 26;
			} else if (this.hand.get(cardInHand).getSuit() == Card.Suit.SPADES) {
				index += 26;
			}
			index = index + this.hand.get(cardInHand).getRank().ordinal();
			cardCount[index]++;
			int debug = index % 13;
			debug += 2;
			//System.out.println("Updated card count at index: " + index + ". Value is now: " + cardCount[index] + ". Hypothetical rank of suit is: " + debug + ", ACE being 14.");
		}
		//System.out.println("\n");
	}

	void add(Card toAdd) {
		this.hand.add(toAdd);
		this.handInOrder.add(toAdd);
		//System.out.println(toAdd.toString() + " added. \n");
		cardCounter();
	}

	void add(Collection<Card> cards) {
		for (Card card : cards) {
			this.hand.add(card);
			this.handInOrder.add(card);
		}
		cardCounter();
	}

	void add(Hand toAdd) {

		int size = toAdd.hand.size();
		for (int i = 0; i < size; i++) {
			this.hand.add(toAdd.hand.get(i));
			this.handInOrder.add(toAdd.hand.get(i));
			//System.out.println(toAdd.hand.get(i).toString() + " added.");

		}
		//System.out.println("\n");
		cardCounter();
	}

/*	void addCollection() {
		//array of cards?
		//Arraylist?
	}*/


	public boolean remove(Card cardToYeet) {
		for (int i = 0; i < this.hand.size(); i++) {
			if (this.hand.get(i) == cardToYeet) {
				//removes cards
				this.hand.remove(cardToYeet);
				this.handInOrder.remove(cardToYeet);
				//recounts
				cardCounter();
				return true;
			}
		}
		return false;
	}

	public boolean remove(Hand handToYeet) {

		ArrayList<Card> cardsToYeet = new ArrayList<Card>();
		//checks if all cards are present
		for (int i = 0; i < handToYeet.hand.size(); i++) {
			//for every card in handToYeet
			if (this.hand.contains(handToYeet.hand.get(i))) {
				//if it's present in the main hand, add it to the hit list
				cardsToYeet.add(handToYeet.hand.get(i));
			} else {
				//if any card ain't in the main hand give up and fail like i do in life
				return false;
			}
		}
		//if all cards in handToYeet are present, go on with yeeting
		for (int i = 0; i < cardsToYeet.size(); i++) {
			this.hand.remove(cardsToYeet.get(i));
			this.handInOrder.remove(cardsToYeet.get(i));
		}
		return true;
	}

	public Card remove(int index) throws Exception {
		if (this.hand.get(index) == null) {
			throw new Exception();
		}
		Card cardToYeet = this.hand.get(index);
		//removes cards
		hand.remove(cardToYeet);
		handInOrder.remove(cardToYeet);
		//recounts the cards
		cardCounter();
		return cardToYeet;
	}

	public void sortDescending() {
		//sorts a hand into descending order using Card's compareTo
		//DOESN'T SORT HANDINORDER
		Collections.sort(hand);


	}

	public void sortAscending() {
		Collections.sort(hand, new CompareAscending());
	}

	public int countRank(Rank rank) {
		//return count of number of cards in hand with that rank
		//use getRank().ordinal()-1retu
		int result = 0;
		int input = rank.ordinal();
		for (Card card : this.hand) {
			if (card.getRank().ordinal() == input) {
				result++;
			}
		}
		return result;
	}

	public int handValue() {
		//use getValue()
		int total = 0;
		for (int i = 0; i < hand.size(); i++) {
			total = total + hand.get(i).getRank().getValue();
		}
		return total;
	}

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < this.hand.size(); i++) {
			result += this.hand.get(i).toString();
			result += "  ";
		}
		result += "\n";
		return result;
	}

	public boolean isFlush() {
		for (int i = 0; i < this.hand.size() - 1; i++) {
			if (this.hand.get(i).getSuit().equals(this.hand.get(i + 1).getSuit())) {
				//if any card doesn't satisfy this, return false
			} else {
				return false;
			}
		}
		return true;
	}

	public boolean isStraight() {
		this.sortDescending();
		for (int i = 0; i < this.hand.size() - 1; i++) {
			if (hand.get(i).getRank().ordinal() == hand.get(i + 1).getRank().ordinal() + 1) {
				//yo this considers jack queen king ace a straight but not ace two three

			} else {
				//as soon as any card doesn't satisfy the if, return false
				return false;
			}
		}
		return true;
	}

	private static void serialTest(Hand hand) {
		String filename = "Hand.ser";
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(hand);
			out.close();
		} catch (Exception e) {
			System.out.println("Creating serial mucked up\n");
		}
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fis);
			Hand test = (Hand) in.readObject();
			in.close();
			System.out.println("Serial test results: " + hand.toString() + "\n");
		} catch (Exception e) {
			System.out.println("Reading serial ducked up\n");
		}
	}

	public static void main(String[] args) {

		System.out.println("new cards made\n");
		Card[] cards = new Card[5];
		cards[0] = new Card(Rank.TWO, Card.Suit.CLUBS);
		cards[1] = new Card(Rank.FOUR, Card.Suit.DIAMONDS);
		cards[2] = new Card(Rank.ACE, Card.Suit.HEARTS);
		cards[3] = new Card(Rank.FOUR, Card.Suit.SPADES);
		cards[4] = new Card(Rank.FOUR, Card.Suit.CLUBS);
		System.out.println("new hand made\n");
		Hand test = new Hand(cards);

		System.out.println("cardcounter test\n");
		test.cardCounter();
		System.out.println("addCard\n");
		test.add(new Card(Rank.TWO, Card.Suit.CLUBS));
		System.out.println("new hand\n");
		Hand test2 = new Hand(cards);
		System.out.println("addHand\n");
		test.add(test2);
		System.out.println("BEFORE REMOVING");
		test.cardCounter();

		test.remove(cards[0]);
		System.out.println("After removing");
		test.cardCounter();
		System.out.println(test.toString());

		Card[] cards2 = new Card[3];
		cards2[0] = new Card(Rank.TWO, Card.Suit.CLUBS);
		cards2[1] = new Card(Rank.THREE, Card.Suit.DIAMONDS);
		cards2[2] = new Card(Rank.FOUR, Card.Suit.HEARTS);
		System.out.println("new hand made\n");
		Hand straight = new Hand(cards2);


		Card[] cards3 = new Card[3];
		cards3[0] = new Card(Rank.TWO, Card.Suit.CLUBS);
		cards3[1] = new Card(Rank.THREE, Card.Suit.CLUBS);
		cards3[2] = new Card(Rank.FOUR, Card.Suit.CLUBS);

		System.out.println("new hand made\n");
		Hand flush = new Hand(cards3);


		System.out.println("Is " + flush + " flush: " + flush.isFlush()+"\n");
		System.out.println("Is " + straight + " straight: " + straight.isStraight()+"\n");
		System.out.println("Is " + flush + " flush: " + flush.isStraight()+"\n");

		System.out.println(flush + "'s value: " + flush.handValue()+"\n");
		System.out.println("number of TWOs " + flush + " has: " + flush.countRank(Rank.TWO)+"\n");


		serialTest(flush);
	}

	public boolean hasClubs() {
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getRank().equals(Rank.TWO) && hand.get(i).getSuit().equals(Suit.CLUBS)) {
				return true;
			}
		}

		return false;
	}
}
