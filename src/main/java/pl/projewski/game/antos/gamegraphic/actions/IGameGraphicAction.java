/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gamegraphic.actions;

import java.awt.Graphics;

/**
 *
 * @author piotr.rojewski
 */
public interface IGameGraphicAction {

    /**
     * Do one step of graphic action. Invoked by graphic timer.
     *
     * @param graphic draw graphic component
     *
     * @return true if it's not finished. False, if there are no more steps to do.
     */
    public boolean doStep(Graphics graphic);

}
