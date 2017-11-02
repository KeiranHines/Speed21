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
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class PlaceBetDialog extends JDialog {
	private JPanel input = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JButton cancel = new JButton("Cancel");
	private JButton submit = new JButton("Submit");
	private JFormattedTextField betValue = new JFormattedTextField(
	    NumberFormat.getIntegerInstance());

	public PlaceBetDialog(MainFrame main, ActionListener betC) {
		super(main, "Place Bet", true);
		setLayout(new BorderLayout());
		input.setLayout(new GridLayout(2, 1));
		input.add(new JLabel("Bet Amount", SwingConstants.CENTER));
		input.add(betValue);
		getRootPane().setDefaultButton(submit);

		cancel.setActionCommand("cancel");
		cancel.addActionListener(betC);
		submit.setActionCommand("submit");
		submit.addActionListener(betC);

		buttonPanel.add(cancel);
		buttonPanel.add(submit);

		add(input, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(main);
	}

	public int getBetValue() {
		if (betValue.getValue() != null)
			return ((Long) betValue.getValue()).intValue();
		return 0;
	}
}