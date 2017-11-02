package model.commands.callback;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

@SuppressWarnings("serial")
public class BustCardCommand extends AbstractCallbackCommand {

	private Player player;
	private PlayingCard card;

	public BustCardCommand(Player player, PlayingCard card) {
		this.player = player;
		this.card = card;
	}

	@Override
	public void execute(GameEngine engine, GameEngineCallback callback) {
		callback.bustCard(player, card, engine);
	}

}
