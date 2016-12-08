/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author WRO00541
 */
@Slf4j
public class AntosResources {

	private static AntosResources instance;

	public static AntosResources getInstance() {
		if (instance == null) {
			instance = new AntosResources();
		}
		return instance;
	}

	private final Map<String, BufferedImage> resources = new HashMap<>();

	private AntosResources() {
	}

	public BufferedImage loadImage(final String filename) {
		if (resources == null) {
			return null;
		}
		if (resources.containsKey(filename)) {
			return resources.get(filename);
		} else {
			final InputStream is = AntosUtil.getStreamToResource(filename);
			if (is != null) {
				try {
					final BufferedImage image = ImageIO.read(AntosUtil.getStreamToResource(filename));
					resources.put(filename, image);
					return image;
				} catch (final IOException ex) {
					log.warn("Cannot load resource + [" + filename + "]", ex);
					return null;
				}
			} else {
				log.warn("Cannot load resource + [" + filename + "]");
				return null;
			}
		}
	}
}
