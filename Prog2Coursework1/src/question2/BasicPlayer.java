package question2;

import java.util.ArrayList;

public class BasicPlayer implements Player {

	private Hand pHand;
	private CardGame game;
	private Strategy strat;
	private BasicCheat cheat;

	BasicPlayer(Strategy strat, BasicCheat cheat) {
		pHand = new Hand();
		this.strat = strat;
		this.game = cheat;
	}

	@Override
	public void addCard(Card c) {

		pHand.add(c);
	}

	@Override
	public void addHand(Hand h) {
		pHand.add(h);
	}

	@Override
	public int cardsLeft() {
		return pHand.size();
	}

	@Override
	public void setGame(CardGame g) {
		this.game = g;
	}

	@Override
	public void setStrategy(Strategy s) {
		this.strat = s;
	}


	public Strategy getStrategy() {
		return this.strat;
	}

	@Override
	public Bid playHand(Bid b) {
		boolean test = this.strat.cheat(b, pHand);
		return strat.chooseBid(b, pHand, test);
	}

	@Override
	public boolean callCheat(Bid b) {
		return this.strat.callCheat(pHand, b);
	}

	@Override
	public Hand getHand() {
		return pHand;
	}

	@Override
	public Bid clubStart() {
		return this.strat.clubStartHand(pHand);
	}


}
