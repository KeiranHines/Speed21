package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.interfaces.Player;
import view.gui.MainFrame;
import view.gui.PlaceBetDialog;

public class PlaceBetController implements ActionListener {
	private PlaceBetDialog dialog;
	private MainFrame main;

	public PlaceBetController(MainFrame main) {
		this.main = main;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
			case "placeBet":
				if (main.getPlayerID() == null) {
					JOptionPane.showMessageDialog(dialog, "Please add a player",
							"ERROR: No player in game", JOptionPane.ERROR_MESSAGE);
				} else {
					dialog = new PlaceBetDialog(main, this);
					dialog.setVisible(true);
				}
				break;
			case "submit":
				if (dialog.getBetValue() == 0
						&& main.getGameEngine().getPlayer(main.getPlayerID()).getBet() == 0) {
					JOptionPane.showMessageDialog(dialog, "ERROR: No bet specified",
							"No bet Specified", JOptionPane.ERROR_MESSAGE);
				} else {
					boolean betValid;
					Player player = main.getGameEngine().getPlayer(main.getPlayerID());
					betValid = main.getGameEngine()
							.placeBet(player, dialog.getBetValue());
					if (betValid) {
						if (dialog.getBetValue() == 0) {
							main.showDealButton(false);
						} else {
							main.showDealButton(true);
						}
						main.getPlayerCardsPanel().updateStatusBar();
						dialog.dispose();
					} else {
						JOptionPane
								.showMessageDialog(dialog,
										"bet is not valid; " + player.getPoints()
												+ " points available", "ERROR: bet not valid",
										JOptionPane.ERROR_MESSAGE);
					}
				}
				break;
			case "cancel":
				dialog.dispose();
				break;
			default:
				System.err.println("ERROR AddPlayerController hit default case "
						+ event.getActionCommand());
		}
	}
}