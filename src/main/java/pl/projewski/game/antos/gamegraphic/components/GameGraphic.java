/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gamegraphic.components;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import lombok.extern.slf4j.Slf4j;
import pl.projewski.game.antos.GameContext;
import pl.projewski.game.antos.gameengine.elements.Creature;
import pl.projewski.game.antos.gameengine.elements.World;
import pl.projewski.game.antos.gamegraphic.actions.CreatureActionAbstract;
import pl.projewski.game.antos.gamegraphic.actions.DieCreatureAction;
import pl.projewski.game.antos.gamegraphic.actions.MoveCreatureAction;

/**
 *
 * @author piotr.rojewski
 */
@Slf4j
public class GameGraphic implements IGameGraphic {

	BufferStrategy bufferStrategy;
	MainFrame mainFrame;
	GamePanel gamePanel;
	HealthBar healthBar;
	Map<Creature, List<CreatureActionAbstract>> graphicActionsMap = new HashMap<>();
	Timer timer;

	@Override
	public void startMainFrame(final GameContext context) {
		mainFrame = new MainFrame(context);
		// mainFrame.setIgnoreRepaint(true);
		mainFrame.createBufferStrategy(2);
		bufferStrategy = mainFrame.getBufferStrategy();
		final Graphics drawGraphics = bufferStrategy.getDrawGraphics();
		mainFrame.paint(drawGraphics);
		drawGraphics.dispose();
		bufferStrategy.show();
	}

	@Override
	public void updateHealth() {
		synchronized (bufferStrategy) {
			final Graphics graphics = bufferStrategy.getDrawGraphics();
			this.healthBar.updateHealth(graphics);
			graphics.dispose();
			bufferStrategy.show();
		}
	}

	@Override
	public void setGamePanel(final GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void setHealthBar(final HealthBar hb) {
		this.healthBar = hb;
	}

	@Override
	public BufferStrategy getBufferStrategy() {
		return bufferStrategy;
	}

	@Override
	public void moveCreature(final Creature creature, final int newx, final int newy) {
		registerGraphicAction(new MoveCreatureAction(this, creature, creature.x, creature.y, newx, newy));
		// this.gamePanel.moveCreature(creature, newx, newy);
	}

	@Override
	public void creatureDie(final Creature creature, final World world) {
		registerGraphicAction(new DieCreatureAction(this, creature, world));
	}

	private void registerGraphicAction(final CreatureActionAbstract action) {
		boolean empty;
		synchronized (graphicActionsMap) {
			empty = graphicActionsMap.isEmpty();
			final Creature creature = action.getCreature();
			List<CreatureActionAbstract> list = graphicActionsMap.get(creature);
			if (list == null) {
				list = new ArrayList<>();
				graphicActionsMap.put(creature, list);
			}
			list.add(action);
		}
		// was it empty before - start TimerTask
		if (empty) {
			timer = new Timer(true);
			timer.schedule(new GameGraphicTimerTask(), 0, 10);
			log.debug("Start graphic actions");
		}
	}

	class GameGraphicTimerTask extends TimerTask {

		@Override
		public void run() {
			synchronized (graphicActionsMap) {
				synchronized (bufferStrategy) {
					// do all actions
					final Graphics graphic = bufferStrategy.getDrawGraphics();
					final Collection<List<CreatureActionAbstract>> values = graphicActionsMap.values();
					for (final List<CreatureActionAbstract> actions : values) {
						if (!actions.get(0).doStep(graphic)) {
							actions.remove(0);
						}
					}
					graphic.dispose();
					bufferStrategy.show();
					Toolkit.getDefaultToolkit().sync();
					// remove task, which finished
					final Set<Entry<Creature, List<CreatureActionAbstract>>> entrySet = graphicActionsMap.entrySet();
					final List<Creature> toRemoveList = new ArrayList<>();
					for (final Entry<Creature, List<CreatureActionAbstract>> entry : entrySet) {
						if (entry.getValue().isEmpty()) {
							toRemoveList.add(entry.getKey());
						}
					}
					for (final Creature creature : toRemoveList) {
						graphicActionsMap.remove(creature);
					}
					// break working of timer, if there is no actions
					if (graphicActionsMap.isEmpty()) {
						timer.cancel();
						log.debug("Stop graphic actions");
					}
				}
			}
		}
	}

}
