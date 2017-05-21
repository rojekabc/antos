/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gamegraphic.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import lombok.extern.slf4j.Slf4j;
import pl.projewski.game.antos.AntosProperties;
import pl.projewski.game.antos.AntosResources;
import pl.projewski.game.antos.GameContext;
import pl.projewski.game.antos.configuration.GameConfiguration;
import pl.projewski.game.antos.gameengine.elements.Element;
import pl.projewski.game.antos.gameengine.elements.World;

/**
 *
 * @author WRO00541
 */
@Slf4j
class GamePanel extends JPanel implements KeyListener {

	GameContext context;
	private final HealthBar healthBar;
	private int keyPressed = 0;
	private int keyLast = 0;

	GamePanel(final GameContext context) {
		setSize(AntosProperties.APP_WIDTH, AntosProperties.APP_HEIGHT);
		setLayout(null);
		add(new ExitButton());
		this.context = context;
		context.getGameGraphic().setGamePanel(this);
		healthBar = new HealthBar(context);

		// setSize(AntosProperties.GAMEPANEL_WIDTH,
		// AntosProperties.GAMEPANEL_HEIGHT);
		// paint black background
		setBackground(AntosProperties.GAMEPANEL_BACKGROUND);
		// System.out.println("IS DOUBLE BUFFERED: " + isDoubleBuffered());
		// setDoubleBuffered(true);
		// setFocusable(true);
		// requestFocus();
		// addKeyListener(this);

		// ctx.registerListener(this, PlayerDeathEvent.class,
		// PlayerMoveEvent.class);
	}

	// It's called only when draw whole graphic by frame
	@Override
	public void paint(final Graphics g) {
		super.paint(g); // To change body of generated methods, choose Tools |
		                // Templates.
		// paint image background
		final BufferedImage background = AntosResources.getInstance()
		        .loadImage(GameConfiguration.getInstance().getBackgroundImage());

		if (background != null) {
			g.drawImage(background, 0, 0, this);
			// } else {
			// g.setColor(AntosProperties.GAMEPANEL_BACKGROUND);
		}
		/*
		 * g.fillRect( player.x * AntosProperties.CELL_WIDTH, player.y *
		 * AntosProperties.CELL_HEIGHT, AntosProperties.CELL_WIDTH,
		 * AntosProperties.CELL_HEIGHT);
		 */
		// paint grid lines
		if (GameConfiguration.getInstance().isPaintGridLines()) {
			g.setColor(Color.lightGray);
			for (int i = 0; i <= AntosProperties.GRID_WIDTH; i++) {
				g.drawLine(i * AntosProperties.CELL_WIDTH, 0, i * AntosProperties.CELL_WIDTH,
				        AntosProperties.GAMEPANEL_HEIGHT);
			}
			for (int j = 0; j <= AntosProperties.GRID_HEIGHT; j++) {
				g.drawLine(0, j * AntosProperties.CELL_HEIGHT, AntosProperties.GAMEPANEL_WIDTH,
				        j * AntosProperties.CELL_HEIGHT);
			}
		}
		// paint health bar
		healthBar.paint(g);
		// paint whole elements on map
		final World world = context.getWorld();
		for (int j = 0; j < AntosProperties.GRID_HEIGHT; j++) {
			for (int i = 0; i < AntosProperties.GRID_WIDTH; i++) {
				final Element e = world.getElement(i, j);
				if (e != null) {
					g.drawImage(e.image, e.x * AntosProperties.CELL_WIDTH, e.y * AntosProperties.CELL_HEIGHT, this);
				}
			}
		}
	}

	@Override
	public void keyTyped(final KeyEvent ke) {
	}

	@Override
	public void keyPressed(final KeyEvent ke) {
		final int kc = ke.getKeyCode();
		if (keyPressed != 0 && kc == keyLast) {
			return;
		}
		keyPressed++;
		keyLast = kc;
		if (!context.canPlayerTakeCommand()) {
			log.info("Cannot take action right now");
			return;
		}
		switch (kc) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
		case KeyEvent.VK_8:
		case KeyEvent.VK_NUMPAD8:
			context.movePlayer(0, -1);
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
		case KeyEvent.VK_2:
		case KeyEvent.VK_NUMPAD2:
			context.movePlayer(0, 1);
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
		case KeyEvent.VK_4:
		case KeyEvent.VK_NUMPAD4:
			context.movePlayer(-1, 0);
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
		case KeyEvent.VK_6:
		case KeyEvent.VK_NUMPAD6:
			context.movePlayer(1, 0);
			break;
		}
	}

	@Override
	public void keyReleased(final KeyEvent ke) {
		keyPressed--;
	}

}
