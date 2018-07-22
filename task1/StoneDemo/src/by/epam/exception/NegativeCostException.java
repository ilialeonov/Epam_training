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
public class NegativeCostException extends Exception{

    public NegativeCostException() {
        super();
    }

    public NegativeCostException(String message) {
        super(message);
    }

    public NegativeCostException(String message, Throwable cause) {
        super(message, cause);
    }

    public NegativeCostException(Throwable cause) {
        super(cause);
    }

    public NegativeCostException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    
}
