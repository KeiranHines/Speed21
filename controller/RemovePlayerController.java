package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import view.gui.MainFrame;

public class RemovePlayerController implements ActionListener {
	private MainFrame main;

	public RemovePlayerController(MainFrame main) {
		this.main = main;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (main.getPlayerID() == null) {
			JOptionPane.showMessageDialog(main, "Cannot remove what you do not have",
					"ERROR: No player in game", JOptionPane.ERROR_MESSAGE);
		} else {
			int selection = JOptionPane.showConfirmDialog(
					main,
					"Are you sure you want to remove "
							+ main.getGameEngine().getPlayer(main.getPlayerID())
									.getPlayerName(), "Confirm removal",
					JOptionPane.YES_NO_OPTION);
			if (selection == 0) {
				// Yes Selected
				main.removePlayer();
			}
		}
	}
}