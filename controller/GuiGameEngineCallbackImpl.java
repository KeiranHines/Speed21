package controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.gui.MainFrame;

/*
 * For assignment two added the Player check to output correctly
 */
public class GuiGameEngineCallbackImpl implements GameEngineCallback {
	// not really sure what I need in here
	private MainFrame main;
	private static Logger logger = Logger
	    .getLogger(GuiGameEngineCallbackImpl.class.getName());

	public GuiGameEngineCallbackImpl(MainFrame main) {
		this.main = main;
		logger.setLevel(Level.ALL);
		logger.getParent().getHandlers()[0].setLevel(Level.FINE);
	}

	@Override
	public void bustCard(Player player, final PlayingCard card, GameEngine engine) {
		if (player.getPlayerId().equals(main.getPlayerID())) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {

					main.getPlayerCardsPanel().addCard(card, true);
				}
			});
		} else {
			logger.log(Level.FINE, "Player: " + player.getPlayerName() + ", card="
			    + card.toString() + " ... YOU BUSTED!");
		}
	}

	@Override
	public void houseBustCard(final PlayingCard card, GameEngine engine) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				main.getHouseCardsPanel().addCard(card, true);
			}
		});
	}

	@Override
	public void houseResult(final int result, GameEngine engine) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JOptionPane.showMessageDialog(main, "House result is: " + result);
			}
		});
	}

	@Override
	public void nextCard(Player player, final PlayingCard card, GameEngine engine) {
		if (player.getPlayerId().equals(main.getPlayerID())) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					main.getPlayerCardsPanel().addCard(card, false);
					main.revalidate();
				}
			});
		} else {
			logger.log(Level.FINE, "Player: " + player.getPlayerName() + ", card="
			    + card.toString());
		}
	}

	@Override
	public void nextHouseCard(final PlayingCard card, GameEngine engine) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				main.getHouseCardsPanel().addCard(card, false);
			}
		});
	}

	@Override
	public void result(Player player, int result, GameEngine engine) {
		if (player.getPlayerId().equals(main.getPlayerID())) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					main.getPlayerCardsPanel().updateStatusBar();
				}
			});
		} else {
			logger.log(Level.INFO, "Player: " + player.getPlayerName()
			    + ", final result=" + result);
		}
	}
}