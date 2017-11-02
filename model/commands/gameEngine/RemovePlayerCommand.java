package model.commands.gameEngine;

import java.io.IOException;
import java.io.ObjectOutputStream;

import model.interfaces.Player;
import model.server.GameEngineServerStub;

@SuppressWarnings("serial")
public class RemovePlayerCommand extends AbstractEngineCommand {

	private Player player;

	public RemovePlayerCommand(Player player) {
		this.player = player;
	}

	@Override
	public void execute(GameEngineServerStub server, ObjectOutputStream out)
	    throws IOException {
		out.writeBoolean(server.getGameEngine().removePlayer(
		    server.getGameEngine().getPlayer(player.getPlayerId())));
		out.reset();
	}

}
