package model.commands.gameEngine;

import java.io.ObjectOutputStream;

import model.server.GameEngineServerStub;

/*
 * This class is not intended to do anything except alert the server that a
 * client is intending to disconnect from the server. 
 */

@SuppressWarnings("serial")
public class DisconnectCommand extends AbstractEngineCommand {

	public DisconnectCommand() {
	}

	@Override
	public void execute(GameEngineServerStub server, ObjectOutputStream out) {
	}
}
