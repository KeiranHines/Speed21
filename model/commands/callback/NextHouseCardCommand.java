package model.commands.callback;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.PlayingCard;

@SuppressWarnings("serial")
public class NextHouseCardCommand extends AbstractCallbackCommand {

	private PlayingCard card;

	public NextHouseCardCommand(PlayingCard card) {
		this.card = card;
	}

	@Override
	public void execute(GameEngine engine, GameEngineCallback callback) {
		callback.nextHouseCard(card, engine);

	}
}
