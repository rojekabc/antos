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
		// check collision (player)
		final Creature player = gameEngine.getPlayer();
		if (newx == player.x && newy == player.y) {
			// attack player
			changeCreatureHealth(player, -1);
			return;
		}
		final Collection<Creature> mobs = gameEngine.getMobs();
		for (final Creature mob : mobs) {
			if (newx == mob.x && newy == mob.y) {
				return;
			}
		}
		// move graphic
		gameGraphic.moveCreature(creature, newx, newy);
		// make move
		creature.x = newx;
		creature.y = newy;

	}

	public void movePlayer(final int dx, final int dy) {
		final Creature player = gameEngine.getPlayer();
		// is dead
		if (player.currentHealth <= 0) {
			return;
		}
		final int newx = player.x + dx;
		final int newy = player.y + dy;
		// is overboarded
		if (newx < 0 || newx >= AntosProperties.GRID_WIDTH || newy < 0 || newy >= AntosProperties.GRID_HEIGHT) {
			return;
		}
		// check collisions
		// - colisions with any non-life block
		if (getWorld().isBlockCollision(newx, newy)) {
			// cannot move
			return;
		}
		// - colisions with mobs
		Creature attackedMob = null;
		final Collection<Creature> mobs = gameEngine.getMobs();
		for (final Creature mob : mobs) {
			if (newx == mob.x && newy == mob.y) {
				attackedMob = mob;
				break;
			}
		}
		try {
			if (attackedMob != null) {
				// take a heath points
				final int attackHP = -2 - random.nextInt(3);
				changeCreatureHealth(attackedMob, attackHP);
				if (attackedMob.currentHealth > 0) {
					// don't move. MOB is still alive
					return;
				} else {
					// kill mob, get health and move
					mobs.remove(attackedMob);
					changeCreatureHealth(player, 3);
				}
			}
			// move graphic
			gameGraphic.moveCreature(player, newx, newy);
			// make move
			player.x = newx;
			player.y = newy;
		} finally {

			// next ture
			// TODO: Tick or on player move - configuration
			nextTure();
		}
	}

	public void changeCreatureHealth(final Creature creature, int dh) {
		if (creature.currentHealth + dh > creature.healthPoints) {
			dh = creature.healthPoints - creature.currentHealth;
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
			if (creature == player) {
				player.image = AntosResources.getInstance().getRIPImage();
				// die graphic
				gameGraphic.playerDie();
			}
		}
	}

	public Collection<Creature> getMobs() {
		return gameEngine.getMobs();
	}

	public World getWorld() {
		return gameEngine.getWorld();
	}

	public void nextTure() {
		log.debug("nextTure");
		// move all mobs
		final Collection<Creature> mobs = getMobs();
		final Random random = new Random();
		for (final Creature creature : mobs) {
			// TODO: Currently move of mob is totally random - do depened on
			// type and with more strategy (target)
			final int move = random.nextInt(4);
			switch (move) {
			case 0:
				moveCreature(creature, -1, 0);
				break;
			case 1:
				moveCreature(creature, 1, 0);
				break;
			case 2:
				moveCreature(creature, 0, -1);
				break;
			case 3:
				moveCreature(creature, 0, 1);
				break;
			}
		}
	}

}
