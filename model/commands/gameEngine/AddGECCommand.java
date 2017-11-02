package model.commands.gameEngine;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;

import model.server.GameEngineServerStub;
import model.server.ServerStubGameEngineCallback;

@SuppressWarnings("serial")
public class AddGECCommand extends AbstractEngineCommand {

	private InetAddress host;
	private int port;

	public AddGECCommand(InetAddress host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public void execute(GameEngineServerStub server, ObjectOutputStream out)
	    throws IOException {
		ServerStubGameEngineCallback serverCallback = new ServerStubGameEngineCallback(
		    host, port);
		server.getCallbackList().add(serverCallback);
		server.getGameEngine().addGameEngineCallback(serverCallback);
	}
}