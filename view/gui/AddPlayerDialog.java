package view.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * In assignment 2 this has been modified to remove the player ID input.
 */
@SuppressWarnings("serial")
public class AddPlayerDialog extends JDialog {
	private JTextField playerName = new JTextField(20);
	private JFormattedTextField points = new JFormattedTextField(
	    NumberFormat.getIntegerInstance());
	private JPanel input = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JButton cancel = new JButton("Cancel");
	private JButton submit = new JButton("Submit");

	public AddPlayerDialog(MainFrame main, ActionListener addPlayC) {
		super(main, "Add player", true);
		setLayout(new BorderLayout());
		input.setLayout(new GridLayout(2, 2));
		/*
		 * Letting the player input there own ID as this is a single player game.if
		 * this were multiplayer I would either generate an ID for them, or validate
		 * the id in the model to make sure it did not already exist.
		 */
		input.add(new JLabel("Player Name:"));
		input.add(playerName);
		input.add(new JLabel("Points:"));
		input.add(points);

		cancel.setActionCommand("cancel");
		cancel.addActionListener(addPlayC);
		submit.setActionCommand("submit");
		submit.addActionListener(addPlayC);

		buttonPanel.add(cancel);
		buttonPanel.add(submit);

		add(input, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(main);
	}

	@Override
	public String getName() {
		return playerName.getText();
	}

	public int getPoints() {
		if (points.getValue() != null)
			return ((Long) points.getValue()).intValue();
		return 0;
	}
}
