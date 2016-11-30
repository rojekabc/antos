/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gameengine.elements;

import java.awt.image.BufferedImage;

/**
 *
 * @author WRO00541
 */
public class Creature extends Element {
	public int healthPoints;
	public int currentHealth;

	public Creature(final int x, final int y, final BufferedImage image, final int healthPoints) {
		super(x, y, image);
		this.healthPoints = healthPoints;
		this.currentHealth = healthPoints;
	}

}
