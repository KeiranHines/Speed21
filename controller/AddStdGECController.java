package controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import model.GameEngineCallbackImpl;
import model.interfaces.GameEngineCallback;
import view.gui.MainFrame;

public class AddStdGECController implements ItemListener {
	private MainFrame main;
	private GameEngineCallback stdOut = new GameEngineCallbackImpl();

	public AddStdGECController(MainFrame main) {
		this.main = main;
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == 1) {
			main.getGameEngine().addGameEngineCallback(stdOut);
			System.out.println("Added a console logger for debugging");
		}
		if (event.getStateChange() == 2) {
			main.getGameEngine().removeGameEngineCallback(stdOut);
			System.out.println("Removing the console logger");
		}
	}
}