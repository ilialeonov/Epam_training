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
public class NoSymbolsException extends Exception{

    public NoSymbolsException() {
    }

    public NoSymbolsException(String message) {
        super(message);
    }

    public NoSymbolsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSymbolsException(Throwable cause) {
        super(cause);
    }
    
}
