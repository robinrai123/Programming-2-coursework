package question2;

import java.util.Random;

public class BasicStrategy implements Strategy {
	@Override
	public boolean cheat(Bid currentBid, Hand pHand) {
		boolean toCheat;
		//if currentBid's rank appears in pHand
		if (pHand.countRank(currentBid.getRank()) != 0) {
			return false;
			//if you have a rank one above the currentBid
		} else if (pHand.countRank(currentBid.getRank().getNext()) != 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Bid chooseBid(Bid currentBid, Hand pHand, boolean cheat) {
		Bid finalBid = null;
		System.out.println("current hand: " + pHand.toString());
		try {

			if (cheat == true) {
				System.out.println("Cheating\n");
				//picks a card at random and lies about either bidding the same rank or the rank above
				Hand handToBid = new Hand();
				Random random = new Random();
				handToBid.add(pHand.remove(random.nextInt(pHand.size())));    //removes a random card
				//lie about being the same rank or the one above it
				Card.Rank rankLie;
				if (random.nextBoolean()) {
					rankLie = currentBid.getRank();
				} else {
					rankLie = currentBid.getRank().getNext();
				}
				finalBid = new Bid(handToBid, rankLie);

			} else {
				//if you don't cheat
				System.out.println("Not cheating\n");
				int currentCount = pHand.countRank(currentBid.getRank());
				int nextCount = pHand.countRank(currentBid.getRank());
				Hand handToBid = new Hand();

				if (currentCount > 0) {
					//if you have the current rank
					Card.Rank rankToBid = currentBid.getRank();
					for (int i = 0; i < pHand.size(); i++) {
						//goes through the hand removing all the cards that have the current rank
						if (pHand.getCard(i).getRank() == currentBid.getRank()) {
							handToBid.add(pHand.remove(i));
						}
					}
					finalBid = new Bid(handToBid, rankToBid);
				} else {
					//go for the next rank
					Card.Rank rankToBid = currentBid.getRank().getNext();
					for (int i = 0; i < pHand.size(); i++) {
						//goes through the hand removing all the cardrs that have the next rank
						if (pHand.getCard(i).getRank() == currentBid.getRank().getNext()) {
							handToBid.add(pHand.remove(i));
						}
					}
					finalBid = new Bid(handToBid, rankToBid);
				}
			}
		} catch (Exception e) {
			System.out.println("ChooseBid ducked up\n");
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


	@Override
	public boolean callCheat(Hand pHand, Bid currentBid) {

		if (currentBid.getCount() + pHand.countRank(currentBid.getRank()) > 4) {
		    //if their bid+your cards adds up to more than 4
			return true;
		}
		return false;
	}


}
