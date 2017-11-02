package app;

import java.io.IOException;
import java.net.BindException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.server.GameEngineServerStub;

public class Server {
	private static Logger logger = Logger.getLogger(Server.class.getName());

	public static void main(String[] args) {
		int port;
		if (args.length >= 1) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 7234;
		}
		try {
			GameEngineServerStub server = new GameEngineServerStub(port);
			server.acceptConnection();
		} catch (BindException e) {
			logger.log(Level.WARNING,
			    "The requested port was in use, please try again with a new port");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "IO error, not bind error");
		}
	}

}