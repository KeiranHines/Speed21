package model.client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.commands.callback.AbstractCallbackCommand;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;

public class ClientGameEngineCallbackServer {

	private static Logger logger = Logger
	    .getLogger(ClientGameEngineCallbackServer.class.getName());

	private List<GameEngineCallback> callbackList = new ArrayList<GameEngineCallback>();
	private GameEngine engine;
	private ServerSocket serverSock;

	public ClientGameEngineCallbackServer(GameEngine engine) {
		this.engine = engine;
		logger.setLevel(Level.ALL);
		logger.getParent().getHandlers()[0].setLevel(Level.FINE);
		try {
			serverSock = new ServerSocket(0);
			waitForServerCallbackThread thread = new waitForServerCallbackThread(
			    serverSock);
			thread.start();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error opening ServerSocket");
		}

	}

	private class waitForServerCallbackThread extends Thread {
		private ServerSocket serverSock;

		public waitForServerCallbackThread(ServerSocket serverSock) {
			this.serverSock = serverSock;
		}

		@Override
		public void run() {
			try {
				Socket sock = serverSock.accept();
				ObjectInputStream in = new ObjectInputStream(sock.getInputStream());

				while (true) {
					AbstractCallbackCommand input = (AbstractCallbackCommand) in
					    .readObject();
					if (input.getClass().getSimpleName().equals("RemoveGECCommand")) {
						sock.close();
					} else {
						for (GameEngineCallback c : callbackList) {
							input.execute(engine, c);
						}
					}
				}
				// logger.log(Level.INFO, "Server callback Disconnected");

			} catch (EOFException e) {
				logger.log(Level.SEVERE, "Server has been shutdown unexpectedly");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				logger.log(Level.SEVERE, "Error the object was not of type command");
			}
		}
	}

	public void addGEC(GameEngineCallback callback) {
		callbackList.add(callback);
	}

	public void removeGEC(GameEngineCallback callback) {
		callbackList.remove(callback);
	}

	public boolean callbackListEmpty() {
		return callbackList.isEmpty();
	}

	public InetAddress getHost() {
		return serverSock.getInetAddress();
	}

	public int getPort() {
		return serverSock.getLocalPort();
	}

	public void disconnect() {
		try {
			serverSock.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Could not close the server socket");
			e.printStackTrace();
		}
	}
}