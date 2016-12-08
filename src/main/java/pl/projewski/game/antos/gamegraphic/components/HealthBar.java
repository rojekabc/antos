/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gamegraphic.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import pl.projewski.game.antos.AntosProperties;
import pl.projewski.game.antos.GameContext;
import pl.projewski.game.antos.gameengine.elements.Creature;

/**
 *
 * @author WRO00541
 */
class HealthBar {

	int width = AntosProperties.MENUPANEL_WIDTH - 2 * AntosProperties.MENUPANEL_MARGIN;
	int height = 40;
	int x = AntosProperties.MENUPANEL_MARGIN + AntosProperties.GAMEPANEL_WIDTH;
	int y = AntosProperties.MENUPANEL_MARGIN;
	int min = 0;
	int max = 0;
	int value = 0;
	Color foregroundColor = Color.green;
	Color backgroundColor = Color.black;
	Color borderColor = Color.white;
	int borderSize = 3;
	int marginSize = 3;
	GameContext context;

	public HealthBar(final GameContext ctx) {
		context = ctx;
		final Creature playerCreature = context.getPlayerCreature();
		max = playerCreature.type.getHp();
		value = playerCreature.currentHealth;
		ctx.getGameGraphic().setHealthBar(this);
		// context.registerListener(this, PlayerHealthEvent.class);
	}

	public void setValue(final int i) {
		this.value = i;
		final int precentage = i * 100 / max;
		if (precentage > 60) {
			foregroundColor = Color.green;
		} else if (precentage > 40) {
			foregroundColor = Color.yellow;
		} else if (precentage > 20) {
			foregroundColor = Color.orange;
		} else {
			foregroundColor = Color.red;
		}
	}

	public void paint(final Graphics g) {
		// System.out.println("PAINT COMPONENT");
		g.setColor(borderColor);
		g.fillRect(x, y, width, height);
		g.setColor(backgroundColor);
		g.fillRect(x + borderSize, y + borderSize, width - 2 * borderSize, height - 2 * borderSize);
		g.setColor(foregroundColor);
		final int barwidth = value * (width - 2 * (borderSize + marginSize)) / max;
		g.fillRect(x + borderSize + marginSize, y + borderSize + marginSize, barwidth,
				height - 2 * (borderSize + marginSize));
	}

	void updateHealth(final Graphics graphics) {
		final Creature playerCreature = context.getPlayerCreature();
		final BufferStrategy bufferStrategy = context.getGameGraphic().getBufferStrategy();
		synchronized (bufferStrategy) {
			max = playerCreature.type.getHp();
			setValue(playerCreature.currentHealth);

			paint(graphics);
		}
		Toolkit.getDefaultToolkit().sync();
	}

}
