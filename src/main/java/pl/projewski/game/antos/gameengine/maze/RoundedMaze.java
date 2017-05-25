package pl.projewski.game.antos.gameengine.maze;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import pl.projewski.game.antos.AntosProperties;
import pl.projewski.game.antos.AntosResources;
import pl.projewski.game.antos.configuration.CreatureAmmount;
import pl.projewski.game.antos.configuration.EBlock;
import pl.projewski.game.antos.configuration.ECreature;
import pl.projewski.game.antos.configuration.GameConfiguration;
import pl.projewski.game.antos.gameengine.elements.Creature;
import pl.projewski.game.antos.gameengine.elements.Element;
import pl.projewski.game.antos.gameengine.elements.Player;

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

	@Override
	public void putMobs() {
		List<CreatureAmmount> creatureAmmountList = GameConfiguration.getInstance().getCreatureAmmount();
		if (creatureAmmountList != null && !creatureAmmountList.isEmpty()) {
			ECreature[] creatures = ECreature.values();
			for (CreatureAmmount creatureAmmount : creatureAmmountList) {
				// get creature name filter
				String creatureName = creatureAmmount.getCreature();
				// calculate number of MOBs to put
				final int numOfMobs = creatureAmmount.getMinAmmount()
				        + randomIntWithBound(creatureAmmount.getRandAmmount());
				List<ECreature> possibleCreatures = new ArrayList<>();
				// find list of possible creatures for this filter
				for (ECreature creature : creatures) {
					if (creature == ECreature.PLAYER) {
						continue;
					}
					if (creature.name().matches(creatureName)) {
						possibleCreatures.add(creature);
					}
				}
				if (possibleCreatures.isEmpty() || numOfMobs == 0) {
					continue;
				}
				for (int i = 0; i < numOfMobs; i++) {
					// random position
					int x, y;
					do {
						x = randomInt(AntosProperties.GRID_WIDTH);
						y = randomInt(AntosProperties.GRID_HEIGHT);
					} while (isAnyCollision(x, y));
					// random mob type
					final int mobSelect = randomInt(possibleCreatures.size());
					// create and put a mob
					final Creature mob = new Creature(possibleCreatures.get(mobSelect), x, y);
					putElement(mob);
					mobs.add(mob);
				}
			}
			// ctx.registerListener(player);
		}
	}

}
