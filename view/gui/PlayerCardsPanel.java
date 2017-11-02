package view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import model.interfaces.Player;
import model.interfaces.PlayingCard;

@SuppressWarnings("serial")
public class PlayerCardsPanel extends JPanel {
	// cardHolder will need at most 12 cards, 11 To get to 21 and one bust
	private JPanel cardHolder = new JPanel(new GridLayout(1, 12));
	private CardImage[] cards = new CardImage[12];
	private int cardCount = 0;
	private JLabel title;
	private JPanel statusbar = new JPanel();
	private MainFrame main;
	private JLabel points, result, bet;
	private boolean bust = false;
	private int total = 0;

	// If no name specified its the house panel
	public PlayerCardsPanel() {
		setLayout(new BorderLayout());
		title = new JLabel("House cards");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		add(title, BorderLayout.SOUTH);
		cardHolder.setPreferredSize(new Dimension(0, 100));
		cardHolder.setBackground(new Color(80,161,98));
		createBlankCards(new Color(80,161,98));
		add(cardHolder, BorderLayout.CENTER);
	}

	public PlayerCardsPanel(MainFrame main, String name) {
		this.main = main;
		setLayout(new BorderLayout());
		title = new JLabel(name + "'s cards");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		add(title, BorderLayout.NORTH);
		cardHolder.setPreferredSize(new Dimension(0, 100));
		cardHolder.setBackground(new Color(16,96,34));
		createBlankCards(new Color(16,96,34));
		add(cardHolder, BorderLayout.CENTER);
		createStatusbar();
		add(statusbar, BorderLayout.SOUTH);
	}

	public void addCard(PlayingCard card, boolean bust) {
		// Clearing the cards if they weren't cleared from the previous game
		if (this.bust || total == 21) {
			clearCards();
		}
		cards[cardCount].updateCard(card.getSuit().toString(), card.getValue()
		    .toString(), bust);
		cardCount++;
		total += card.getScore();
		cardHolder.updateUI();
		updateUI();
		this.bust = bust;
		// Removing the deal button because the cards have been dealt
		if (this.bust || total == 21) {
			if (main != null) {
				main.showDealButton(false);
			}
		}
	}

	public void clearCards() {
		for (int i = 0; i < 12; i++) {
			cards[i].clearCard();
		}
		total = 0;
		cardCount = 0;
		cardHolder.updateUI();
	}

	private void createBlankCards(Color color) {
		// Creating blank cards to be updated as dealt
		for (int i = 0; i < 12; i++) {
			cards[i] = new CardImage(color);
			cardHolder.add(cards[i]);
		}
	}

	public void createStatusbar() {
		statusbar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusbar.setLayout(new BoxLayout(statusbar, BoxLayout.X_AXIS));
		points = new JLabel("Points: "
		    + main.getGameEngine().getPlayer(main.getPlayerID()).getPoints() + "  ");
		bet = new JLabel("Bet: "
		    + main.getGameEngine().getPlayer(main.getPlayerID()).getBet() + "  ");
		result = new JLabel("Result: "
		    + main.getGameEngine().getPlayer(main.getPlayerID()).getResult() + "  ");
		points.setHorizontalAlignment(SwingConstants.LEFT);
		bet.setHorizontalAlignment(SwingConstants.LEFT);
		result.setHorizontalAlignment(SwingConstants.LEFT);
		statusbar.add(points);
		statusbar.add(bet);
		statusbar.add(result);
	}

	public void updateStatusBar() {
		Player player = main.getGameEngine().getPlayer(main.getPlayerID());
		points.setText("Points: " + player.getPoints() + "  ");
		bet.setText("Bet: " + player.getBet() + "  ");
		result.setText("Result: " + player.getResult() + "  ");
		statusbar.updateUI();
	}
}