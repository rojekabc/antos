/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos;

import lombok.extern.slf4j.Slf4j;
import pl.projewski.game.antos.configuration.GameConfiguration;
import pl.projewski.game.antos.gameengine.GameEngineFactory;
import pl.projewski.game.antos.gameengine.IGameEngine;
import pl.projewski.game.antos.gamegraphic.components.GameGraphicFactory;
import pl.projewski.game.antos.gamegraphic.components.IGameGraphic;

/**
 *
 * @author WRO00541
 */
@Slf4j
public class Main {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(final String[] args) {
		try {
			AntosConfiguration.getInstance();
			AntosResources.getInstance();
			GameConfiguration.getInstance();

			final IGameEngine gameEngine = GameEngineFactory.getGameEngine();
			final IGameGraphic gameGraphic = GameGraphicFactory.getGameGraphic();
			final GameContext context = new GameContext(gameGraphic, gameEngine);
			gameEngine.createWorld();
			gameGraphic.startMainFrame(context);
		} catch (RuntimeException e) {
			log.error("Error occured", e);
		} catch (Exception e) {
			log.error("Error occured", e);
		}
	}

}
