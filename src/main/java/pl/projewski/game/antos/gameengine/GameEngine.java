/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gameengine;

import java.util.Collection;

import pl.projewski.game.antos.gameengine.elements.Creature;
import pl.projewski.game.antos.gameengine.elements.World;

/**
 *
 * @author piotr.rojewski
 */
class GameEngine implements IGameEngine {

	World world;

	@Override
	public void createWorld() {
		world = new World();
	}

	@Override
	public Creature getPlayer() {
		return world.player;
	}

	@Override
	public Collection<Creature> getMobs() {
		return world.mobs;
	}

	@Override
	public World getWorld() {
		return world;
	}

}
