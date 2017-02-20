package pl.projewski.game.antos.gameengine.sialg;

import pl.projewski.game.antos.GameContext;
import pl.projewski.game.antos.gameengine.elements.Creature;

/**
 * Algorithm of creature.
 * 
 * @author piotr.rojewski
 *
 */
public interface ICreatureSIAlgorithm {
	/**
	 * Do a next creature turn.
	 */
	void creatureNextTurn(GameContext context, Creature creature);

}
