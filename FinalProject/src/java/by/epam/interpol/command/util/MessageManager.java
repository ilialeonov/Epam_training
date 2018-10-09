/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command.util;

import java.util.ResourceBundle;

/**
 *
 * @author Администратор
 */
public class MessageManager {

    private final static ResourceBundle resourceBundle 
            = ResourceBundle.getBundle("resources\\messages");
    static {
        if (resourceBundle == null) {
            throw new RuntimeException();
        }
    }
    private MessageManager() { 
    }
    
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
