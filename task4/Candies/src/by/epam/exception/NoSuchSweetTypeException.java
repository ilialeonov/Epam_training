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
public class NoSuchSweetTypeException extends Exception{

    public NoSuchSweetTypeException() {
    }

    public NoSuchSweetTypeException(String message) {
        super(message);
    }

    public NoSuchSweetTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchSweetTypeException(Throwable cause) {
        super(cause);
    }
    
}
