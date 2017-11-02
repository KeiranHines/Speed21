package view.gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ServerConnectionDialog extends JPanel {
	private JTextField host = new JTextField("127.0.0.1", 15);
	private JTextField port = new JTextField("7234", 5);

	public ServerConnectionDialog() {
		setLayout(new GridLayout(2, 2));
		add(new JLabel("Host:"));
		add(host);
		add(new JLabel("Port: "));
		add(port);
	}

	public String getHost() {
		return host.getText();
	}

	public int getPort() {
		return Integer.parseInt(port.getText());
	}
}
