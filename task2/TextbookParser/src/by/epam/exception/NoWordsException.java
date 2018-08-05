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
public class NoWordsException extends Exception{

    public NoWordsException() {
    }

    public NoWordsException(String message) {
        super(message);
    }

    public NoWordsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoWordsException(Throwable cause) {
        super(cause);
    }
    
}