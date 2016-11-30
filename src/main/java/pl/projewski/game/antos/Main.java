/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos;

import pl.projewski.game.antos.gameengine.GameEngineFactory;
import pl.projewski.game.antos.gameengine.IGameEngine;
import pl.projewski.game.antos.gamegraphic.components.GameGraphicFactory;
import pl.projewski.game.antos.gamegraphic.components.IGameGraphic;

/**
 *
 * @author WRO00541
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AntosConfiguration.getInstance();
        AntosResources.getInstance();

        IGameEngine gameEngine = GameEngineFactory.getGameEngine();
        IGameGraphic gameGraphic = GameGraphicFactory.getGameGraphic();
        GameContext context = new GameContext(gameGraphic, gameEngine);
        gameEngine.createWorld();
        gameGraphic.startMainFrame(context);
    }

}
