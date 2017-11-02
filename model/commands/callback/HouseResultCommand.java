package model.commands.callback;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;

@SuppressWarnings("serial")
public class HouseResultCommand extends AbstractCallbackCommand {

	private int result;

	public HouseResultCommand(int result) {
		this.result = result;
	}

	@Override
	public void execute(GameEngine engine, GameEngineCallback callback) {
		callback.houseResult(result, engine);

	}

}
