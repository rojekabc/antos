/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos;

import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.antos.util.PropertiesWrapper;

/**
 *
 * @author WRO00541
 */
public class AntosConfiguration {

    private static AntosConfiguration instance;
    private static final Log log = LogFactory.getLog(AntosConfiguration.class);

    public static AntosConfiguration getInstance() {
        if (instance == null) {
            instance = new AntosConfiguration();
        }
        return instance;
    }

    PropertiesWrapper gameProps = null;
    private Properties messages = null;

    private AntosConfiguration() {
        if (gameProps == null) {
            gameProps = AntosUtil.loadPropertiesFromFileOrResource("configuration.properties");
        }
        if (gameProps != null) {
            messages = AntosUtil.loadPropertiesFromFileOrResource(
                    "messages-" + gameProps.getProperty("language", "en") + ".properties");
        }
    }

    public String getMessage(String id) {
        String result = "- ? -";
        if (messages != null) {
            result = messages.getProperty(id, "- ? -");
        }
        return result;
    }

    public boolean getPropertyBoolean(String name, boolean defaultValue) {
        boolean result;
        if (gameProps != null) {
            result = gameProps.getPropertyBoolean(name, defaultValue);
        } else {
            result = defaultValue;
        }
        return result;
    }

    public int getPropertyInteger(String name, int defaultValue) {
        if (gameProps != null) {
            return gameProps.getPropertyInteger(name, defaultValue);
        } else {
            return defaultValue;
        }
    }
}
