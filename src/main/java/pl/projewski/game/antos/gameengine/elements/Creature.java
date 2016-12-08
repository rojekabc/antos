/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gameengine.elements;

import pl.projewski.game.antos.AntosResources;
import pl.projewski.game.antos.configuration.ECreature;

/**
 *
 * @author WRO00541
 */
public class Creature extends Element {
	public ECreature type;
	public int currentHealth;

	public Creature(final ECreature creatureType, final int x, final int y) {
		super(x, y, AntosResources.getInstance().loadImage(creatureType.getImageResource()));
		this.type = creatureType;
		this.currentHealth = creatureType.getHp();
	}

}
