/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gameengine.elements;

import pl.projewski.game.antos.AntosResources;
import pl.projewski.game.antos.configuration.ECreature;
import pl.projewski.game.antos.gameengine.exceptions.CreateCreatureSIAlgorithmException;
import pl.projewski.game.antos.gameengine.sialg.ECreatureSI;
import pl.projewski.game.antos.gameengine.sialg.ICreatureSIAlgorithm;

/**
 *
 * @author WRO00541
 */
public class Creature extends Element {
	public ECreature type;
	public int currentHealth;
	public ICreatureSIAlgorithm creatureSI;

	public Creature(final ECreature creatureType, final int x, final int y) {
		super(x, y, AntosResources.getInstance().loadImage(creatureType.getImageResource()));
		this.type = creatureType;
		this.currentHealth = creatureType.getHp();
		final String creatureSIName = creatureType.getCreatureSI();
		if (creatureSIName != null) {
			try {
				creatureSI = ECreatureSI.valueOf(creatureSIName.toUpperCase()).createSIAlgorithm();
			} catch (final InstantiationException e) {
				throw new CreateCreatureSIAlgorithmException(e);
			} catch (final IllegalAccessException e) {
				throw new CreateCreatureSIAlgorithmException(e);
			}
		}
	}

}
