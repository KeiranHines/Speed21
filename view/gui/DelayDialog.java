package view.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import controller.DelayController;

@SuppressWarnings("serial")
public class DelayDialog extends JDialog {
	private JSlider slider;
	private JLabel title = new JLabel("Change Delay", SwingConstants.CENTER);
	private JPanel buttonPanel = new JPanel();
	private JButton cancel = new JButton("Cancel");
	private JButton submit = new JButton("Submit");

	public DelayDialog(MainFrame main, DelayController delayC) {
		slider = new JSlider(SwingConstants.HORIZONTAL, 0, 500, main.getDelay());
		setLayout(new BorderLayout());
		slider.addChangeListener(delayC);
		slider.setMajorTickSpacing(100);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setToolTipText(String.valueOf(main.getDelay()));
		add(title, BorderLayout.NORTH);
		add(slider, BorderLayout.CENTER);

		cancel.setActionCommand("cancel");
		cancel.addActionListener(delayC);
		submit.setActionCommand("submit");
		submit.addActionListener(delayC);

		buttonPanel.add(cancel);
		buttonPanel.add(submit);
		add(buttonPanel, BorderLayout.SOUTH);
		pack();
	}

	public int getDelay() {
		return slider.getValue();
	}

	public void updateTooltip() {
		slider.setToolTipText(String.valueOf(slider.getValue()));
	}
}