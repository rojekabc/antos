/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gameengine.elements;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import pl.projewski.game.antos.configuration.CreatureAmount;
import pl.projewski.game.antos.configuration.GameConfiguration;
import pl.projewski.game.antos.configuration.MazeConfiguration;
import pl.projewski.game.antos.gameengine.exceptions.CannotCreateMazeInstanceException;
import pl.projewski.game.antos.gameengine.maze.EMaze;
import pl.projewski.game.antos.gameengine.maze.IMaze;

/**
 *
 * @author WRO00541
 */
@Slf4j
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
		GameConfiguration configuration = GameConfiguration.getInstance();
		String startMaze = configuration.getStartMaze();
		log.info("Loading maze: " + startMaze);
		try {
			MazeConfiguration mazeConfiguration = configuration.getMazeConfiguration(startMaze);
			currentMaze = EMaze.getMazeFromName(mazeConfiguration.getType());
			log.info("Maze type: " + mazeConfiguration.getType());
			currentMaze.init(randomizer.nextLong());
			List<String> mobs = mazeConfiguration.getMobs();
			for (String mobConfigurationName : mobs) {
				log.info("Loading mob's configuration: " + mobConfigurationName);
				CreatureAmount creatureAmmount = configuration.getCreatureAmmount(mobConfigurationName);
				currentMaze.putMobs(creatureAmmount);
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException e) {
			throw new CannotCreateMazeInstanceException(e);
		}
		currentMaze.putPlayer(player);
	}

	@Override
	public void putMobs(CreatureAmount creatureAmount) {
		// TODO Auto-generated method stub

	}
}
