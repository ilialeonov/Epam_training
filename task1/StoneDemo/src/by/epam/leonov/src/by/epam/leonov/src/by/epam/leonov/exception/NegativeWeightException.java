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
public class NegativeWeightException extends Exception{

    public NegativeWeightException() {
        super();
    }

    public NegativeWeightException(String message) {
        super(message);
    }

    public NegativeWeightException(String message, Throwable cause) {
        super(message, cause);
    }

    public NegativeWeightException(Throwable cause) {
        super(cause);
    }

    public NegativeWeightException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
