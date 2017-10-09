package pl.projewski.game.antos.gameengine.maze;

import java.awt.image.BufferedImage;

import lombok.NoArgsConstructor;
import pl.projewski.game.antos.AntosProperties;
import pl.projewski.game.antos.AntosResources;
import pl.projewski.game.antos.configuration.EBlock;
import pl.projewski.game.antos.gameengine.elements.Element;
import pl.projewski.game.antos.gameengine.elements.Player;
import pl.projewski.jdungeon.map.GeneratedMap;
import pl.projewski.jdungeon.map.IMapGenerator;
import pl.projewski.jdungeon.map.MapElement;
import pl.projewski.jdungeon.map.generator.RainDropRoomGenerator;

@NoArgsConstructor
public class RoomMaze extends AbstractMaze {

	@Override
	public void putPlayer(final Player player) {
		// find free position
		int x;
		int y;
		do {
			x = randomInt(AntosProperties.GRID_WIDTH);
			y = randomInt(AntosProperties.GRID_HEIGHT);
		} while (getElement(x, y) != null);
		player.x = x;
		player.y = y;
		putElement(player);
	}

	@Override
	public void createMap() {
		// final IMapGenerator roomGenerator = new SimpleRoomGenerator();
		final IMapGenerator roomGenerator = new RainDropRoomGenerator();
		final GeneratedMap generatedMap = roomGenerator.generate(AntosProperties.GRID_WIDTH,
		        AntosProperties.GRID_HEIGHT, randomLong());
		final BufferedImage simpleImage = AntosResources.getInstance().loadImage(EBlock.SIMPLE.getImageResource());
		for (int y = 0; y < AntosProperties.GRID_HEIGHT; y++) {
			for (int x = 0; x < AntosProperties.GRID_WIDTH; x++) {
				final byte mapValue = generatedMap.getValueByPosition(x, y);
				if (mapValue == MapElement.NOT_GENERATED.getMapValue() || mapValue == MapElement.WALL.getMapValue()) {
					putElement(new Element(x, y, simpleImage));
				}
			}
		}
	}

}
