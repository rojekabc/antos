package pl.projewski.game.antos.gameengine.maze;

import java.awt.image.BufferedImage;

import lombok.NoArgsConstructor;
import pl.projewski.game.antos.AntosProperties;
import pl.projewski.game.antos.AntosResources;
import pl.projewski.game.antos.configuration.EBlock;
import pl.projewski.game.antos.gameengine.elements.Element;
import pl.projewski.game.antos.gameengine.elements.Player;

@NoArgsConstructor
public class RoundedMaze extends AbstractMaze {

	public RoundedMaze(int randomSeed) {
		super(randomSeed);
	}

	@Override
	public void createMap() {
		// draw block round
		final BufferedImage simpleImage = AntosResources.getInstance().loadImage(EBlock.SIMPLE.getImageResource());
		for (int i = 0; i < AntosProperties.GRID_WIDTH; i++) {
			putElement(new Element(i, 0, simpleImage));
			putElement(new Element(i, AntosProperties.GRID_HEIGHT - 1, simpleImage));
		}
		for (int i = 1; i < AntosProperties.GRID_HEIGHT - 1; i++) {
			putElement(new Element(0, i, simpleImage));
			putElement(new Element(AntosProperties.GRID_WIDTH - 1, i, simpleImage));
		}
	}

	@Override
	public void putPlayer(Player player) {
		putElement(player);
	}

}
