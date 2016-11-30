/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gameengine.elements;

import java.awt.image.BufferedImage;

/**
 *
 * @author piotr.rojewski
 */
public class Player extends Creature {

    public Player(int x, int y, BufferedImage image, int healthPoints) {
        super(x, y, image, healthPoints);
    }

}
