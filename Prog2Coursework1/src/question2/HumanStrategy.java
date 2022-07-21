package question2;

import java.util.Scanner;

public class HumanStrategy implements Strategy {


	@Override
	public boolean cheat(Bid b, Hand h) {
		String input;

		//if you have to cheat

		if ((h.countRank(b.getRank()) == 0) && (h.countRank(b.getRank().getNext()) == 0)) {
			//if you don't have either the same rank or the rank above

			System.out.println("My dude you gotta cheat. \n");
			return true;
		}

		while (true) {
			System.out.println("Do you want to cheat?");
			Scanner scanner = new Scanner(System.in);
			input = scanner.nextLine();

			if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
				return true;
			} else if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) {
				return false;
			} else {
				System.out.println("Invalid input fam, yes or no please. y and n work too. ");
			}

		}
	}

	@Override
	public Bid chooseBid(Bid b, Hand h, boolean cheat) {
		Bid finalBid = null;
		Scanner scanner = new Scanner(System.in);
		Hand handToYeet = new Hand();
		Card.Rank rankToBid;
		try {
			//prints out all the players cards with a number/index to pick
			System.out.println("Your cards: \n");
			for (int j = 0; j < h.size(); j++) {
				int x = j + 1;
				System.out.println(x + ": " + h.getCard(j));
			}
			if (cheat == true) {
				//ask how many cards to bid
				System.out.println("How many cards do you want to bid? ");
				int sizeInput = intInput(4);
				//if sizeInput is bigger than the number of cards in hand
				if (sizeInput > h.size()) {
					sizeInput = h.size();
					System.out.println("You have too little cards, bidding " + h.size() + " cards instead.");
				} else {
					System.out.println("Going to bid " + sizeInput + " cards.");
				}
				for (int i = 0; i < sizeInput; i++) {
					//prints cards for user to pick
					System.out.println("Add a card to bid from the list: \n");
					for (int j = 0; j < h.size(); j++) {
						int x = j + 1;
						System.out.println(x + ": " + h.getCard(j));
					}
					//removes the card chosen and adds it to the bidding hand
					int cardSelection = intInput(h.size());
					System.out.println("picked " + cardSelection);
					cardSelection = cardSelection - 1;
					handToYeet.add(h.remove(cardSelection));
				}
				//just uses numbers 1 and 2 instead of yes and no
				System.out.println("Now, what rank do you want to lie about bidding, the current rank or the next rank. 1 or 2, respectively.");
				int rankDecision = intInput(2);
				if (rankDecision == 1) {
					rankToBid = b.getRank();
				} else {
					rankToBid = b.getRank().getNext();
				}
				System.out.println("Bidding: " + handToYeet.toString());
				finalBid = new Bid(handToYeet, rankToBid);
			} else {
				//counts how many cards of the current rank and next rank are in your hand
				int currentCount = h.countRank(b.getRank());
				int nextCount = h.countRank(b.getRank().getNext());
				int counter = 0;
				//if there's a choice
				if (currentCount > 0 && nextCount > 0) {
					System.out.println("Which rank do you want to bid? current or next?\n1 or 2 respectively.");
					int rankDecision = intInput(2);
					System.out.println("and how many cards do you want to bid?");

					//if you picked to bid current cards
					if (rankDecision == 1) {
						//sets the rank in bid to be current
						rankToBid = b.getRank();
						int amountDecision = intInput(currentCount);
						for (int i = 0; i < h.size(); i++) {
							if ((h.getCard(i).getRank() == b.getRank()) && (counter < amountDecision)) {
								handToYeet.add(h.remove(i));
								//shrinks size of hand when you remove a card, so decrease i to not miss any
								i--;
								counter++;
							}
						}
						//if you picked to bid next cards
					} else {
						//sets the rank in bid to be next
						rankToBid = b.getRank().getNext();
						int amountDecision = intInput(nextCount);
						for (int i = 0; i < h.size(); i++) {
							if ((h.getCard(i).getRank() == b.getRank().getNext()) && (counter < amountDecision)) {
								handToYeet.add(h.remove(i));
								//shrinks size of hand when you remove a card, so decrease i to not miss any
								i--;
								counter++;
							}
						}
					}
					//slap it into the final bid
					System.out.println("Bidding: " + handToYeet.toString());
					finalBid = new Bid(handToYeet, rankToBid);
				}
				//when you must bid the current rank
				else if (currentCount > 0) {
					System.out.println("You only have the current card to bid.");
					//sets the rank in bid to be the current one
					rankToBid = b.getRank();
					System.out.println("How many of them do you want to bid?");
					//input is 1 to the size of temp given they're all the right card
					int amountDecision = intInput(currentCount);
					//removes the right amount from your hand
					for (int i = 0; i < h.size(); i++) {
						if (counter < amountDecision && h.getCard(i).getRank() == rankToBid) {
							handToYeet.add(h.remove(i));
						}
					}
					System.out.println("Bidding: " + handToYeet.toString());
					finalBid = new Bid(handToYeet, rankToBid);
				}
				//when you must bid the next rank
				else if (nextCount > 0) {
					System.out.println("You only have the next card to bid.");

					//sets the rank in bid to be the next one
					rankToBid = b.getRank().getNext();

					System.out.println("How many of them do you want to bid?");
					//input is 1 to the size of temp given they're all the right card
					int amountDecision = intInput(nextCount);
					//removes the right amount from your actual hand
					for (int i = 0; i < h.size(); i++) {
						if (counter < amountDecision && h.getCard(i).getRank() == rankToBid) {
							handToYeet.add(h.remove(i));
						}
					}
					System.out.println("Bidding: " + handToYeet.toString());
					finalBid = new Bid(handToYeet, rankToBid);
				}

			}
		} catch (Exception e) {
			System.out.println("Huaman choosebid lucked up.");
		}
		return finalBid;

	}

	private int intInput(int highBound) {
		Scanner scanner = new Scanner(System.in);
		int result = 0;

		//input is 1 to the size of temp given they're all the right card
		do {
			System.out.println("Input 1 to " + highBound);
			if (scanner.hasNextInt()) {
				//if int
				result = scanner.nextInt();
				if (result >= 1 && result <= highBound) {
					//check if int is valid
					return result;
				}
			} else {
				scanner.next();
				//asks for input again
			}
			System.out.println("what??");
		} while (true);
	}

	@Override
	public Bid clubStartHand(Hand h) {
		//bids just the two of clubs. Will only be called if the hand has it
		try {
			for (int i = 0; i < h.size(); i++) {
				//goes through hand
				if (h.getCard(i).getRank().equals(Card.Rank.TWO) && h.getCard(i).getSuit().equals(Card.Suit.CLUBS)) {
					//if 2 of clubs, bid it
					Hand handToYeet = new Hand();
					handToYeet.add(h.remove(i));
					Bid finalBid = new Bid(handToYeet, Card.Rank.TWO);
					return finalBid;
				}
			}
		} catch (Exception e) {
			System.out.println("clubstart ducked\n");
		}
		return null;
		//will never hit this since the function's only called if it has the card
	}


	@Override
	public boolean callCheat(Hand h, Bid b) {
		String input = "";
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("Call cheat or nah?");
			input = scanner.nextLine();
			if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
				return true;
			} else if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) {
				return false;
			} else {
				System.out.println("Invalid input fam, yes or no please. y and n work too. ");
			}
		}
	}


}
