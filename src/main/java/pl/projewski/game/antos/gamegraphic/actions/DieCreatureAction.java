package pl.projewski.game.antos.gamegraphic.actions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

import lombok.extern.slf4j.Slf4j;
import pl.projewski.game.antos.AntosProperties;
import pl.projewski.game.antos.AntosResources;
import pl.projewski.game.antos.configuration.EBlock;
import pl.projewski.game.antos.configuration.GameConfiguration;
import pl.projewski.game.antos.gameengine.elements.Creature;
import pl.projewski.game.antos.gameengine.elements.Element;
import pl.projewski.game.antos.gameengine.elements.World;
import pl.projewski.game.antos.gamegraphic.components.IGameGraphic;

@Slf4j
public class DieCreatureAction extends CreatureActionAbstract {

	private final World world;

	public DieCreatureAction(final IGameGraphic gameGraphic, final Creature creature, final World world) {
		super(gameGraphic, creature);
		this.world = world;
	}

	@Override
	public boolean doStep(final Graphics graphics) {
		log.info("graphic - Creature die");
		// remove from world
		world.removeElement(creature);

		final int panelx = creature.x * AntosProperties.CELL_WIDTH;
		final int panely = creature.y * AntosProperties.CELL_HEIGHT;
		final BufferedImage background = AntosResources.getInstance()
				.loadImage(GameConfiguration.getInstance().getBackgroundImage());
		if (background != null) {
			try {
				final BufferedImage backImage = background.getSubimage(panelx, panely, AntosProperties.CELL_WIDTH,
						AntosProperties.CELL_HEIGHT);
				graphics.drawImage(backImage, panelx, panely, null);
			} catch (final RasterFormatException e) {
				// TODO: Change to more check, before take image
				graphics.setColor(AntosProperties.GAMEPANEL_BACKGROUND);
				graphics.fillRect(panelx, panely, AntosProperties.CELL_WIDTH, AntosProperties.CELL_HEIGHT);
			}
		} else {
			graphics.setColor(AntosProperties.GAMEPANEL_BACKGROUND);
			graphics.fillRect(panelx, panely, AntosProperties.CELL_WIDTH, AntosProperties.CELL_HEIGHT);
		}
		if (GameConfiguration.getInstance().isPaintGridLines()) {
			graphics.setColor(Color.lightGray);
			graphics.drawRect(panelx, panely, AntosProperties.CELL_WIDTH, AntosProperties.CELL_HEIGHT);
		}
		// place RIP block if available
		final String ripBlockName = creature.type.getRipBlockName();
		if (ripBlockName != null) {
			final EBlock ripBlock = EBlock.valueOf(ripBlockName);
			// this is because of moving action, which may replace drawn
			// image of RIP
			creature.image = AntosResources.getInstance().loadImage(ripBlock.getImageResource());
			world.putElement(new Element(creature.x, creature.y,
					AntosResources.getInstance().loadImage(ripBlock.getImageResource())));
			graphics.drawImage(creature.image, panelx, panely, null);
		}

		return false;
	}

}
