package question2;

public class StrategyFactory {

	public Strategy setStrategy(String input) {

		input = input.toLowerCase();
		switch (input) {
			case "h":
				return new HumanStrategy();
			case "t":
				return new ThinkerStrategy();
			case "m":
				return new MyStrategy();
			default:
				return new BasicStrategy();
		}
	}
}
