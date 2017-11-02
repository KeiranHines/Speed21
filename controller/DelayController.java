package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.gui.DelayDialog;
import view.gui.MainFrame;

public class DelayController implements ActionListener, ChangeListener {
	private MainFrame main;
	private DelayDialog dialog;

	public DelayController(MainFrame main) {
		this.main = main;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
			case "changeDelay":
				dialog = new DelayDialog(main, this);
				dialog.setVisible(true);
				break;
			case "submit":
				main.setDelay(dialog.getDelay());
				dialog.dispose();
				break;
			case "cancel":
				dialog.dispose();
				break;
			default:
				System.err.println("ERROR AddPlayerController hit default case "
						+ event.getActionCommand());
		}
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		// TODO Auto-generated method stub
		dialog.updateTooltip();
	}
}