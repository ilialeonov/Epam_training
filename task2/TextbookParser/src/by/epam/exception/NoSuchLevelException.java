/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.exception;

/**
 *
 * @author uks50
 */
public class NoSuchLevelException extends Exception{

    public NoSuchLevelException() {
    }

    public NoSuchLevelException(String message) {
        super(message);
    }

    public NoSuchLevelException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchLevelException(Throwable cause) {
        super(cause);
    }
    
}
