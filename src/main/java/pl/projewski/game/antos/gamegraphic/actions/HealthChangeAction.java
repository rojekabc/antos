package pl.projewski.game.antos.gamegraphic.actions;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

import pl.projewski.game.antos.AntosProperties;
import pl.projewski.game.antos.AntosResources;
import pl.projewski.game.antos.configuration.GameConfiguration;
import pl.projewski.game.antos.gameengine.elements.Creature;
import pl.projewski.game.antos.gamegraphic.components.IGameGraphic;

public class HealthChangeAction extends CreatureActionAbstract {

	private final int healthChange;
	int step = 0;
	private final int creaturex;
	private final int creaturey;
	BufferedImage subImage = null;

	public HealthChangeAction(final IGameGraphic gameGraphic, final Creature creature, final int healthChange) {
		super(gameGraphic, creature);
		this.healthChange = healthChange;
		this.creaturex = creature.x;
		this.creaturey = creature.y;

		final BufferedImage background = AntosResources.getInstance()
				.loadImage(GameConfiguration.getInstance().getBackgroundImage());
		if (background != null) {
			// TODO: Check that subimage is in bound of background image or
			// create on start correctly sized background image
			try {
				subImage = background.getSubimage(creaturex * AntosProperties.CELL_WIDTH,
						creaturey * AntosProperties.CELL_HEIGHT, AntosProperties.CELL_WIDTH,
						AntosProperties.CELL_HEIGHT);
			} catch (final RasterFormatException e) {
				subImage = null;
			}
		}

	}

	@Override
	public boolean doStep(final Graphics graphic) {
		if (step < 10) {
			String string = "";
			if (healthChange > 0) {
				string = "+";
			}
			string += Integer.toString(healthChange);
			final char[] charArray = string.toCharArray();
			final Font font = new Font("Verdana", Font.BOLD, 20 + step);
			final FontMetrics fontMetrics = graphic.getFontMetrics(font);
			final int panelx = creaturex * AntosProperties.CELL_WIDTH
					+ (AntosProperties.CELL_WIDTH - fontMetrics.stringWidth(string)) / 2;
			final int panely = creaturey * AntosProperties.CELL_HEIGHT
					+ (AntosProperties.CELL_HEIGHT - fontMetrics.getHeight() + font.getSize()) / 2;

			final Graphics2D g2 = (Graphics2D) graphic;
			final GlyphVector glyphVector = font.createGlyphVector(g2.getFontRenderContext(), charArray);
			final Shape outline = glyphVector.getOutline();

			// draw background
			graphic.setColor(AntosProperties.GAMEPANEL_BACKGROUND);

			if (subImage != null) {
				graphic.drawImage(subImage, creaturex * AntosProperties.CELL_WIDTH,
						creaturey * AntosProperties.CELL_HEIGHT, null);
			} else {
				graphic.fillRect(creaturex * AntosProperties.CELL_WIDTH, creaturey * AntosProperties.CELL_HEIGHT,
						AntosProperties.CELL_WIDTH, AntosProperties.CELL_HEIGHT);
			}

			// draw creature image
			graphic.drawImage(creature.image, creaturex * AntosProperties.CELL_WIDTH,
					creaturey * AntosProperties.CELL_HEIGHT, null);

			// draw label with health change
			if (healthChange > 0) {
				g2.setColor(Color.GREEN);
			} else {
				g2.setColor(Color.RED);
			}
			g2.translate(panelx, panely);
			g2.fill(outline);
			g2.setColor(Color.WHITE);
			g2.draw(outline);
			g2.translate(-panelx, -panely);
			step++;
			return true;
		} else if (step < 30) {
			step++;
			return true;
		}
		// draw background
		graphic.setColor(AntosProperties.GAMEPANEL_BACKGROUND);

		if (subImage != null) {
			graphic.drawImage(subImage, creaturex * AntosProperties.CELL_WIDTH, creaturey * AntosProperties.CELL_HEIGHT,
					null);
		} else {
			graphic.fillRect(creaturex * AntosProperties.CELL_WIDTH, creaturey * AntosProperties.CELL_HEIGHT,
					AntosProperties.CELL_WIDTH, AntosProperties.CELL_HEIGHT);
		}

		// draw creature image
		graphic.drawImage(creature.image, creaturex * AntosProperties.CELL_WIDTH,
				creaturey * AntosProperties.CELL_HEIGHT, null);
		return false;
	}

}
