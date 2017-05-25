/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gameengine.elements;

import java.util.Collection;
import java.util.Random;

import pl.projewski.game.antos.gameengine.maze.IMaze;
import pl.projewski.game.antos.gameengine.maze.RoundedMaze;

/**
 *
 * @author WRO00541
 */
public class World implements IMaze {

	public Player player;
	public IMaze currentMaze;
	private final static Random randomizer = new Random();

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
		player = new Player(1, 1);
		// TODO: Generate
		currentMaze = new RoundedMaze(randomizer.nextInt());
		currentMaze.putPlayer(player);
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
}
