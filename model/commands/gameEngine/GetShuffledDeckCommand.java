package model.commands.gameEngine;

import java.io.IOException;
import java.io.ObjectOutputStream;

import model.server.GameEngineServerStub;

@SuppressWarnings("serial")
public class GetShuffledDeckCommand extends AbstractEngineCommand {
	@Override
	public void execute(GameEngineServerStub server, ObjectOutputStream out)
	    throws IOException {
		out.writeObject(server.getGameEngine().getShuffledDeck());
		out.reset();
	}
}
