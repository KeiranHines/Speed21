package model.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.GameEngineImpl;
import model.commands.gameEngine.AbstractEngineCommand;
import model.interfaces.GameEngine;

public class GameEngineServerStub {

	private static Logger logger = Logger.getLogger(GameEngineServerStub.class
	    .getName());

	private ServerSocket serverSock;
	private ConcurrentLinkedDeque<ServerStubGameEngineCallback> callbackList = new ConcurrentLinkedDeque<ServerStubGameEngineCallback>();

	private final GameEngine gameEngine = new GameEngineImpl();

	public GameEngineServerStub() throws IOException {
		serverSock = new ServerSocket(0);
		logger.setLevel(Level.ALL);
		logger.getParent().getHandlers()[0].setLevel(Level.FINE);
	}

	public GameEngineServerStub(int port) throws IOException {
		serverSock = new ServerSocket(port);
		logger.setLevel(Level.ALL);
		logger.getParent().getHandlers()[0].setLevel(Level.FINE);
	};

	public void acceptConnection() {
		int count = 1;
		while (true) {
			try {
				System.out.println("Waiting for client " + count + " on port "
				    + serverSock.getLocalPort());
				Socket sock = serverSock.accept();
				logger.log(Level.INFO,
				    "Client Connected: " + sock.getRemoteSocketAddress());
				HandleIoThread thread = new HandleIoThread(sock, count);
				thread.start();
				count++;
			} catch (IOException e) {
				logger.log(Level.WARNING, "IO Error thrown");
			}
		}
	}

	private class HandleIoThread extends Thread {
		private Socket sock;
		private int clientNumber;

		// Using ConcurrentLinkedDeque cause Thread safety is needed.

		public HandleIoThread(Socket sock, int clientNumber) {
			this.sock = sock;
			this.clientNumber = clientNumber;
		}

		@Override
		public void run() {
			try {
				ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
				ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());

				while (!sock.isClosed()) {
					AbstractEngineCommand input = (AbstractEngineCommand) in.readObject();
					input.execute(GameEngineServerStub.this, out);
					if (input.getClass().getSimpleName().equals("DisconnectCommand")) {
						sock.close();
					}
				}
				logger.log(Level.INFO, "Client " + clientNumber + " Disconnected");

			} catch (SocketException e) {
				logger.log(Level.SEVERE, "Client " + clientNumber
				    + " appears to be offline");
				try {
					sock.close();
				} catch (IOException e1) {
					logger.log(Level.SEVERE,
					    "Error closing the socket after client offline");
				}
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Error IO exception;");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				logger.log(Level.SEVERE,
				    "Error the object was not of type AbstractEngineCommand");
			}
		}
	}

	public GameEngine getGameEngine() {
		return this.gameEngine;
	}

	public ConcurrentLinkedDeque<ServerStubGameEngineCallback> getCallbackList() {
		return callbackList;
	}
}
