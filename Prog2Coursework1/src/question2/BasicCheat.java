package question2;

import question2.Card.*;

import java.util.*;

public class BasicCheat implements CardGame {
	private Player[] players;
	private int nosPlayers;
	public static final int MINPLAYERS = 5;
	private int currentPlayer;
	private Hand discards;    //not accessible to anyone - they'd be cheating at cheat!
	private Hand publicDiscards;
	private Bid currentBid;


	public BasicCheat() {
		this(MINPLAYERS);    //smart gamer move whoever wrote this
	}

	public BasicCheat(int playerNumber) {
		StrategyFactory factory = new StrategyFactory();
		nosPlayers = playerNumber;
		//nosFancyPlayers = fancy;

		players = new Player[nosPlayers];
		//fancyPlayers = new fancyPlayer[nosFancyPlayers];



		for (int i = 0; i < nosPlayers; i++) {
			players[i] = (new BasicPlayer(new MyStrategy(), this));
			Scanner scanner = new Scanner(System.in);
			System.out.println("What Strategy do you want for player "+ (i+1)+"?");
			boolean temp = true;
			String input;
			while(temp == true){
				System.out.println("Basic, Human, Thinker, or My?");
				input = scanner.nextLine();
				if(input.equalsIgnoreCase("Human")||input.equalsIgnoreCase("h")){
					players[i] = new BasicPlayer(factory.setStrategy("h"), this);
					temp = false;
				}
				else if (input.equalsIgnoreCase("Thinker")||input.equalsIgnoreCase("t")){
					players[i] = new BasicPlayer(factory.setStrategy("t"), this);
					temp = false;
				}
				else if(input.equalsIgnoreCase("My")||input.equalsIgnoreCase("m")){
					players[i] = new BasicPlayer(factory.setStrategy("m"), this);
					temp = false;
				}
				else if(input.equalsIgnoreCase("Basic")||input.equalsIgnoreCase("b")){
					players[i] = new BasicPlayer(factory.setStrategy("peee"), this);
					temp = false;
				}
				else{
					System.out.println("Pick one of the four options.");
				}
			}
		}
		currentBid = new Bid();
		currentBid.setRank(Rank.TWO);
		currentPlayer = 0;
	}

	@Override
	public boolean playTurn() {
//        lastBid=currentBid;
		//Ask player for a play,
		System.out.println("Current bid = " + currentBid + "\n");

		if (players[currentPlayer].getStrategy() instanceof MyStrategy) {
			//regular discards reveals the actual cards, so I'm giving myStrat all the "supposed" bids from the players
			((MyStrategy) players[currentPlayer].getStrategy()).giveDiscards(publicDiscards);
		}

		currentBid = players[currentPlayer].playHand(currentBid);


		//
		System.out.println("Player bid = " + currentBid + "\n");
		//Add hand played to discard pile
		discards.add(currentBid.getHand());
		for (int i = 0; i < currentBid.getHand().size(); i++) {
			publicDiscards.add(new Card(currentBid.getRank(), Card.Suit.CLUBS));
			//suit doesn't matter, given we're just counting ranks in myStrat
		}
		//Offer all other players the chance to call cheat
		boolean cheat = false;

		ArrayList<Integer> shuffle = new ArrayList<>();
		//arrayList of player numbers. 1,2,3,4,5...etc to the number of players
		for (int i = 0; i < players.length; i++) {
			shuffle.add(i);
		}
		Collections.shuffle(shuffle);
		//shuffles the arrayList. 3,2,5,1,4...etc

		for (int j = 0; j < players.length && !cheat; j++) {
			int i = shuffle.get(j);    //simply uses the number in the arrayList as i. Clever eh?
			if (i != currentPlayer) {
				System.out.println("Player " + (i + 1) + "'s turn to accuse.");
				cheat = players[i].callCheat(currentBid);
				if (cheat) {
					System.out.println("Player called cheat by Player " + (i + 1));
					if (isCheat(currentBid)) {
						//CHEAT CALLED CORRECTLY
						//Give the discard pile of cards to currentPlayer who then has to play again
						players[currentPlayer].addHand(discards);
						System.out.println("Player cheats!");
						System.out.println("Adding cards to player " +
								(currentPlayer + 1) + players[currentPlayer]);

					} else {
						//CHEAT CALLED INCORRECTLY
						//Give cards to caller i who is new currentPlayer
						System.out.println("Player is honest");
						currentPlayer = i;
						players[currentPlayer].addHand(discards);
						System.out.println("Adding cards to player " +
								(currentPlayer + 1) + players[currentPlayer]);
					}
					//If cheat is called, current bid reset to an empty bid with rank two whatever
					//the outcome
					currentBid = new Bid();
					//Discards now reset to empty
					discards = new Hand();
					publicDiscards = new Hand();

					//reset the discard pile for all strategies with one
					for (int x = 0; x < players.length; x++) {
						if (players[x].getStrategy() instanceof ThinkerStrategy) {
							((ThinkerStrategy) players[x].getStrategy()).resetDiscarded();
						}
						if (players[x].getStrategy() instanceof MyStrategy) {
							((MyStrategy) players[x].getStrategy()).resetDiscarded();
						}
					}
					clubStart();
					//start from whoever has the 3 of clubs
				}
			}
		}
		if (!cheat) {
			//Go to the next player
			System.out.println("No Cheat Called");
			currentPlayer = (currentPlayer + 1) % nosPlayers;
		}
		return true;
	}

