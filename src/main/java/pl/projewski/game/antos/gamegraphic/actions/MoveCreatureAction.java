/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gamegraphic.actions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.RasterFormatException;

import pl.projewski.game.antos.AntosProperties;
import pl.projewski.game.antos.AntosResources;
import pl.projewski.game.antos.configuration.GameConfiguration;
import pl.projewski.game.antos.gameengine.elements.Creature;
import pl.projewski.game.antos.gamegraphic.components.IGameGraphic;

/**
 *
 * @author piotr.rojewski
 */
public class MoveCreatureAction extends GameGraphicActionAbstract {

	private final int newx, newy, oldx, oldy;
	private final Creature creature;
	private int movex, movey;
	private int step, steps;
	BufferedImage oldSubImage = null;
	BufferedImage newSubImage = null;
	ImageObserver observer = null;

	public MoveCreatureAction(final ImageObserver observer, final IGameGraphic gameGraphic, final Creature creature,
			final int oldx, final int oldy, final int newx, final int newy) {
		super(gameGraphic);
		this.creature = creature;
		this.newx = newx;
		this.newy = newy;
		this.oldx = oldx;
		this.oldy = oldy;
		if (GameConfiguration.getInstance().isMoveSlide()) {
			steps = 8;
		} else {
			steps = 1;
		}
		step = 0;
		if (newx > oldx) {
			movex = AntosProperties.CELL_WIDTH / steps;
		} else if (newx < oldx) {
			movex = -AntosProperties.CELL_WIDTH / steps;
		} else {
			movex = 0;
		}
		if (newy > oldy) {
			movey = AntosProperties.CELL_HEIGHT / steps;
		} else if (newy < oldy) {
			movey = -AntosProperties.CELL_HEIGHT / steps;
		} else {
			movey = 0;
		}
		final BufferedImage background = AntosResources.getInstance()
				.loadImage(GameConfiguration.getInstance().getBackgroundImage());
		if (background != null) {
			// TODO: Check that subimage is in bound of background image or
			// create on start correctly sized background image
			try {
				oldSubImage = background.getSubimage(oldx * AntosProperties.CELL_WIDTH,
						oldy * AntosProperties.CELL_HEIGHT, AntosProperties.CELL_WIDTH, AntosProperties.CELL_HEIGHT);
			} catch (final RasterFormatException e) {
				oldSubImage = null;
			}
			try {
				newSubImage = background.getSubimage(newx * AntosProperties.CELL_WIDTH,
						newy * AntosProperties.CELL_HEIGHT, AntosProperties.CELL_WIDTH, AntosProperties.CELL_HEIGHT);
			} catch (final RasterFormatException e) {
				newSubImage = null;
			}
		}
	}

	@Override
	public boolean doStep(final Graphics graphics) {
		// clear current and next cell
		graphics.setColor(AntosProperties.GAMEPANEL_BACKGROUND);

		if (oldSubImage != null) {
			graphics.drawImage(oldSubImage, oldx * AntosProperties.CELL_WIDTH, oldy * AntosProperties.CELL_HEIGHT,
					observer);
		} else {
			graphics.fillRect(oldx * AntosProperties.CELL_WIDTH, oldy * AntosProperties.CELL_HEIGHT,
					AntosProperties.CELL_WIDTH, AntosProperties.CELL_HEIGHT);
		}
		if (newSubImage != null) {
			graphics.drawImage(newSubImage, newx * AntosProperties.CELL_WIDTH, newy * AntosProperties.CELL_HEIGHT,
					observer);
		} else {
			graphics.fillRect(newx * AntosProperties.CELL_WIDTH, newy * AntosProperties.CELL_HEIGHT,
					AntosProperties.CELL_WIDTH, AntosProperties.CELL_HEIGHT);
		}
		// redraw grid lines
		if (GameConfiguration.getInstance().isPaintGridLines()) {
			graphics.setColor(Color.lightGray);
			graphics.drawRect(oldx * AntosProperties.CELL_WIDTH, oldy * AntosProperties.CELL_HEIGHT,
					AntosProperties.CELL_WIDTH, AntosProperties.CELL_HEIGHT);
			graphics.drawRect(newx * AntosProperties.CELL_WIDTH, newy * AntosProperties.CELL_HEIGHT,
					AntosProperties.CELL_WIDTH, AntosProperties.CELL_HEIGHT);
		}
		// draw image
		graphics.drawImage(creature.image, oldx * AntosProperties.CELL_WIDTH + step * movex,
				oldy * AntosProperties.CELL_HEIGHT + step * movey, observer);
		step++;
		return step <= steps;
	}

}
