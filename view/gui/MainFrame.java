package view.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import controller.ExitController;
import controller.GuiGameEngineCallbackImpl;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	private String playerID = null;
	private PlayerCardsPanel playerPanel;
	private PlayerCardsPanel housePanel = new PlayerCardsPanel();
	private JPanel topPanel = new JPanel(new BorderLayout());
	private GameEngine engine;
	private Toolbar toolbar = new Toolbar(this);
	private int delay = 10;
	private List<GameEngineCallback> callbacks = new ArrayList<>();

	public MainFrame(GameEngine engine) {// GameEngine engine) {
		this.engine = engine;
		GameEngineCallback callback = new GuiGameEngineCallbackImpl(this);
		callbacks.add(callback);
		engine.addGameEngineCallback(callback);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Speed21");
		setLayout(new BorderLayout());
		setSize(800, 600);
		setJMenuBar(new Menubar(this));
		addWindowListener(new ExitController(this));
		topPanel.add(toolbar, BorderLayout.NORTH);
		topPanel.add(housePanel, BorderLayout.SOUTH);
		add(topPanel, BorderLayout.NORTH);
		setLocationRelativeTo(null);
	}

	public void clearCards() {
		playerPanel.clearCards();
		housePanel.clearCards();
	}

	public void createPlayerPanel(String name) {
		playerPanel = new PlayerCardsPanel(this, name);
		add(playerPanel, BorderLayout.SOUTH);
		revalidate();
	}

	public int getDelay() {
		return delay;
	}

	public GameEngine getGameEngine() {
		return engine;
	}

	public PlayerCardsPanel getHouseCardsPanel() {
		return housePanel;
	}

	// May need to update this to include multiple players in as2(ids)
	public PlayerCardsPanel getPlayerCardsPanel() {
		return playerPanel;
	}

	// Getting a reference to the player for this view
	public String getPlayerID() {
		return playerID;
	}

	public boolean removePlayer() {
		Boolean removed = engine.removePlayer(engine.getPlayer(playerID));
		if (removed) {
			playerID = null;
			remove(playerPanel);
			playerPanel = null;
			revalidate();
			repaint();
		} else {
			JOptionPane.showMessageDialog(this,
			    "Cannot remove Player, something went wrong",
			    "ERROR: cannot remove player", JOptionPane.ERROR_MESSAGE);
		}
		return removed;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void setPlayerID(String ID) {
		playerID = ID;
	}

	public void showDealButton(boolean bool) {
		toolbar.ShowDealButton(bool);
		revalidate();
	}

	public Collection<GameEngineCallback> getCallbacks() {
		return callbacks;
	}
}