	public int winner() {
		for (int i = 0; i < nosPlayers; i++) {
			if (players[i].cardsLeft() == 0)
				return i;
		}
		return -1;
	}

	public void initialise() {
		//Create Deck of cards
		Deck d = new Deck();
		d.shuffle();
		//Deal cards to players
		Iterator<Card> it = d.iterator();
		int count = 0;
		while (it.hasNext()) {
			players[count % nosPlayers].addCard(it.next());
			it.remove();
			count++;
		}
		//Initialise Discards
		discards = new Hand();
		publicDiscards = new Hand();
		//Chose first player
		currentPlayer = 0;
		currentBid = new Bid();
		currentBid.setRank(Rank.TWO);
	}

	void clubStart() {    //checks who has 2 of clubs and makes them bid. Updates current player
		for (int i = 0; i < players.length; i++) {
			//for every player
			if (players[i].getHand().hasClubs()) {
				//if they have 2 of clubs
				currentBid = players[i].clubStart();
				//make them bid the card and set it to the current bid
				discards.add(currentBid.getHand());
				publicDiscards.add(currentBid.getHand());
				//add to discard pile
				System.out.println("First bid is 2 of Clubs by Player " + (i + 1));
				int nextPlayer = i + 1;
				if (nextPlayer == players.length) {
					nextPlayer = 0;
				}
				//sets the current player to be the one after the player who just played the 2 of clubs
				currentPlayer = nextPlayer;
			}
		}
	}

	public void playGame() {
		initialise();
		int turnCount = 0;
		Scanner in = new Scanner(System.in);
		//start with whoever has the 2 of clubs

		clubStart();
		//make whoever has 2 of clubs start

		boolean finished = false;
		while (!finished) {
			//Play a hand
			System.out.println("Player " + (currentPlayer + 1) + "'s turn");
			playTurn();
			System.out.println("Current actual discards = " + discards);
			System.out.println("Current player visible discards = " + publicDiscards);
			turnCount++;
			for (int j = 0; j < players.length; j++) {
				System.out.println("Player " + (j + 1) + " cards left: "
						+ players[j].cardsLeft());
			}
			System.out.println("\n");
			System.out.println("Turn " + turnCount + " Complete. Press any key to continue or enter Q to quit>");
			String str = in.nextLine();
			if (str.equals("Q") || str.equals("q") || str.equals("quit"))
				finished = true;
			int w = winner();
			if (w >= 0) {    //whoever forgot the equals sign, shame on you
				System.out.println("\n\n\nThe Winner is Player " + (w + 1));
				finished = true;
			}

		}
	}

	public static boolean isCheat(Bid b) {
		for (Card c : b.getHand()) {
			if (c.getRank() != b.r)
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		BasicCheat cheat = new BasicCheat();
		cheat.playGame();
	}
}
