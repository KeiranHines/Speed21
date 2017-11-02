package model;

import java.io.Serializable;

import model.interfaces.PlayingCard;

@SuppressWarnings("serial")
public class PlayingCardImpl implements PlayingCard, Serializable {

	private Suit suit;
	private Value value;
	private int score;

	public PlayingCardImpl(Suit s, Value v) {
		suit = s;
		value = v;
		// Using the enum value postion to determine card value. provided enum
		// is in order this will work
		score = v.ordinal() + 1;
		if (score > 10) {
			score = 10;
		}
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public Suit getSuit() {
		return suit;
	}

	@Override
	public Value getValue() {
		return value;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("Suit: " + suit);
		string.append(", Value: " + value);
		string.append(", Score=" + score);
		return string.toString();
	}
}