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
public class NoParagraphsException extends Exception{

    public NoParagraphsException() {
    }

    public NoParagraphsException(String message) {
        super(message);
    }

    public NoParagraphsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoParagraphsException(Throwable cause) {
        super(cause);
    }
    
}
