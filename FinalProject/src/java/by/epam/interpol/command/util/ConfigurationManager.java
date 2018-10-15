/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command.util;

import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ilia Leonov
 * defines page by key in bundle 
 */
public class ConfigurationManager {
    private final static Logger LOG = LogManager.getLogger(ConfigurationManager.class);

    private final static ResourceBundle resourceBundle 
            = ResourceBundle.getBundle("resources\\config");
    static {
        if (resourceBundle == null) {
            throw new RuntimeException();
        }
    }
    private ConfigurationManager() { 
    }

    /**
     *
     * @param key is a key in bundle
     * @return value in bundle by key
     */
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
    
}
