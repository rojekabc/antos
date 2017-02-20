package pl.projewski.game.antos.gameengine.sialg;

import pl.projewski.game.antos.GameContext;
import pl.projewski.game.antos.gameengine.elements.Creature;

public class RandomCreatureSI implements ICreatureSIAlgorithm {

	@Override
	public void creatureNextTurn(final GameContext context, final Creature creature) {
		final int move = context.getRandom().nextInt(4);
		switch (move) {
		case 0:
			context.moveCreature(creature, -1, 0);
			break;
		case 1:
			context.moveCreature(creature, 1, 0);
			break;
		case 2:
			context.moveCreature(creature, 0, -1);
			break;
		case 3:
			context.moveCreature(creature, 0, 1);
			break;
		}
	}

}
