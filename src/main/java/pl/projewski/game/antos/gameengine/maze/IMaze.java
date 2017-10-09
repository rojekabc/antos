package pl.projewski.game.antos.gameengine.maze;

import java.util.Collection;

import pl.projewski.game.antos.configuration.CreatureAmount;
import pl.projewski.game.antos.gameengine.elements.Creature;
import pl.projewski.game.antos.gameengine.elements.Element;
import pl.projewski.game.antos.gameengine.elements.Player;

public interface IMaze {
	Element getElement(final int x, final int y);

	<T extends Element> T get(final Class<T> c, final int x, final int y);

	boolean isAnyCollision(final int x, final int y);

	boolean isBlockCollision(final int x, final int y);

	boolean isCreatureCollision(final int x, final int y);

	void removeElement(final Element e);

	void putElement(final Element e);

	void putPlayer(Player player);

	void removeMob(Creature creature);

	Collection<Creature> getMobs();

	void init(long seed);

	void putMobs(CreatureAmount creatureAmount);
}
