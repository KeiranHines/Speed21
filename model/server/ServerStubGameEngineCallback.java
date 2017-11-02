package model.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.commands.callback.AbstractCallbackCommand;
import model.commands.callback.BustCardCommand;
import model.commands.callback.HouseBustCardCommand;
import model.commands.callback.HouseResultCommand;
import model.commands.callback.NextCardCommand;
import model.commands.callback.NextHouseCardCommand;
import model.commands.callback.ResultCommand;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

public class ServerStubGameEngineCallback implements GameEngineCallback {

	private static Logger logger = Logger
	    .getLogger(ServerStubGameEngineCallback.class.getName());
	private InetAddress host;
	private int port;
	private Socket sock;
	private ObjectOutputStream out;
	private boolean error;

	public ServerStubGameEngineCallback(InetAddress host, int port) {
		logger.setLevel(Level.ALL);
		logger.getParent().getHandlers()[0].setLevel(Level.FINE);
		this.host = host;
		this.port = port;
		try {
			this.sock = new Socket(host, port);
			this.out = new ObjectOutputStream(sock.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		// This will always be the first method called from the callbacks. So if the
		// client has force disconnected error will check and remove them from the
		// game
		error = sendCommand(new NextCardCommand(player, card));
		if (error) {
			engine.removeGameEngineCallback(this);
		}
	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		sendCommand(new BustCardCommand(player, card));
	}

	@Override
	public void result(Player player, int result, GameEngine engine) {
		sendCommand(new ResultCommand(player, result));
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		sendCommand(new NextHouseCardCommand(card));
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		sendCommand(new HouseBustCardCommand(card));
	}

	@Override
	public void houseResult(int result, GameEngine engine) {
		sendCommand(new HouseResultCommand(result));
	}

	private boolean sendCommand(AbstractCallbackCommand command) {
		
		try {
			out.writeObject(command);
			out.reset();
			return false;
		} catch (SocketException e) {
			return true;
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error writing over the network" + " Command: "
			    + command.getClass());
			e.printStackTrace();
			return true;
		}
	}

	public InetAddress getHost() {
		return this.host;
	}

	public int getPort() {
		return this.port;
	}
}
