package model.commands.gameEngine;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.server.GameEngineServerStub;

@SuppressWarnings("serial")
public abstract class AbstractEngineCommand implements Serializable {
	private static Logger logger = Logger.getLogger(AbstractEngineCommand.class
	    .getName());

	public abstract void execute(GameEngineServerStub engine,
	    ObjectOutputStream out) throws IOException;

	public void logError(String error) {
		logger.log(Level.SEVERE, error);
	}
}
