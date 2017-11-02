package model.commands.gameEngine;

import java.io.ObjectOutputStream;

import model.server.GameEngineServerStub;

@SuppressWarnings("serial")
public class CalculateResultCommand extends AbstractEngineCommand {

	@Override
	public void execute(GameEngineServerStub server, ObjectOutputStream out) {
		server.getGameEngine().calculateResult();

	}

}
