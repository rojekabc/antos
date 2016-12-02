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
			if (creature == player) {
				// player attack MOB
				final int attackHP = -2 - random.nextInt(3);
				changeCreatureHealth(collisionCreature, attackHP);
				if (collisionCreature.currentHealth > 0) {
					// don't move. MOB is still alive
					return;
				} else {
					// kill mob, get health and move
					getWorld().mobs.remove(collisionCreature);
					getWorld().removeElement(collisionCreature);
					changeCreatureHealth(player, 3);
				}
			} else if (collisionCreature == player) {
				// MOB attack player
				changeCreatureHealth(player, -1);
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
