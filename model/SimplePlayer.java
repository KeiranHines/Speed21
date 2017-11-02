package model;

import java.io.Serializable;

import model.interfaces.Player;

//Made serializable for assignment 2
@SuppressWarnings("serial")
public class SimplePlayer implements Player, Serializable {

	private String playerId;
	private String playerName;
	private int points;
	private int bet;
	private int result = 0;

	public SimplePlayer(String playerId, String playerName, int points) {
		this.playerId = playerId;
		this.playerName = playerName;
		this.points = points;
	}

	@Override
	public int getBet() {
		return bet;
	}

	@Override
	public String getPlayerId() {
		return playerId;
	}

	@Override
	public String getPlayerName() {
		return playerName;
	}

	@Override
	public int getPoints() {
		return points;
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public boolean placeBet(int bet) {
		assert bet >= 0 : "Bet is less than 0";
		if (bet < 0)
			return false;
		if (bet <= points) {
			this.bet = bet;
			return true;
		}
		return false;
	}

	@Override
	public void resetBet() {
		bet = 0;
	}

	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public void setResult(int result) {
		this.result = result;
	}

	// Only used in the text based GUI not really needed.
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("id=" + playerId);
		string.append(", name=" + playerName);
		string.append(", points=" + points);
		string.append(", result=" + result);
		string.append(", bet=" + bet);
		return string.toString();
	}
}