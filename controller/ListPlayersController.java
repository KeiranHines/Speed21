package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import model.interfaces.Player;
import view.gui.MainFrame;

public class ListPlayersController implements ActionListener {
	private MainFrame main;

	public ListPlayersController(MainFrame main) {
		this.main = main;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Collection<Player> players = main.getGameEngine().getAllPlayers();
		if (players.size() == 0) {
			System.out
			    .println("There is no players in the game, maybe you should add"
			        + " a player or this will get rather boring");
		} else {
			System.out
			    .printf("%s\t\t%s\t%s\t%s\n", "Name", "Points", "Bet", "Result");
			System.out.println("--------------------------------------");
			for (Player p : players) {
				if (!p.getPlayerId().equals(main.getPlayerID())) {
					System.out.printf("%s\t%d\t%d\t%d\n", p.getPlayerName(),
					    p.getPoints(), p.getBet(), p.getResult());
				} else {
					System.out.printf("%s\t%d\t%d\t%d*\n", p.getPlayerName(),
					    p.getPoints(), p.getBet(), p.getResult());
				}
			}
			System.out.println("--------------------------------------");
		}
	}
}
