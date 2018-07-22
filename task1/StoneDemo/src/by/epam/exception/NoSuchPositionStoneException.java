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
public class NoSuchPositionStoneException extends Exception{

    public NoSuchPositionStoneException() {
    }

    public NoSuchPositionStoneException(String message) {
        super(message);
    }

    public NoSuchPositionStoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchPositionStoneException(Throwable cause) {
        super(cause);
    }

    public NoSuchPositionStoneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
