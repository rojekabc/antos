/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.util;

import java.util.Properties;

/**
 *
 * @author wro00541
 */
public class PropertiesWrapper extends Properties {

    public boolean getPropertyBoolean(String property, boolean defaultValue) {
        final String value = this.getProperty(property);
        if (value == null) {
            return defaultValue;
        } else {
            return Boolean.valueOf(value);
        }
    }

    public int getPropertyInteger(String property, int defaultValue) {
        final String value = this.getProperty(property);
        if (value == null) {
            return defaultValue;
        } else {
            return Integer.valueOf(value);
        }
    }
}
