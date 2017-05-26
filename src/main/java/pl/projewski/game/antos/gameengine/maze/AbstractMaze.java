package pl.projewski.game.antos.gameengine.maze;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import lombok.NoArgsConstructor;
import pl.projewski.game.antos.AntosProperties;
import pl.projewski.game.antos.gameengine.elements.Creature;
import pl.projewski.game.antos.gameengine.elements.Element;
import pl.projewski.game.antos.gameengine.elements.Player;

@NoArgsConstructor
public abstract class AbstractMaze implements IMaze {
	public Player player;
	private Element[] map;
	public Collection<Creature> mobs;
	private long randomSeed;
	private Random random;

	@Override
	public void init(long randomSeed) {
		// create map object
		map = new Element[AntosProperties.GRID_WIDTH * AntosProperties.GRID_HEIGHT];
		mobs = new ArrayList<>();
		this.randomSeed = randomSeed;
		random = new Random(randomSeed);
		createMap();
		putMobs();
	}

	@Override
	public Element getElement(final int x, final int y) {
		return map[x + y * AntosProperties.GRID_WIDTH];
	}

	void set(final Element e, final int x, final int y) {
		map[x + y * AntosProperties.GRID_WIDTH] = e;
	}

	@Override
	public final <T extends Element> T get(final Class<T> c, final int x, final int y) {
		final Element e = map[x + y * AntosProperties.GRID_WIDTH];
		if (e == null) {
			return null;
		} else {
			return c.cast(e);
		}
	}

	@Override
	public final boolean isAnyCollision(final int x, final int y) {
		return getElement(x, y) != null;
	}

	@Override
	public final boolean isBlockCollision(final int x, final int y) {
		final Element element = getElement(x, y);
		if (element != null) {
			return !(element instanceof Creature);
		}
		return false;
	}

	@Override
	public final boolean isCreatureCollision(final int x, final int y) {
		if (player.x == x && player.y == y) {
			return true;
		}
		for (final Creature mob : mobs) {
			if (mob.x == x && mob.y == y) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void removeElement(final Element e) {
		set(null, e.x, e.y);
	}

	@Override
	public void putElement(final Element e) {
		if (getElement(e.x, e.y) != null) {
			throw new IllegalStateException();
		}
		set(e, e.x, e.y);
	}

	protected int randomInt(int bound) {
		return bound != 0 ? random.nextInt(bound) : 0;
	}

	protected int randomIntWithBound(int bound) {
		return random.nextInt(bound + 1);
	}

	@Override
	public void removeMob(Creature creature) {
		mobs.remove(creature);
	}

	protected AbstractMaze(int randomSeed) {
		init(randomSeed);
	}

	public abstract void createMap();

	public abstract void putMobs();

	@Override
	public Collection<Creature> getMobs() {
		return mobs;
	}
}
