/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gameengine.elements;

import java.util.Collection;
import java.util.Random;

import pl.projewski.game.antos.configuration.GameConfiguration;
import pl.projewski.game.antos.gameengine.exceptions.CannotCreateMazeInstanceException;
import pl.projewski.game.antos.gameengine.maze.EMaze;
import pl.projewski.game.antos.gameengine.maze.IMaze;

/**
 *
 * @author WRO00541
 */
public class World implements IMaze {

	public Player player;
	public IMaze currentMaze;
	private Random randomizer = new Random();

	@Override
	public final Element getElement(final int x, final int y) {
		return currentMaze.getElement(x, y);
	}

	@Override
	public final <T extends Element> T get(final Class<T> c, final int x, final int y) {
		return currentMaze.get(c, x, y);
	}

	@Override
	public final boolean isAnyCollision(final int x, final int y) {
		return currentMaze.isAnyCollision(x, y);
	}

	@Override
	public final boolean isBlockCollision(final int x, final int y) {
		return currentMaze.isBlockCollision(x, y);
	}

	@Override
	public final boolean isCreatureCollision(final int x, final int y) {
		return currentMaze.isCreatureCollision(x, y);
	}

	@Override
	public void removeElement(final Element e) {
		currentMaze.removeElement(e);
	}

	@Override
	public void putElement(final Element e) {
		currentMaze.putElement(e);
	}

	public World() {
		init(System.currentTimeMillis());
	}

	@Override
	public void putPlayer(Player player) {
	}

	@Override
	public void removeMob(Creature creature) {
		currentMaze.removeMob(creature);
	}

	@Override
	public Collection<Creature> getMobs() {
		return currentMaze.getMobs();
	}

	@Override
	public void init(long randomSeed) {
		// random engine
		randomizer = new Random(randomSeed);
		// player
		player = new Player(1, 1);
		// current maze (location)
		String startMaze = GameConfiguration.getInstance().getStartMaze();
		try {
			currentMaze = EMaze.getMazeFromName(startMaze);
			currentMaze.init(randomizer.nextLong());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException e) {
			throw new CannotCreateMazeInstanceException(e);
		}
		currentMaze.putPlayer(player);
	}
}
