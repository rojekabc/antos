package pl.projewski.game.antos.gameengine.elements;

import java.awt.image.BufferedImage;

public class Element {
	public int x;
	public int y;
	public BufferedImage image;

	public Element(final int x, final int y, final BufferedImage image) {
		this.x = x;
		this.y = y;
		this.image = image;
	}

}
