/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.leonov.exception;

/**
 *
 * @author Администратор
 */
public class BadIntervalException extends Exception{

    public BadIntervalException() {
        super();
    }

    public BadIntervalException(String message) {
        super(message);
    }

    public BadIntervalException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadIntervalException(Throwable cause) {
        super(cause);
    }

    public BadIntervalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
