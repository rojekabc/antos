/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gamegraphic.actions;

import pl.projewski.game.antos.gamegraphic.components.IGameGraphic;

/**
 *
 * @author piotr.rojewski
 */
public abstract class GameGraphicActionAbstract implements IGameGraphicAction {

	protected final IGameGraphic gameGraphic;

	protected GameGraphicActionAbstract(final IGameGraphic gameGraphic) {
		this.gameGraphic = gameGraphic;
	}

}
