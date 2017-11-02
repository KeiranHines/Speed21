package model.commands.callback;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class ResultCommand extends AbstractCallbackCommand {

	private Player player;
	private int result;

	public ResultCommand(Player player, int result) {
		this.player = player;
		this.result = result;
	}

	@Override
	public void execute(GameEngine engine, GameEngineCallback callback) {
		callback.result(player, result, engine);

	}

}
