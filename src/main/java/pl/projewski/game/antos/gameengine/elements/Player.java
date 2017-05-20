/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gameengine.elements;

import pl.projewski.game.antos.configuration.ECreature;

/**
 *
 * @author piotr.rojewski
 */
public class Player extends Creature {

	public Player(final int x, final int y) {
		super(ECreature.PLAYER, x, y);
	}

}
