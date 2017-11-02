package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.interfaces.Player;
import view.gui.MainFrame;

public class DealController implements ActionListener {
	private MainFrame main;

	public DealController(MainFrame main) {
		this.main = main;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// Getting the first, and at this stage only player
		// main.clearCards();
		new Thread() {
			@Override
			public void run() {
				for (final Player p : main.getGameEngine().getAllPlayers()) {
					main.getGameEngine().dealPlayer(p, main.getDelay());
				}
				main.getGameEngine().calculateResult();
				main.getPlayerCardsPanel().updateStatusBar();
				main.showDealButton(false);
			}
		}.start();
	}
}