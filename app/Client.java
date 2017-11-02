package app;

import java.io.IOException;

import javax.swing.JOptionPane;

import model.client.GameEngineClientStub;
import view.gui.MainFrame;
import view.gui.ServerConnectionDialog;

public class Client {

	public static void main(String[] args) {
		GameEngineClientStub engine = null;

		while (engine == null) {
			ServerConnectionDialog connectionDialog = new ServerConnectionDialog();
			int result = JOptionPane.showConfirmDialog(null, connectionDialog,
			    "Enter host and port to connect to", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				try {
					engine = new GameEngineClientStub(connectionDialog.getHost(),
					    connectionDialog.getPort());

				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error connecting to the server",
					    "Error connecting to the server", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				System.exit(0);
			}
		}
		MainFrame main = new MainFrame(engine);
		main.setVisible(true);

	}
}
