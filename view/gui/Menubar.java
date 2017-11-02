package view.gui;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.AddPlayerController;
import controller.AddStdGECController;
import controller.DelayController;
import controller.PlaceBetController;
import controller.RemovePlayerController;
import controller.ExitController;

@SuppressWarnings("serial")
public class Menubar extends JMenuBar {
	private ActionListener addPlayC, placeBetC, removePlayC, exitC, delayC;
	private ItemListener addStdGEC;
	private JMenu fileMenu = new JMenu("File");
	private JMenu helpMenu = new JMenu("Help");
	private JMenu debug = new JMenu("Debug");
	private JCheckBoxMenuItem print = new JCheckBoxMenuItem("Show Std.out GEC");

	public Menubar(MainFrame main) {
		addPlayC = new AddPlayerController(main);
		placeBetC = new PlaceBetController(main);
		removePlayC = new RemovePlayerController(main);
		exitC = new ExitController(main);
		delayC = new DelayController(main);
		addStdGEC = new AddStdGECController(main);

		fileMenu.add(createMenuItem("Add Player", "addPlayer", addPlayC,
		    KeyEvent.VK_A));
		fileMenu.add(createMenuItem("Place Bet", "placeBet", placeBetC,
		    KeyEvent.VK_B));
		fileMenu.add(createMenuItem("Remove Player", "removePlayer", removePlayC,
		    KeyEvent.VK_R));
		fileMenu.addSeparator();
		fileMenu.add(createMenuItem("Exit", "exit", exitC, KeyEvent.VK_E));
		fileMenu.setMnemonic(KeyEvent.VK_F);
		add(fileMenu);
		debug.add(createMenuItem("Change Delay", "changeDelay", delayC,
		    KeyEvent.VK_D));
		print.addItemListener(addStdGEC);
		debug.add(print);
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.add(debug);
		add(helpMenu);
	}

	private JMenuItem createMenuItem(String label, String ac, ActionListener al,
	    int mnemonic) {
		JMenuItem item = new JMenuItem(label);
		item.setActionCommand(ac);
		item.addActionListener(al);
		item.setMnemonic(mnemonic);
		return item;
	}
}