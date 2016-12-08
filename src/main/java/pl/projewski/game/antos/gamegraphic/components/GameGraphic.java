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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lombok.extern.slf4j.Slf4j;
import pl.projewski.game.antos.GameContext;
import pl.projewski.game.antos.gameengine.elements.Creature;
import pl.projewski.game.antos.gamegraphic.actions.IGameGraphicAction;
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
	List<IGameGraphicAction> graphicActions = new ArrayList<>();
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
		registerGraphicAction(new MoveCreatureAction(gamePanel, this, creature, creature.x, creature.y, newx, newy));
		// this.gamePanel.moveCreature(creature, newx, newy);
	}

	@Override
	public void creatureDie(final Creature creature) {
		synchronized (bufferStrategy) {
			// do all actions
			final Graphics graphics = bufferStrategy.getDrawGraphics();
			this.gamePanel.creatureDie(graphics, creature);
			graphics.dispose();
			bufferStrategy.show();
			Toolkit.getDefaultToolkit().sync();
		}
	}

	private void registerGraphicAction(final IGameGraphicAction action) {
		boolean empty;
		synchronized (graphicActions) {
			empty = graphicActions.isEmpty();
			graphicActions.add(action);
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
			final List<IGameGraphicAction> toRemove = new ArrayList<>();
			synchronized (graphicActions) {
				synchronized (bufferStrategy) {
					// do all actions
					final Graphics graphic = bufferStrategy.getDrawGraphics();
					for (final IGameGraphicAction graphicAction : graphicActions) {
						if (!graphicAction.doStep(graphic)) {
							toRemove.add(graphicAction);
						}
					}
					graphic.dispose();
					bufferStrategy.show();
					Toolkit.getDefaultToolkit().sync();
					// remove task, which finished
					graphicActions.removeAll(toRemove);
					// break working of timer, if there is no actions
					if (graphicActions.isEmpty()) {
						timer.cancel();
						log.debug("Stop graphic actions");
					}
				}
			}
		}
	}

}
