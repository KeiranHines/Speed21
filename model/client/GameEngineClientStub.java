package model.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collection;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.SimplePlayer;
import model.commands.gameEngine.AbstractEngineCommand;
import model.commands.gameEngine.AddGECCommand;
import model.commands.gameEngine.AddPlayerCommand;
import model.commands.gameEngine.CalculateResultCommand;
import model.commands.gameEngine.DealHouseCommand;
import model.commands.gameEngine.DealPlayerCommand;
import model.commands.gameEngine.DisconnectCommand;
import model.commands.gameEngine.GetAllPlayersCommand;
import model.commands.gameEngine.GetPlayerCommand;
import model.commands.gameEngine.GetShuffledDeckCommand;
import model.commands.gameEngine.PlaceBetCommand;
import model.commands.gameEngine.RemovePlayerCommand;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import model.server.GameEngineServerStub;

public class GameEngineClientStub implements GameEngine {
	private static Logger logger = Logger.getLogger(GameEngineServerStub.class
	    .getName());
	private Socket sock;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ClientGameEngineCallbackServer cgecs;

	public GameEngineClientStub(String host, int port) throws IOException {
		logger.setLevel(Level.ALL);
		logger.getParent().getHandlers()[0].setLevel(Level.FINE);

		sock = new Socket(host, port);
		out = new ObjectOutputStream(sock.getOutputStream());
		in = new ObjectInputStream(sock.getInputStream());

		cgecs = new ClientGameEngineCallbackServer(this);
		sendCommand(new AddGECCommand(cgecs.getHost(), cgecs.getPort()));
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		cgecs.addGEC(gameEngineCallback);
	}

	@Override
	public void addPlayer(Player player) {
		sendCommand(new AddPlayerCommand(player));
	}

	@Override
	public void calculateResult() {
		sendCommand(new CalculateResultCommand());
	}

	@Override
	// My implementations do not deal directly to the house at all, this should be
	// done by the calculate result method
	public void dealHouse(int delay) {
		sendCommand(new DealHouseCommand(delay));
	}

	@Override
	public void dealPlayer(Player player, int delay) {
		sendCommand(new DealPlayerCommand(player, delay));
	}

	// I don't like that I have to do this
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Player> getAllPlayers() {
		sendCommand(new GetAllPlayersCommand());
		return (Collection<Player>) recieveObject();
	}

	@Override
	public Player getPlayer(String id) {
		sendCommand(new GetPlayerCommand(id));
		// Should probably create an abstract Player class
		return (SimplePlayer) recieveObject();
	}

	// I don't like that I have to do this
	@SuppressWarnings("unchecked")
	@Override
	public Deque<PlayingCard> getShuffledDeck() {
		sendCommand(new GetShuffledDeckCommand());
		return (Deque<PlayingCard>) recieveObject();
	}

	@Override
	public boolean placeBet(Player player, int bet) {
		sendCommand(new PlaceBetCommand(player, bet));
		return recieveBoolean();
	}

	@Override
	public void removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		// ClientGameEngineCallbackServer callback = callbackList
		// .get(gameEngineCallback);
		// callback.disconnect();
		// callbackList.remove(gameEngineCallback);
		// sendCommand(new RemoveGECCommand(callback.getHost(),
		// callback.getPort()));
		//
		// // if the is no callbacks the client has disconnected;
		// if (callbackList.values().size() == 0) {
		// sendCommand(new DisconnectCommand());
		// logger.log(Level.INFO,
		// "Client has been disconnected on the client side");
		// }
		cgecs.removeGEC(gameEngineCallback);
		if (cgecs.callbackListEmpty()) {
			sendCommand(new DisconnectCommand());
			logger.log(Level.INFO, "Client has been disconnected on the client side");
		}
	}

	@Override
	public boolean removePlayer(Player player) {
		sendCommand(new RemovePlayerCommand(player));
		return recieveBoolean();
	}

	private synchronized void sendCommand(AbstractEngineCommand command) {
		try {
			out.writeObject(command);
			out.reset();
		} catch (SocketException e) {
			logger.log(Level.SEVERE,
			    "The server appears to be offline. Forcing shutdown");
			System.exit(1);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error writing over the network" + " Command: "
			    + command.getClass());
			e.printStackTrace();
		}
	}

	private synchronized Object recieveObject() {
		try {
			Object response = in.readObject();
			return response;
		} catch (ClassNotFoundException | IOException e) {
			logger.log(Level.SEVERE,
			    "Could not get the response back from the server");
			e.printStackTrace();
		}
		// This would only be returned if there was a connection with the server.
		return null;
	}

	private synchronized boolean recieveBoolean() {
		try {
			boolean response = in.readBoolean();
			return response;
		} catch (IOException e) {
			logger.log(Level.SEVERE,
			    "Could not get the response back from the server");
			e.printStackTrace();
		}
		// This would only be called if the server crashes.
		return false;
	}
}
