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
		final Creature other = gameEngine.getPlayer();
		if (newx == other.x && newy == other.y) {
			// TODO: Attack player
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
		if (attackedMob != null) {

			// for now - just kill mob and append life (eat mob)
			// TODO: attack mob
			mobs.remove(attackedMob);
			changePlayerHealth(+5);
		} else {
			// change health
			changePlayerHealth(-1);
		}
		// move graphic
		gameGraphic.moveCreature(player, newx, newy);
		// make move
		player.x = newx;
		player.y = newy;

		// next ture
		// TODO: Tick or on player move - configuration
		nextTure();
	}

	public void changePlayerHealth(final int dh) {
		final Creature player = gameEngine.getPlayer();
		if (player.currentHealth + dh > player.healthPoints) {
			player.currentHealth = player.healthPoints;
		} else {
			player.currentHealth += dh;
		}
		// healh bar graphic
		gameGraphic.updateHealth();
		// die
		if (player.currentHealth <= 0) {
			player.image = AntosResources.getInstance().getRIPImage();
			// die graphic
			gameGraphic.playerDie();
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
