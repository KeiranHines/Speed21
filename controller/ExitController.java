package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import model.interfaces.GameEngineCallback;
import view.gui.MainFrame;

public class ExitController extends WindowAdapter implements ActionListener {
	private MainFrame main;

	public ExitController(MainFrame main) {
		this.main = main;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		exitConfirmations();
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		exitConfirmations();
	}

	private void exitConfirmations() {
		int ans = JOptionPane.showConfirmDialog(null,
		    "Are you sure you want to exit", "Confirm exit",
		    JOptionPane.YES_NO_OPTION);
		if (ans == 0) {
			if (main.getPlayerID() != null) {
				main.getGameEngine().removePlayer(
				    main.getGameEngine().getPlayer(main.getPlayerID()));
			}
			for (GameEngineCallback callback : main.getCallbacks()) {
				main.getGameEngine().removeGameEngineCallback(callback);
			}
			System.exit(0);
		}
	}
}