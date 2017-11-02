package model.commands.gameEngine;

import java.io.ObjectOutputStream;

import model.interfaces.Player;
import model.server.GameEngineServerStub;

@SuppressWarnings("serial")
public class AddPlayerCommand extends AbstractEngineCommand {

	private Player player;

	public AddPlayerCommand(Player player) {
		this.player = player;
	}

	@Override
	public void execute(GameEngineServerStub server, ObjectOutputStream out) {
		server.getGameEngine().addPlayer(player);
	}

}
