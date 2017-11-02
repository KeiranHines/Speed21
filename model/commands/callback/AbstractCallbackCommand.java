package model.commands.callback;

import java.io.Serializable;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;

@SuppressWarnings("serial")
public abstract class AbstractCallbackCommand implements Serializable {

	public abstract void execute(GameEngine engine, GameEngineCallback callback);

}
