package model.commands.gameEngine;

import java.io.ObjectOutputStream;

import model.interfaces.Player;
import model.server.GameEngineServerStub;

@SuppressWarnings("serial")
public class DealPlayerCommand extends AbstractEngineCommand {

	private Player player;
	private int delay;

	public DealPlayerCommand(Player player, int delay) {
		this.player = player;
		this.delay = delay;
	}

	@Override
	public void execute(GameEngineServerStub server, ObjectOutputStream out) {

		server.getGameEngine().dealPlayer(
		    server.getGameEngine().getPlayer(player.getPlayerId()), delay);
	}

}
