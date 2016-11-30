/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gamegraphic.components;

import java.awt.image.BufferStrategy;
import pl.projewski.game.antos.GameContext;
import pl.projewski.game.antos.gameengine.elements.Creature;

/**
 *
 * @author piotr.rojewski
 */
public interface IGameGraphic {

    void startMainFrame(GameContext context);

    public void updateHealth();

    public void setGamePanel(GamePanel gamePanel);

    public void setHealthBar(HealthBar healthBar);

    public BufferStrategy getBufferStrategy();

    public void moveCreature(Creature creature, int newx, int newy);

    public void playerDie();
}
