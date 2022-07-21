package question2;

import java.util.Random;

public class MyStrategy implements Strategy {
	private Random random = new Random();
	private Hand discarded = new Hand();

	//Same as ThinkerStrategy, but discarded is the public "supposed" pile in basicCheat.
	//It uses it's own lies at some points, but it's late and I'm lazy
	//

	@Override
	public boolean cheat(Bid b, Hand h) {
		//if you have any legit cards, sometimes cheat but usually play legit.
		//I would've just copied basicStrat and put in the random chance in both the ifs, but I can't be bothered to
		//check if that changes chance.
		if (h.countRank(b.getRank()) != 0 || h.countRank(b.getRank().getNext()) != 0) {


			if (random.nextInt(100) > 69) {
				//30% chance to still cheat
				System.out.println("Cheating\n");
				return true;

			} else {
				System.out.println("Playing legit\n");
				return false;
			}
		} else {
			System.out.println("Cheating\n");
			return true;
		}
	}

	@Override
	public Bid chooseBid(Bid b, Hand h, boolean cheat) {

		//prints out all the players cards
		System.out.println("Your cards: \n");
		for (int j = 0; j < h.size(); j++) {
			int x = j + 1;
			System.out.println(x + ": " + h.getCard(j));
		}
		System.out.println("\n");

		//makes the final hand to bid
		Hand handToBid = new Hand();
		//makes the final bid
		Bid finalBid = null;
		try {
			if (cheat == true) {
				//more likely to bid higher ranked cards
				h.sortAscending();
				//sorts the cards so the top index corresponds with the top rank
				int noToBid;    //number of cards to bid. Maxes at 4 or size of the rank, whichever's smallest
				if (h.size() < 4) {    //determines amount of cards to bid
					noToBid = random.nextInt(h.size()) + 1;    //random between 1-size
				} else {
					noToBid = random.nextInt(4) + 1;    //random between 1-4
				}
				//50 percent change to pick top card
				//30ish percent chance to pick second highest card
				for (int i = 0; i < noToBid; i++) {    //picks number of cards specified above
					if (random.nextInt(100) > 50) {    //50 percent chance to pick the top card
						handToBid.add(h.remove(h.size() - 1));
					} else if (random.nextInt(100) > 70) {    //30ish percent chance to pick the next top card
						if (h.size() < 2) {    //if hand is too small
							handToBid.add(h.remove(h.size() - 1));    //just pick the top one
						} else {                                            //the hand is big enough
							handToBid.add(h.remove(h.size() - 2));    //pick the second top one
						}
					} else {            //20ish percent to yeet any card
						handToBid.add(h.remove(random.nextInt(h.size())));    //0-size()-1
						//random card to yeet
					}
				}
				Card.Rank rankToLie;    //makes the rank to lie about bidding
				if (random.nextBoolean()) {        //50 percent chance to bid current or next
					rankToLie = b.getRank();
				} else {
					rankToLie = b.getRank().getNext();
				}
/*				for (int i = 0; i < handToBid.size(); i++) {    //adds all the cards that are going to be bid in the
					discarded.add(handToBid.getCard(i));        //remove pile
				}*/
				finalBid = new Bid(handToBid, rankToLie);        //sets the final bid
			} else {
				int currentCount = h.countRank(b.getRank());
				int nextCount = h.countRank(b.getRank().getNext());
				//bids all of your current or next cards, though sometimes it will bid a random number of them.
				if (currentCount > 0 && nextCount > 0) {
					//if both ranks are available, pick at random. Works by setting the other count to 0.
					if (random.nextBoolean()) {
						nextCount = 0;
					} else {
						currentCount = 0;
					}
				}
				int counter = 0;
				if (currentCount > 0) {
					//if only current rank is available
					Card.Rank rankToBid = b.getRank();
					if (random.nextInt(100) > 69) {
						//30 percent chance it will bid a random number of cards
						currentCount = random.nextInt(currentCount) + 1;
					}
					for (int i = 0; i < h.size(); i++) {
						if ((h.getCard(i).getRank() == b.getRank()) && (counter < currentCount)) {
							//discarded.add(h.getCard(i));
							handToBid.add(h.remove(i));
							counter++;
							i--;    //the card at i has been yeeted so you gotta go back to check the new card at i
						}
					}
					finalBid = new Bid(handToBid, rankToBid);
				} else if (nextCount > 0) {
					//if only next rank is available
					Card.Rank rankToBid = b.getRank().getNext();
					if (random.nextInt(100) > 69) {
						//30 percent chance it will bid a random number of cards
						nextCount = random.nextInt(nextCount) + 1;
					}
					for (int i = 0; i < h.size(); i++) {
						if ((h.getCard(i).getRank() == b.getRank().getNext()) && (counter < nextCount)) {
							//discarded.add(h.getCard(i));
							handToBid.add(h.remove(i));
							counter++;
							i--;    //the card at i has been yeeted so you gotta go back to check the new card at i
						}
					}
					finalBid = new Bid(handToBid, rankToBid);
				}
			}

		} catch (Exception e) {
			System.out.println("ThinkerStrat mucked up.\n");
		}
		return finalBid;
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

	public void giveDiscards(Hand h){
		discarded.add(h);
	}

	@Override
	public boolean callCheat(Hand h, Bid b) {

		int totalCount = h.countRank(b.getRank()) + discarded.countRank(b.getRank()) ;
		int bidCount = b.getCount();
		if (bidCount + totalCount > 4) {
			//your cards + their bid is more than four
			return true;
		}
		if (bidCount + totalCount == 4) {
			if (random.nextInt(100) > 80) {
				return true;
			}
		}
		if (bidCount + totalCount == 3) {
			if (random.nextInt(100) > 90) {
				return true;
			}
		}
		return false;

	}

	//resets thinker's discarded pile
	public void resetDiscarded() {
		this.discarded = new Hand();
	}


}
