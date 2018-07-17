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
public class OutOfPercentIntervalException extends Exception{

    public OutOfPercentIntervalException() {
    }

    public OutOfPercentIntervalException(String message) {
        super(message);
    }

    public OutOfPercentIntervalException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfPercentIntervalException(Throwable cause) {
        super(cause);
    }

    public OutOfPercentIntervalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    
}
