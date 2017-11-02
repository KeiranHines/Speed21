package model.commands.gameEngine;

import java.io.IOException;
import java.io.ObjectOutputStream;

import model.server.GameEngineServerStub;

@SuppressWarnings("serial")
public class GetPlayerCommand extends AbstractEngineCommand {

	private String id;

	public GetPlayerCommand(String id) {
		this.id = id;
	}

	@Override
	public void execute(GameEngineServerStub server, ObjectOutputStream out)
	    throws IOException {
		out.writeObject(server.getGameEngine().getPlayer(id));
		out.reset();
	}
}
