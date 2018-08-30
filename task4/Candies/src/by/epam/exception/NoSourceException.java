/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.exception;

/**
 *
 * @author Администратор
 */
public class NoSourceException extends Exception{

    public NoSourceException() {
    }

    public NoSourceException(String message) {
        super(message);
    }

    public NoSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSourceException(Throwable cause) {
        super(cause);
    }
    
}
