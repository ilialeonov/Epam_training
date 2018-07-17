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
public class NoSuchGemException extends Exception{

    public NoSuchGemException() {
    }

    public NoSuchGemException(String message) {
        super(message);
    }

    public NoSuchGemException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchGemException(Throwable cause) {
        super(cause);
    }

    public NoSuchGemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
