package model.commands.gameEngine;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.interfaces.Player;
import model.server.GameEngineServerStub;

@SuppressWarnings("serial")
public class GetAllPlayersCommand extends AbstractEngineCommand {

	@Override
	public void execute(GameEngineServerStub server, ObjectOutputStream out)
	    throws IOException {
		ArrayList<Player> list = new ArrayList<Player>();
		list.addAll(server.getGameEngine().getAllPlayers());
		out.writeObject(list);
		out.reset();
	}
}
