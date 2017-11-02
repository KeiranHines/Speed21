package model.commands.gameEngine;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.Iterator;

import model.server.GameEngineServerStub;
import model.server.ServerStubGameEngineCallback;

@SuppressWarnings("serial")
public class RemoveGECCommand extends AbstractEngineCommand {

	private InetAddress host;
	private int port;

	public RemoveGECCommand(InetAddress host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public void execute(GameEngineServerStub server, ObjectOutputStream out) {
		Iterator<ServerStubGameEngineCallback> iter = server.getCallbackList()
		    .iterator();
		while (iter.hasNext()) {
			ServerStubGameEngineCallback callback = iter.next();
			if (callback.getHost().equals(host) && callback.getPort() == port) {
				server.getCallbackList().remove(callback);
				server.getGameEngine().removeGameEngineCallback(callback);
			}
		}
	}
}
