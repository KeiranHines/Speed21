package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.JOptionPane;

import model.SimplePlayer;
import view.gui.AddPlayerDialog;
import view.gui.MainFrame;

public class AddPlayerController implements ActionListener {
	private AddPlayerDialog dialog;
	private MainFrame main;

	public AddPlayerController(MainFrame main) {
		this.main = main;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case "addPlayer":
			// Changed to validate if one player registered per view
			if (main.getPlayerID() != null) {
				JOptionPane.showMessageDialog(dialog,
				    "ERROR: This client already has a player",
				    "cannot add another player to this client",
				    JOptionPane.ERROR_MESSAGE);
			} else {
				dialog = new AddPlayerDialog(main, this);
				dialog.setVisible(true);
			}
			break;
		case "submit":
			String name = dialog.getName();
			String id = UUID.randomUUID().toString();
			int points = dialog.getPoints();
			boolean error = false;
			if (name.isEmpty()) {
				error = true;
				JOptionPane.showMessageDialog(dialog, "ERROR: No name specified",
				    "No Name Specified", JOptionPane.ERROR_MESSAGE);
			}
			if (id.isEmpty()) {
				error = true;
				JOptionPane.showMessageDialog(dialog, "No id Specified",
				    "ERROR: No id specified", JOptionPane.ERROR_MESSAGE);
			}
			if (points == 0) {
				error = true;
				JOptionPane.showMessageDialog(dialog, "ERROR: No points specified",
				    "No points Specified", JOptionPane.ERROR_MESSAGE);
			}
			if (points < 0) {
				error = true;
				JOptionPane.showMessageDialog(dialog,
				    "Points should be greater than 0",
				    "ERROR: points should be positive", JOptionPane.ERROR_MESSAGE);
			}
			if (!error) {
				main.getGameEngine().addPlayer(new SimplePlayer(id, name, points));
				main.setPlayerID(id);
				main.createPlayerPanel(name);
				dialog.dispose();
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