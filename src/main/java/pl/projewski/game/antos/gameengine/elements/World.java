/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gameengine.elements;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import pl.projewski.game.antos.AntosProperties;
import pl.projewski.game.antos.AntosResources;
import pl.projewski.game.antos.configuration.CreatureAmmount;
import pl.projewski.game.antos.configuration.EBlock;
import pl.projewski.game.antos.configuration.ECreature;
import pl.projewski.game.antos.configuration.GameConfiguration;

/**
 *
 * @author WRO00541
 */
public class World {

	public Player player;
	private final Element[] map;
	public Collection<Creature> mobs;
	private final static Random randomizer = new Random();

	public final Element getElement(final int x, final int y) {
		return map[x + y * AntosProperties.GRID_WIDTH];
	}

	public final <T extends Element> T get(final Class<T> c, final int x, final int y) {
		final Element e = map[x + y * AntosProperties.GRID_WIDTH];
		if (e == null) {
			return null;
		} else {
			return c.cast(e);
		}
	}

	public final boolean isAnyCollision(final int x, final int y) {
		return getElement(x, y) != null;
	}

	public final boolean isBlockCollision(final int x, final int y) {
		final Element element = getElement(x, y);
		if (element != null) {
			return !(element instanceof Creature);
		}
		return false;
	}

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

	void set(final Element e, final int x, final int y) {
		map[x + y * AntosProperties.GRID_WIDTH] = e;
	}

	public void removeElement(final Element e) {
		set(null, e.x, e.y);
	}

	public void putElement(final Element e) {
		if (getElement(e.x, e.y) != null) {
			throw new IllegalStateException();
		}
		set(e, e.x, e.y);
	}

	public World() {
		// create map object
		map = new Element[AntosProperties.GRID_WIDTH * AntosProperties.GRID_HEIGHT];
		// draw block round
		final BufferedImage simpleImage = AntosResources.getInstance().loadImage(EBlock.SIMPLE.getImageResource());
		for (int i = 0; i < AntosProperties.GRID_WIDTH; i++) {
			putElement(new Element(i, 0, simpleImage));
			putElement(new Element(i, AntosProperties.GRID_HEIGHT - 1, simpleImage));
		}
		for (int i = 1; i < AntosProperties.GRID_HEIGHT - 1; i++) {
			putElement(new Element(0, i, simpleImage));
			putElement(new Element(AntosProperties.GRID_WIDTH - 1, i, simpleImage));
		}
		// put player
		player = new Player(1, 1);
		putElement(player);
		// put mobs
		mobs = new ArrayList<>();
		List<CreatureAmmount> creatureAmmountList = GameConfiguration.getInstance().getCreatureAmmount();
		if (creatureAmmountList != null && !creatureAmmountList.isEmpty()) {
			ECreature[] creatures = ECreature.values();
			for (CreatureAmmount creatureAmmount : creatureAmmountList) {
				// get creature name filter
				String creatureName = creatureAmmount.getCreature();
				// calculate number of MOBs to put
				final int numOfMobs = creatureAmmount.getMinAmmount() + (creatureAmmount.getRandAmmount() == 0 ? 0
				        : randomizer.nextInt(creatureAmmount.getRandAmmount() + 1));
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
					continue;
				}
				for (int i = 0; i < numOfMobs; i++) {
					// random position
					int x, y;
					do {
						x = randomizer.nextInt(AntosProperties.GRID_WIDTH);
						y = randomizer.nextInt(AntosProperties.GRID_HEIGHT);
					} while (isAnyCollision(x, y));
					// random mob type
					final int mobSelect = randomizer.nextInt(possibleCreatures.size());
					// create and put a mob
					final Creature mob = new Creature(possibleCreatures.get(mobSelect), x, y);
					putElement(mob);
					mobs.add(mob);
				}
			}
			// ctx.registerListener(player);
		}
	}
}
