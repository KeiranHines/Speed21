package model.commands.gameEngine;

import java.io.IOException;
import java.io.ObjectOutputStream;

import model.interfaces.Player;
import model.server.GameEngineServerStub;

@SuppressWarnings("serial")
public class PlaceBetCommand extends AbstractEngineCommand {
	private Player player;
	private int bet;

	public PlaceBetCommand(Player player, int bet) {
		this.player = player;
		this.bet = bet;

	}

	@Override
	public void execute(GameEngineServerStub server, ObjectOutputStream out)
	    throws IOException {
		this.player = server.getGameEngine().getPlayer(player.getPlayerId());
		boolean result = server.getGameEngine().placeBet(player, bet);
		out.writeBoolean(result);
		out.reset();
	}
}
