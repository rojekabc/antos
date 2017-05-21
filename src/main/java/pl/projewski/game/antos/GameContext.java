/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos;

import java.util.Collection;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import pl.projewski.game.antos.gameengine.IGameEngine;
import pl.projewski.game.antos.gameengine.elements.Creature;
import pl.projewski.game.antos.gameengine.elements.World;
import pl.projewski.game.antos.gamegraphic.components.IGameGraphic;

/**
 *
 * @author piotr.rojewski
 */
@Slf4j
public class GameContext {

	IGameGraphic gameGraphic;
	IGameEngine gameEngine;
	private final Random random = new Random();

	GameContext(final IGameGraphic gg, final IGameEngine ge) {
		this.gameGraphic = gg;
		this.gameEngine = ge;
	}

	public IGameGraphic getGameGraphic() {
		return gameGraphic;
	}

	public IGameEngine getGameEngine() {
		return gameEngine;
	}

	public Creature getPlayerCreature() {
		return gameEngine.getPlayer();
	}

	public void moveCreature(final Creature creature, final int dx, final int dy) {
		final int newx = creature.x + dx;
		final int newy = creature.y + dy;
		// is overboarded
		if (newx < 0 || newx >= AntosProperties.GRID_WIDTH || newy < 0 || newy >= AntosProperties.GRID_HEIGHT) {
			return;
		}
		// - colisions with any non-life block
		if (getWorld().isBlockCollision(newx, newy)) {
			// cannot move
			return;
		}
		// check collision (creature)
		final Creature collisionCreature = getWorld().get(Creature.class, newx, newy);
		if (collisionCreature != null) {
			final Creature player = gameEngine.getPlayer();
			final int attackHP = creature.type.getMinAttack()
			        + (creature.type.getRndAttack() > 0 ? random.nextInt(creature.type.getRndAttack()) : 0);
			if (creature == player) {
				// player attack MOB
				gameGraphic.changeCreatureHealth(collisionCreature, -attackHP);
				changeCreatureHealth(collisionCreature, -attackHP);
				if (collisionCreature.currentHealth <= 0) {
					// kill mob, get health
					gameGraphic.changeCreatureHealth(player, 3);
					changeCreatureHealth(player, 3);
				}
				return;
			} else if (collisionCreature == player) {
				// MOB attack player
				gameGraphic.changeCreatureHealth(player, -attackHP);
				changeCreatureHealth(player, -attackHP);
				return;
			} else {
				// MOB try to move on another MOB place
				return;
			}
		}
		// move graphic
		gameGraphic.moveCreature(creature, newx, newy);
		// make move
		getWorld().removeElement(creature);
		creature.x = newx;
		creature.y = newy;
		getWorld().putElement(creature);
	}

	public void movePlayer(final int dx, final int dy) {
		try {
			final Creature player = gameEngine.getPlayer();
			// is dead
			if (player.currentHealth <= 0) {
				return;
			}
			moveCreature(player, dx, dy);
		} finally {
			nextTurn();
		}
	}

	/**
	 * Check that player creature doesn't do any action right now and can take
	 * new command, which should do from now.
	 * 
	 * @return
	 */
	public boolean canPlayerTakeCommand() {
		final Creature player = gameEngine.getPlayer();
		return !gameGraphic.hasGraphicAction(player);
	}

	public void changeCreatureHealth(final Creature creature, int dh) {
		if (creature.currentHealth + dh > creature.type.getHp()) {
			dh = creature.type.getHp() - creature.currentHealth;
		}
		creature.currentHealth += dh;
		final Creature player = gameEngine.getPlayer();
		if (creature == player) {
			// healh bar graphic
			gameGraphic.updateHealth();
			log.info("Player get " + dh + " HP");
		} else {
			log.info("MOB get " + dh + " HP");
		}
		// die
		if (creature.currentHealth <= 0) {
			// die graphic
			getWorld().mobs.remove(creature);
			gameGraphic.creatureDie(creature, getWorld());
		}
	}

	public Collection<Creature> getMobs() {
		return gameEngine.getMobs();
	}

	public World getWorld() {
		return gameEngine.getWorld();
	}

	public Random getRandom() {
		return random;
	}

	public void nextTurn() {
		log.debug("nextTurn");
		// move all mobs
		final Collection<Creature> mobs = getMobs();
		for (final Creature creature : mobs) {
			if (creature.creatureSI != null) {
				creature.creatureSI.creatureNextTurn(this, creature);
			}
		}
	}

}
