package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

public class GameEngineImpl implements GameEngine {
	private Map<String, Player> players = new HashMap<String, Player>();
	// I don't like that I had to make this protected
	protected List<GameEngineCallback> callbackList = new ArrayList<GameEngineCallback>();
	private LinkedList<PlayingCard> deck = new LinkedList<PlayingCard>();
	private int houseResult; // For as1 house does not need to be its own class.
	private int houseDelay = 0;

	public GameEngineImpl() {
		// Creating a deck of cards so that each card is unique.
		for (PlayingCard.Suit s : PlayingCard.Suit.values()) {
			for (PlayingCard.Value v : PlayingCard.Value.values()) {
				PlayingCard c = new PlayingCardImpl(s, v);
				deck.add(c);
			}
		}
		assert deck.size() == PlayingCard.DECK_SIZE : "The deck is not "
		    + PlayingCard.DECK_SIZE + " cards";
		Collections.shuffle(deck);
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		callbackList.add(gameEngineCallback);
		assert callbackList.contains(gameEngineCallback) : "Error adding callback";
	}

	@Override
	public void addPlayer(Player player) {
		if (player == null) {
			assert player == null : "Error trying to add null player";
			return;
		}
		if (player.getPlayerId() == null) {
			assert player.getPlayerId() != null : "Error trying to add player with null id";

			return;
		}
		if (player.getPlayerName() == null) {
			assert player.getPlayerName() != null : "Error trying to add player with null Name";
			return;
		}
		if (player.getPoints() <= 0) {
			assert player.getPoints() > 0 : "Error player points needs to be positive when creating a player";
			return;
		}
		/*
		 * I would like to be able to validate if that player already exists but If
		 * I do that it will need to be in the view because I can't return from this
		 * false if the player id was already in use, hence the commented out
		 * assertion
		 */

		// assert !players.containsKey(player.getPlayerId()) :
		// "There is already a player with that ID";

		players.put(player.getPlayerId(), player);
		assert players.containsKey(player.getPlayerId()) : "Error adding player";
	}

	@Override
	public void calculateResult() {
		houseResult = 0;
		dealHouse(houseDelay);
		assert houseResult <= BUST_LEVEL : "Error house is over the bust level";
		for (Player p : getAllPlayers()) {
			// Only calculating results for players that bet. Not really needed
			if (p.getBet() == 0) {
				break;
			}
			if (p.getResult() < houseResult) {
				p.setPoints(p.getPoints() - p.getBet());
				p.resetBet();
			} else if (p.getResult() > houseResult) {
				p.setPoints(p.getPoints() + p.getBet());
				p.resetBet();
			} else {
				p.resetBet();
			}
		}
	}

	@Override
	// I would have made this private as it should only be called by
	// calculateResult
	public void dealHouse(int delay) {
		assert houseResult == 0 : "House result is not 0 when starting deal";
		if (deck.isEmpty()) {
			newDeck();
		}
		PlayingCard card = deck.remove();
		while (houseResult + card.getScore() <= BUST_LEVEL) {
			for (GameEngineCallback callback : callbackList) {
				callback.nextHouseCard(card, this);
			}
			houseResult += card.getScore();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (deck.isEmpty()) {
				newDeck();
			}
			card = deck.remove();
		}
		assert houseResult <= BUST_LEVEL : "House result is over the bust level";
		if (houseResult != BUST_LEVEL) {
			for (GameEngineCallback callback : callbackList) {
				callback.houseBustCard(card, this);
			}
		}
		for (GameEngineCallback callback : callbackList) {
			callback.houseResult(houseResult, this);
		}
	}

	@Override
	public void dealPlayer(Player player, int delay) {
		houseDelay = delay;
		if (delay < 0) {
			assert delay >= 0 : "Error delay can't be negative";
			return;
		}
		if (player == null) {
			assert player != null : "Error no such player";
			return;
		}
		if (player.getBet() != 0) {
			if (deck.isEmpty()) {
				newDeck();
			}
			PlayingCard card = deck.remove();
			int result = 0;
			while (result + card.getScore() <= BUST_LEVEL) {
				assert !callbackList.isEmpty() : "No callbacks";
				for (GameEngineCallback callback : callbackList) {
					callback.nextCard(player, card, GameEngineImpl.this);
				}
				result += card.getScore();
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (deck.isEmpty()) {
					newDeck();
				}
				card = deck.remove();
			}
			assert result <= BUST_LEVEL : "result is over the bust level";
			if (result != BUST_LEVEL) {
				for (GameEngineCallback callback : callbackList) {
					callback.bustCard(player, card, this);
				}
			}
			player.setResult(result);
			for (GameEngineCallback callback : callbackList) {
				callback.result(player, result, this);
			}
		}
	}

	@Override
	public Collection<Player> getAllPlayers() {
		return players.values();

	}

	@Override
	public Player getPlayer(String id) {
		// returns null if the player does not exist
		return players.get(id);
	}

	@Override
	public Deque<PlayingCard> getShuffledDeck() {
		assert deck != null : "Error the deck does not exist";
		return deck;
	}

	private void newDeck() {
		assert deck.size() == 0 : "Error getting a new deck before the deck is empty";
		for (PlayingCard.Suit s : PlayingCard.Suit.values()) {
			for (PlayingCard.Value v : PlayingCard.Value.values()) {
				PlayingCard c = new PlayingCardImpl(s, v);
				deck.add(c);
			}
		}
		assert deck.size() == PlayingCard.DECK_SIZE : "new deck is not "
		    + PlayingCard.DECK_SIZE;
		Collections.shuffle(deck);
	}

	@Override
	public boolean placeBet(Player player, int bet) {
		assert player != null : "Error that player does not exist";
		if (player != null)
			return player.placeBet(bet);
		else
			return false;
	}

	@Override
	public void removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		callbackList.remove(gameEngineCallback);
		assert !callbackList.contains(gameEngineCallback) : "Error removing callback";
	}

	@Override
	public boolean removePlayer(Player player) {
		if (player == null) {
			assert player != null : "Error trying to remove a null player";
			return false;
		}
		if (player.getPlayerId() == null) {
			assert player.getPlayerId() != null : "Error player Id to remove is null";
			return false;
		}
		return players.remove(player.getPlayerId(), player);
	}
}