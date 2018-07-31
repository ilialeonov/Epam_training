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
public class NoSentencesException extends Exception{

    public NoSentencesException() {
    }

    public NoSentencesException(String message) {
        super(message);
    }

    public NoSentencesException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSentencesException(Throwable cause) {
        super(cause);
    }
    
}
