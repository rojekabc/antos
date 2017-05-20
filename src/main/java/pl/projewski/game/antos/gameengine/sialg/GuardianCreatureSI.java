package pl.projewski.game.antos.gameengine.sialg;

import pl.projewski.game.antos.GameContext;
import pl.projewski.game.antos.gameengine.elements.Creature;
import pl.projewski.game.antos.gameengine.elements.Element;
import pl.projewski.game.antos.gameengine.elements.Player;
import pl.projewski.game.antos.gameengine.elements.World;

public class GuardianCreatureSI implements ICreatureSIAlgorithm {

	@Override
	public void creatureNextTurn(final GameContext context, final Creature creature) {
		final World world = context.getGameEngine().getWorld();
		final Position[] checkPositions = new Position[] { new Position(creature.x + 1, creature.y),
				new Position(creature.x - 1, creature.y), new Position(creature.x, creature.y + 1),
				new Position(creature.x, creature.y - 1) };

		for (final Position position : checkPositions) {
			final Element element = world.getElement(position.x, position.y);
			if (element instanceof Player) {
				context.moveCreature(creature, position.x - creature.x, position.y - creature.y);
				return;
			}
		}

	}

	class Position {
		final int x;
		final int y;

		Position(final int x, final int y) {
			this.x = x;
			this.y = y;
		}
	}

}
