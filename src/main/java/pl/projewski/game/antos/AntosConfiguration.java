/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos;

import java.util.Properties;

import pl.projewski.game.antos.configuration.GameConfiguration;

/**
 *
 * @author WRO00541
 */
public class AntosConfiguration {

	private static AntosConfiguration instance;

	public static AntosConfiguration getInstance() {
		if (instance == null) {
			instance = new AntosConfiguration();
		}
		return instance;
	}

	private Properties messages = null;

	private AntosConfiguration() {
		messages = AntosUtil.loadPropertiesFromFileOrResource(
				"messages-" + GameConfiguration.getInstance().getLanguage() + ".properties");
	}

	public String getMessage(final String id) {
		String result = "- ? -";
		if (messages != null) {
			result = messages.getProperty(id, "- ? -");
		}
		return result;
	}

}
