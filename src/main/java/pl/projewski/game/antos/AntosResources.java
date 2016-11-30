/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author WRO00541
 */
public class AntosResources {

	private static AntosResources instance;
	private static final Log log = LogFactory.getLog(AntosResources.class);

	public static AntosResources getInstance() {
		if (instance == null) {
			instance = new AntosResources();
		}
		return instance;
	}

	private BufferedImage knight = null;
	private BufferedImage ghost = null;
	private BufferedImage rip = null;
	private BufferedImage backgroundMain = null;
	private BufferedImage simpleBlock = null;

	private AntosResources() {
		knight = loadImage("icons/character-icons/Knight/64x64.png");
		ghost = loadImage("icons/character-icons/Ghost/64x64.png");
		rip = loadImage("icons/RIP-64x64.png");
		if (AntosProperties.PAINT_BACKGROUND) {
			backgroundMain = loadImage("bckg/main.png");
		}
		simpleBlock = loadImage("block/64x64/Simple.png");
	}

	private BufferedImage loadImage(final String filename) {
		final InputStream is = AntosUtil.getStreamToResource(filename);
		if (is != null) {
			try {
				return ImageIO.read(AntosUtil.getStreamToResource(filename));
			} catch (final IOException ex) {
				log.warn("Cannot load resource + [" + filename + "]", ex);
				return null;
			}
		} else {
			log.warn("Cannot load resource + [" + filename + "]");
			return null;
		}
	}

	public BufferedImage getSimpleBlockImage() {
		return simpleBlock;
	}

	public BufferedImage getKnightImage() {
		return knight;
	}

	public BufferedImage getGhostImage() {
		return ghost;
	}

	public BufferedImage getRIPImage() {
		return rip;
	}

	public BufferedImage getMainBackgroundImage() {
		return backgroundMain;
	}
}
