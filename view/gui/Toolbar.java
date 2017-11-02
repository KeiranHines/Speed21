package view.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import controller.AddPlayerController;
import controller.DealController;
import controller.ListPlayersController;
import controller.PlaceBetController;
import controller.RemovePlayerController;

@SuppressWarnings("serial")
public class Toolbar extends JPanel {
	private JToolBar toolbar = new JToolBar();
	private ActionListener addPlayC;
	private ActionListener removePlayC;
	private ActionListener listPlayC;
	private ActionListener placeBetC;
	private ActionListener dealC;
	private JButton deal;

	public Toolbar(MainFrame main) {
		super(new BorderLayout());
		addPlayC = new AddPlayerController(main);
		removePlayC = new RemovePlayerController(main);
		listPlayC = new ListPlayersController(main);
		placeBetC = new PlaceBetController(main);
		dealC = new DealController(main);
		toolbar.add(createButton("Add Player", "Add player", "addPlayer", addPlayC,
		    KeyEvent.VK_A));
		toolbar.add(createButton("Place Bet", "Rlace bet", "placeBet", placeBetC,
		    KeyEvent.VK_B));
		toolbar.add(createButton("Remove Player", "Remove Player", "removePlayer",
		    removePlayC, KeyEvent.VK_R));
		toolbar.add(createButton("List Players", "List Players", "listPlayers",
		    listPlayC, KeyEvent.VK_L));
		deal = createButton("Deal", "deal to player", "deal", dealC, KeyEvent.VK_D);
		add(toolbar, BorderLayout.NORTH);
	}

	private JButton createButton(String text, String tooltip, String command,
	    ActionListener al, int Mnemonic) {
		JButton button = new JButton();
		button.setToolTipText(tooltip);
		button.setText(text);
		button.setActionCommand(command);
		button.addActionListener(al);
		button.setMnemonic(Mnemonic);
		return button;
	}

	public void ShowDealButton(Boolean bool) {
		if (bool) {
			toolbar.add(deal);
		} else {
			toolbar.remove(deal);
		}
		toolbar.updateUI();
		toolbar.revalidate();
	}
}