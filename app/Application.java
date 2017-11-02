package app;

import javax.swing.SwingUtilities;

import model.GameEngineImpl;
import model.interfaces.GameEngine;
import view.gui.MainFrame;

public class Application {
	final static GameEngine engine = new GameEngineImpl();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainFrame main = new MainFrame(engine);
				main.setVisible(true);
			}
		});
	}
}