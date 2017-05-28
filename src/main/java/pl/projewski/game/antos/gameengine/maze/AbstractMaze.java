package pl.projewski.game.antos.gameengine.maze;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.projewski.game.antos.AntosProperties;
import pl.projewski.game.antos.configuration.CreatureAmount;
import pl.projewski.game.antos.configuration.ECreature;
import pl.projewski.game.antos.gameengine.elements.Creature;
import pl.projewski.game.antos.gameengine.elements.Element;
import pl.projewski.game.antos.gameengine.elements.Player;

@NoArgsConstructor
@Slf4j
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

	@Override
	public Collection<Creature> getMobs() {
		return mobs;
	}

	@Override
	public void putMobs(CreatureAmount creatureAmount) {
		ECreature[] creatures = ECreature.values();
		// get creature name filter
		String creatureName = creatureAmount.getCreature();
		// calculate number of MOBs to put
		final int numOfMobs = creatureAmount.getMinAmount() + randomIntWithBound(creatureAmount.getRandAmount());
		log.info("Number of MOBs: " + numOfMobs + "(min=" + creatureAmount.getMinAmount() + ", rand="
		        + creatureAmount.getRandAmount() + ")");
		List<ECreature> possibleCreatures = new ArrayList<>();
		// find list of possible creatures for this filter
		for (ECreature creature : creatures) {
			if (creature == ECreature.PLAYER) {
				continue;
			}
			if (creature.name().matches(creatureName)) {
				possibleCreatures.add(creature);
			}
		}
		if (possibleCreatures.isEmpty() || numOfMobs == 0) {
			return;
		}
		for (int i = 0; i < numOfMobs; i++) {
			// random position
			int x, y;
			do {
				x = randomInt(AntosProperties.GRID_WIDTH);
				y = randomInt(AntosProperties.GRID_HEIGHT);
			} while (isAnyCollision(x, y));
			// random mob type
			final int mobSelect = randomInt(possibleCreatures.size());
			// create and put a mob
			final Creature mob = new Creature(possibleCreatures.get(mobSelect), x, y);
			putElement(mob);
			mobs.add(mob);
		}
		// ctx.registerListener(player);
	}

}
