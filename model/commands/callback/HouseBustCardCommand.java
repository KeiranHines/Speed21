package model.commands.callback;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.PlayingCard;

@SuppressWarnings("serial")
public class HouseBustCardCommand extends AbstractCallbackCommand {

	private PlayingCard card;

	public HouseBustCardCommand(PlayingCard card) {
		this.card = card;
	}

	@Override
	public void execute(GameEngine engine, GameEngineCallback callback) {
		callback.houseBustCard(card, engine);

	}
}
