/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gameengine.elements;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import pl.projewski.game.antos.AntosProperties;
import pl.projewski.game.antos.AntosResources;
import pl.projewski.game.antos.configuration.EBlock;
import pl.projewski.game.antos.configuration.ECreature;

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
		final int numOfMobs = randomizer.nextInt(AntosProperties.MAX_ADD_MOB_AMMOUT) + AntosProperties.MIN_MOB_AMMOUNT;
		mobs = new ArrayList<>();
		for (int i = 0; i < numOfMobs; i++) {
			int x, y;
			do {
				x = randomizer.nextInt(AntosProperties.GRID_WIDTH);
				y = randomizer.nextInt(AntosProperties.GRID_HEIGHT);
			} while (isAnyCollision(x, y));

			// random mob type (omit player)
			final int mobSelect = randomizer.nextInt(ECreature.values().length - 1);
			final Creature mob = new Creature(ECreature.values()[mobSelect + 1], x, y);
			putElement(mob);
			mobs.add(mob);
		}
		// ctx.registerListener(player);
	}
}
