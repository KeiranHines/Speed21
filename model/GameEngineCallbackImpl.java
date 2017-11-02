package model;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

//This class is for the text based player implementation, if you are looking for 
//The GUI based callback please check controller.GUIGameEngineCallback
public class GameEngineCallbackImpl implements GameEngineCallback {
	private static Logger logger = Logger.getLogger(GameEngineCallbackImpl.class
	    .getName());

	public GameEngineCallbackImpl() {
		// What is my purpose
		logger.setLevel(Level.ALL);
		logger.getParent().getHandlers()[0].setLevel(Level.FINE);
	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		logger.log(Level.FINE, "Player: " + player.getPlayerName() + ", card="
		    + card.toString() + " ... YOU BUSTED!");
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		logger.log(Level.FINE, "Player: " + "House" + ", card=" + card.toString()
		    + " ... YOU BUSTED!");
	}

	@Override
	public void houseResult(int result, GameEngine engine) {
		logger.log(Level.INFO, "Player: " + "House" + ", final result=" + result);
	}

	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		logger.log(Level.FINE, "Player: " + player.getPlayerName() + ", card="
		    + card.toString());
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		logger.log(Level.FINE, "Player: " + "House" + ", card=" + card.toString());
	}

	@Override
	public void result(Player player, int result, GameEngine engine) {
		// This logger prints twice Maybe due to info?
		logger.log(Level.INFO, "Player: " + player.getPlayerName()
		    + ", final result=" + result);
	}
}