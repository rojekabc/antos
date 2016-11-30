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
public interface IGameEngine {

	void createWorld();

	Creature getPlayer();

	Collection<Creature> getMobs();

	World getWorld();

}
