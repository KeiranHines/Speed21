package model.commands.gameEngine;

import java.io.ObjectOutputStream;

import model.server.GameEngineServerStub;

@SuppressWarnings("serial")
public class DealHouseCommand extends AbstractEngineCommand {

	private int delay;

	public DealHouseCommand(int delay) {
		this.delay = delay;
	}

	@Override
	public void execute(GameEngineServerStub server, ObjectOutputStream out) {
		server.getGameEngine().dealHouse(delay);
	}

}
