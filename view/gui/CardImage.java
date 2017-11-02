package view.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CardImage extends JPanel {
	private JLabel value, suit, of, bust;
	private Color color;

	public CardImage(Color color) {
		this.color = color;
		value = new JLabel();
		add(value);
		of = new JLabel();
		add(of);
		suit = new JLabel();
		add(suit);
		bust = new JLabel("(BUST)");
		bust.setVisible(false);
		add(bust);
		setBackground(color);
	}

	public void clearCard() {
		value.setText("");
		suit.setText("");
		of.setText("");
		value.setForeground(Color.BLACK);
		suit.setForeground(Color.BLACK);
		of.setForeground(Color.BLACK);
		bust.setVisible(false);
		setBackground(color);
		setBorder(null);
	}

	public void updateCard(String suit, String value, boolean bust) {
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEtchedBorder());
		this.value.setText(value);
		this.suit.setText(suit);
		of.setText("of");
		if (suit.equalsIgnoreCase("Hearts") || suit.equalsIgnoreCase("Diamonds")) {
			this.value.setForeground(Color.RED);
			this.suit.setForeground(Color.RED);
			of.setForeground(Color.RED);
		}
		if (bust) {
			this.bust.setVisible(true);
		}
	}
}
