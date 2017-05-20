package pl.projewski.game.antos.gamegraphic.actions;

import lombok.Getter;
import pl.projewski.game.antos.gameengine.elements.Creature;
import pl.projewski.game.antos.gamegraphic.components.IGameGraphic;

public abstract class CreatureActionAbstract extends GameGraphicActionAbstract {
	@Getter
	protected final Creature creature;

	protected CreatureActionAbstract(final IGameGraphic gameGraphic, final Creature creature) {
		super(gameGraphic);
		this.creature = creature;
	}

}
