/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.projewski.game.antos.util.PropertiesWrapper;

/**
 *
 * @author WRO00541
 */
@Slf4j
public class AntosUtil {

    public static InputStream getStreamToResource(final String filename) {
        InputStream is = null;
        // check as file
        File file = new File(filename);
        if (file.exists()) {
            log.info("Find [" + filename + "] as file");
            try {
                is = new FileInputStream(file);
            } catch (FileNotFoundException ex) {
                log.warn("Fail loading file [" + filename + "]", ex);
            }
        }
        // check as resource
        if (is == null) {
            is = AntosConfiguration.class.getClassLoader().getResourceAsStream(filename);
            if (is != null) {
                log.info("Find [" + filename + "] as resource");
            }
        }
        return is;
    }

    public static PropertiesWrapper loadPropertiesFromFileOrResource(String propertiesFilename) {
        InputStream is = getStreamToResource(propertiesFilename);

        // load configuration
        PropertiesWrapper props = null;
        if (is == null) {
            log.info("Configuration [" + propertiesFilename + "] not found");
        } else {
            try {
                props = new PropertiesWrapper();
                props.load(is);
            } catch (IOException ex) {
                log.warn("Fail loading properties for [" + propertiesFilename + "]", ex);
                props = null;
            }
        }

        return props;
    }
}
