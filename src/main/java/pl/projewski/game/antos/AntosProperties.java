/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos;

import java.awt.Color;

/**
 *
 * @author WRO00541
 */
public interface AntosProperties {

	int MENUPANEL_WIDTH = 150; // APP_WIDTH - GAMEPANEL_WIDTH;
	int GRID_WIDTH = 18;
	int GRID_HEIGHT = 12;
	int CELL_WIDTH = 64;
	int CELL_HEIGHT = 64;
	int GAMEPANEL_WIDTH = CELL_WIDTH * GRID_WIDTH;
	int GAMEPANEL_HEIGHT = CELL_HEIGHT * GRID_HEIGHT;
	int MENUPANEL_MARGIN = 10;
	int APP_HEIGHT = GAMEPANEL_HEIGHT;
	int APP_WIDTH = GAMEPANEL_WIDTH + MENUPANEL_WIDTH + MENUPANEL_MARGIN;
	Color GAMEPANEL_BACKGROUND = Color.black;
	int MENUPANEL_HEIGHT = APP_HEIGHT;
}
